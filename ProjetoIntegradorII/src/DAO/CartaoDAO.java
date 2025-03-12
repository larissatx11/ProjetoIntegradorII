package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import Classes.Cartao;
import Classes.Receita;
import Classes.Usuario;
import Conexao.ModuloConexao;

public class CartaoDAO {
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public int cadastrar(Cartao cartao) {
		conexao = ModuloConexao.conector();

		// Adicionar cartão de débito
		if (cartao.getTipo().equals("Débito")) {
			String sql = "insert into cartao(numero, bandeira, limite, usuario_id, tipo) values(?,?,?,?,?)";
			int idGerado = 0; // Inicialize o ID como 0 (em caso de erro)

			try {
				pst = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setInt(1, cartao.getNumero());
				pst.setString(2, cartao.getBandeira());
				pst.setDouble(3, cartao.getLimite());
				pst.setInt(4, cartao.getUsuario_id());
				pst.setString(5, "Débito");
				cartao.setFechamento(0);
				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					// Obtém as chaves geradas automaticamente
					ResultSet generatedKeys = pst.getGeneratedKeys();
					if (generatedKeys.next()) {
						idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
					}
					JOptionPane.showMessageDialog(null, "Cartão cadastrado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Falha ao cadastrar o cartão.");
				}
			} catch (SQLException e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao cadastrar o cartão: " + e.getMessage());
			}
			return idGerado; // Retorna o ID gerado ou 0 em caso de erro
		}
		// Adicionar cartão de crédito
		if (cartao.getTipo().equals("Crédito")) {
			String sql = "insert into cartao(numero, bandeira, valor_atual, limite, data_fechamento, usuario_id, tipo) values(?,?,?,?,?,?,?)";
			int idGerado = 0; // Inicialize o ID como 0 (em caso de erro)
			try {
				pst = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setInt(1, cartao.getNumero());
				pst.setString(2, cartao.getBandeira());
				pst.setDouble(3, cartao.getValor_atual());
				pst.setDouble(4, cartao.getLimite());
				pst.setInt(5, cartao.getFechamento());
				pst.setInt(6, cartao.getUsuario_id());
				pst.setString(7, "Crédito");

				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					// Obtém as chaves geradas automaticamente
					ResultSet generatedKeys = pst.getGeneratedKeys();
					if (generatedKeys.next()) {
						idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
					}
					JOptionPane.showMessageDialog(null, "Cartão cadastrado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Falha ao cadastrar o cartão.");
				}
			} catch (SQLException e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao cadastrar o cartão: " + e.getMessage());
			}
			return idGerado; // Retorna o ID gerado ou 0 em caso de erro
		}
		return 1;
	}

	// Método para excluir o cartão com base no id
	public int excluirCartao(int idCartao) {
		conexao = ModuloConexao.conector();
		String sql = "DELETE FROM cartao WHERE id = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setInt(1, idCartao);

			int excluir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cartão?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION) {
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cartão excluído com sucesso!");
				return 0;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir cartão: " + e.getMessage());
		}
		return 1;
	}

	// Método para atualizar cartao com base no id
	public void atualizarCartao(Cartao cartao) {
		conexao = ModuloConexao.conector();
		String query;

		// Tipo do cartão está sendo alterado de "Crédito" para "Débito"
		if ("Débito".equals(cartao.getTipo())) {
			query = "UPDATE cartao SET numero=?, bandeira=?, limite=?, tipo=?, data_fechamento=?, valor_atual=? WHERE id=?";
			// double limite = calcularReceita(cartao);
			try {
				pst = conexao.prepareStatement(query);
				pst.setDouble(1, cartao.getNumero());
				pst.setString(2, cartao.getBandeira());
				pst.setDouble(3, cartao.getLimite());
				pst.setString(4, cartao.getTipo());
				pst.setInt(5, 0); // data fechamento
				pst.setDouble(6, 0); // valor_atual
				pst.setInt(7, cartao.getId()); // Usa o ID do cartao para identificar o registro a ser atualizado

				int rowsUpdated = pst.executeUpdate();
				if (rowsUpdated > 0) {
					JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao atualizar informações do cartão: " + e.getMessage());
			}
		} else if ("Crédito".equals(cartao.getTipo())) {
			// Tipo do cartão está sendo alterado de "Débito" para "Crédito"
			query = "UPDATE cartao SET numero=?, bandeira=?, valor_atual=?, limite=?, data_fechamento=?, tipo=? WHERE id=?";

			try {
				pst = conexao.prepareStatement(query);
				pst.setLong(1, cartao.getNumero());
				pst.setString(2, cartao.getBandeira());
				pst.setDouble(3, cartao.getValor_atual());
				pst.setDouble(4, cartao.getLimite());
				pst.setInt(5, cartao.getFechamento());
				pst.setString(6, cartao.getTipo());
				pst.setInt(7, cartao.getId()); // Usa o ID da receita para identificar o registro a ser atualizado

				int rowsUpdated = pst.executeUpdate();
				if (rowsUpdated > 0) {
					JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao atualizar informações do cartão: " + e.getMessage());
			}
		}
	}

	public void atualizarLimiteCartao(Cartao cartao, Double novoLimite) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE cartao SET limite=? WHERE id=?";

		try {
			pst = conexao.prepareStatement(query);
			pst.setDouble(1, novoLimite);
			pst.setInt(2, cartao.getId());

			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Limite do cartão atualizado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao atualizar o limite do cartão.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o limite do cartão: " + e.getMessage());
		}
	}

	// Listar os cartoes do usuário
	public List<Cartao> ListarCartoes(Usuario usuario) {
		List<Cartao> cartoes = new ArrayList<>();

		String sql = "SELECT * FROM cartao WHERE usuario_id = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, usuario.getId()); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int numero = rs.getInt("numero");
				String tipo = rs.getString("tipo");
				String bandeira = rs.getString("bandeira");
				double limite = rs.getDouble("limite");
				double valorAtual = rs.getDouble("valor_atual");
				int data = rs.getInt("data_fechamento");

				// Cria um objeto Receita com os dados do banco de dados
				Cartao cartao = new Cartao();
				cartao.setId(id);
				cartao.setNumero(numero);
				cartao.setTipo(tipo);
				cartao.setBandeira(bandeira);
				cartao.setLimite(limite);
				cartao.setValor_atual(valorAtual);
				cartao.setFechamento(data);
				cartoes.add(cartao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartoes;
	}

	// Calcula o total da fatura do cartão
	public Double calcularFatura(String cartao) {
		conexao = ModuloConexao.conector();
		String sql = "SELECT SUM(valor) FROM despesa WHERE cartao = ?";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, cartao);
			rs = pst.executeQuery();

			if (rs.next()) {
				Double somaDespesas = rs.getDouble(1);
				return somaDespesas;
			} else {
				return 1.0; // Retorna 1.0 se não houver despesas
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0; // Retorna 0.0 em caso de erro
		}
	}

	// Atualiza a nova fatura na tabela
	public void atualizarFatura(String cartao, double novaFatura) {
		conexao = ModuloConexao.conector();
		String sql = "UPDATE cartao SET valor_atual = ? WHERE CONCAT(bandeira, ' - ', numero) = ?";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setDouble(1, novaFatura);
			pst.setString(2, cartao);
			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Fatura atualizada com sucesso!");
			} else {
				System.out.println("Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao atualizar a fatura: " + e.getMessage());
		}
	}

	// Atualiza o limite do cartão de débito
	public void atualizaSaldoCartaoDebito(Usuario usuario) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE cartao SET limite = ? WHERE tipo = 'Débito' AND usuario_id = ?";
		try {
			pst = conexao.prepareStatement(query);
			pst.setDouble(1, usuario.getSaldo()); // Atualiza o limite do cartão de débito
			pst.setInt(2, usuario.getId());
			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Limite do cartão de débito atualizado com sucesso!");
			} else {
				System.out.println("Nenhum registro foi atualizado.");
//				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao atualizar limite do cartão de débito: " + e.getMessage());
		}
	}

	public Cartao obterCartao(String cartaoConcatenado) {
		conexao = ModuloConexao.conector();
		String query = "SELECT * FROM cartao WHERE CONCAT(bandeira, ' - ', numero) = ?";

		try {
			pst = conexao.prepareStatement(query);
			pst.setString(1, cartaoConcatenado);
			rs = pst.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				int numero = rs.getInt("numero");
				String tipo = rs.getString("tipo");
				String bandeira = rs.getString("bandeira");
				double limite = rs.getDouble("limite");
				double valorAtual = rs.getDouble("valor_atual");
				int data = rs.getInt("data_fechamento");
				int usuario_id = rs.getInt("usuario_id");

				// Cria um objeto Receita com os dados do banco de dados
				Cartao cartao = new Cartao();
				cartao.setId(id);
				cartao.setNumero(numero);
				cartao.setTipo(tipo);
				cartao.setBandeira(bandeira);
				cartao.setLimite(limite);
				cartao.setValor_atual(valorAtual);
				cartao.setFechamento(data);
				cartao.setUsuario_id(usuario_id);
				return cartao;

			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro encontrado para o cartão: " + cartaoConcatenado);
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao obter limite do cartão: " + e.getMessage());
		}
		return null;
	}

}
