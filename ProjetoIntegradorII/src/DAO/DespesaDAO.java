package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import Classes.Cartao;
import Classes.Despesa;
import Classes.Receita;
import Classes.Usuario;
import Conexao.ModuloConexao;
import Telas.TelaCartoes;
import Telas.TelaDespesa;
import Telas.TelaReceita;

public class DespesaDAO {
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int idGerado;
	int rowsAffected;
	int rowsUpdated;
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	private TelaReceita telaReceita;
	private TelaCartoes telaCartoes;
	CartaoDAO cartoesDAO = new CartaoDAO();
	Cartao cartao = new Cartao();

	public int verificarSaldo(Despesa despesa, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		if (despesa.getFormaDePagamento() == "Débito" || despesa.getFormaDePagamento() == "Dinheiro") {
			if (despesa.getValor() <= usu.getSaldo()) {
				if(cadastrar(despesa, usu, telaCartoes) != 0) {
					usuarioDAO.atualizaSaldo(usuarioDAO.subtraiSaldo(usu, despesa.getValor()));				
					telaReceita.novoSaldo(usu.getSaldo());

					cartoesDAO.atualizaSaldoCartaoDebito(usu);
					List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
					telaCartoes.atualizarTabela(cartoesAtualizados);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
				return 0;
			}
		}
		if (despesa.getFormaDePagamento() == "Crédito") {

			cartao = cartoesDAO.obterCartao(despesa.getCartao());
			if (despesa.getValor() <= (cartao.getLimite() - cartao.getValor_atual())) {
				if(cadastrar(despesa, usu, telaCartoes) != 0) {
					// Calcula a fatura do cartão de crédito
					double faturaAtualizada = cartoesDAO.calcularFatura(despesa.getCartao());
					cartao.setValor_atual(faturaAtualizada);
					cartoesDAO.atualizarFatura(despesa.getCartao(), faturaAtualizada); // Atualiza a fatura na tabela de
																						// cartões
					List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
					telaCartoes.atualizarTabela(cartoesAtualizados);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Limite insuficiente!");
				return 0;
			}
		}
		return 1;
	}

	public int cadastrar(Despesa despesa, Usuario usu, TelaCartoes telaCartoes) {
		int controle = 0;
		this.telaCartoes = telaCartoes;
		conexao = ModuloConexao.conector();
		int idGerado = 0;

		String sql = "insert into despesa (usuario_id, valor, data, descricao, categoria, forma_de_pagamento, cartao, numero_de_parcelas, pago) values (?,?,?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setInt(1, despesa.getUsuario_id());
			pst.setDouble(2, despesa.getValor());
			java.sql.Date sqlDate = new java.sql.Date(despesa.getData().getTime());
			pst.setDate(3, sqlDate);
			pst.setString(4, despesa.getDescricao());
			pst.setString(5, despesa.getCategoria());
			pst.setString(6, despesa.getFormaDePagamento());
			pst.setString(7, despesa.getCartao());
			pst.setInt(8, despesa.getNumeroDeParcelas());
			if (despesa.getFormaDePagamento().equals("Crédito")) {
				pst.setBoolean(9, false);
			} else {
				pst.setBoolean(9, true);
				rowsAffected = pst.executeUpdate();
			}

			if (despesa.getFormaDePagamento().equals("Crédito")) {
				double valorParcelado = despesa.getValor() / despesa.getNumeroDeParcelas();
				pst.setDouble(2, valorParcelado);
				pst.setBoolean(9, false);
				
				// Verifica dia da compra para parcelamento de fatura
				java.sql.Date dataParcela = new java.sql.Date(despesa.getData().getTime());
				java.sql.Date dataParcelaFechamento = new java.sql.Date(despesa.getData().getTime());
				cartao = cartoesDAO.obterCartao(despesa.getCartao());
				if (cartao.getFechamento() < dataParcelaFechamento.getDate() && controle == 0) {
					// Suponha que despesa.getData() retorna um objeto java.sql.Date
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(despesa.getData());

					// Adiciona 1 ao mês
					calendar.add(Calendar.MONTH, 1);
					// Obtém a nova data após adicionar 1 ao mês
					despesa.setData(calendar.getTime());
					dataParcela = new java.sql.Date(despesa.getData().getTime());

					pst.setDate(3, dataParcela);
					controle = 1;
				}

				for (int i = 1; i <= despesa.getNumeroDeParcelas(); i++) {
					dataParcela = new java.sql.Date(despesa.getData().getTime());
					rowsAffected = pst.executeUpdate();
					dataParcela.setMonth(dataParcela.getMonth() + i);
					pst.setDate(3, dataParcela);
				}
			}			

			if (rowsAffected > 0) {
				// Obtém as chaves geradas automaticamente
				ResultSet generatedKeys = pst.getGeneratedKeys();
				if (generatedKeys.next()) {
					idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
					despesa.setId(idGerado);
					System.out.println("ID da despesa: " + idGerado);
				}
				JOptionPane.showMessageDialog(null, "Despesa cadastrada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao cadastrar a despesa.");
			}
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar a despesa: " + e.getMessage());
		}
		return idGerado; // Retorna o ID gerado ou 0 em caso de erro
	}

	public void atualizarDespesa(Despesa despesa, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		conexao = ModuloConexao.conector();
		String query = "UPDATE despesa SET usuario_id=?, categoria=?, valor=?, data=?, forma_de_pagamento=?, cartao=?,"
				+ " numero_de_parcelas=?, descricao=?, pago=? WHERE id=?";
		try {
			pst = conexao.prepareStatement(query);
			pst.setInt(1, despesa.getUsuario_id());
			pst.setString(2, despesa.getCategoria());
			pst.setDouble(3, despesa.getValor());
			java.sql.Date sqlDate = new java.sql.Date(despesa.getData().getTime());
			pst.setDate(4, sqlDate);
			pst.setString(5, despesa.getFormaDePagamento());
			pst.setString(6, despesa.getCartao());
			pst.setInt(7, despesa.getNumeroDeParcelas());
			pst.setString(8, despesa.getDescricao());
			pst.setBoolean(9, despesa.isPago());
			pst.setInt(10, despesa.getId());

			if (despesa.getFormaDePagamento() == "Débito" || despesa.getFormaDePagamento() == "Dinheiro") {
				if (despesa.getValor() <= usu.getSaldo()) {
					rowsUpdated = pst.executeUpdate();
				} else {
					JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
				}
			}
			if (despesa.getFormaDePagamento() == "Crédito") {
				cartao = cartoesDAO.obterCartao(despesa.getCartao());
				if (despesa.getValor() <= (cartao.getLimite() - cartao.getValor_atual())) {
					rowsUpdated = pst.executeUpdate();
				} else {
					JOptionPane.showMessageDialog(null, "Limite insuficiente!");
				}
			}

			if (rowsUpdated > 0) {
				usuarioDAO.subtraiSaldo(usu, despesa.getValor());
				usuarioDAO.atualizaSaldo(usu);
				telaReceita.novoSaldo(usu.getSaldo());

				// Recalcula a fatura após a atualização
				double novaFatura = cartoesDAO.calcularFatura(despesa.getCartao());
				// Atualiza o valor da fatura no banco de dados e na tabela de cartões
				cartoesDAO.atualizarFatura(despesa.getCartao(), novaFatura);

				cartoesDAO.atualizaSaldoCartaoDebito(usu);
				List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
				telaCartoes.atualizarTabela(cartoesAtualizados);
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações da despesa: " + e.getMessage());
		}
	}

	public int excluirDespesa(Despesa despesa, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		conexao = ModuloConexao.conector();
		// Obter o valor da despesa antes de excluí-la
		double valorDespesa = despesa.getValor();
		String sql = "DELETE FROM despesa WHERE id = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setInt(1, despesa.getId());
			int excluir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a Despesa?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION) {
				String cartaoSelecionado = despesa.getCartao();
				double faturaAtualizada = cartoesDAO.calcularFatura(cartaoSelecionado);
				pst.executeUpdate();
				if(despesa.isPago()) {
					usuarioDAO.atualizaSaldo(usuarioDAO.somaSaldo(usu, despesa.getValor()));
					telaReceita.novoSaldo(usu.getSaldo());	
				}
				// Excluir a despesa da fatura do cartao
				double novaFatura = faturaAtualizada - valorDespesa;
				cartoesDAO.atualizarFatura(cartaoSelecionado, novaFatura);
				cartoesDAO.atualizaSaldoCartaoDebito(usu);
				// Atualizar a tabela de cartões na interface gráfica
				List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
//				telaReceita.novoSaldo(usu.getSaldo());
				telaCartoes.atualizarTabela(cartoesAtualizados);

				JOptionPane.showMessageDialog(null, "Despesa excluída com sucesso!");
				return 1;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir receita: " + e.getMessage());
		}
		return 0;
	}

	public List<Despesa> ListarDespesas(Usuario usuario) {
		List<Despesa> despesas = new ArrayList<>();

		String sql = "SELECT * FROM despesa WHERE usuario_id = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, usuario.getId()); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				// Cria um objeto Receita com os dados do banco de dados
				Despesa despesa = new Despesa();
				despesa.setId(rs.getInt("id"));
				despesa.setUsuario_id(usuario.getId());
				despesa.setValor(rs.getDouble("valor"));
				despesa.setData(rs.getDate("data"));
				despesa.setDescricao(rs.getString("descricao"));
				despesa.setCategoria(rs.getString("categoria"));
				despesa.setFormaDePagamento(rs.getString("forma_de_pagamento"));
				despesa.setCartao(rs.getString("cartao"));
				despesa.setNumeroDeParcelas(rs.getInt("numero_de_parcelas"));
				despesa.setPago(rs.getBoolean("pago"));
				despesas.add(despesa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

}
