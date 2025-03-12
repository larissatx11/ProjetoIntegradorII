package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import Classes.Cartao;
import Classes.Receita;
import Classes.Usuario;
import Conexao.ModuloConexao;
import Telas.TelaCartoes;
import Telas.TelaReceita;

import java.util.Date;

public class ReceitaDAO {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	private TelaReceita telaReceita;
	private TelaCartoes telaCartoes;
	CartaoDAO cartoesDAO = new CartaoDAO();

	public int cadastrar(Receita rec, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		int idGerado = 0;
		conexao = ModuloConexao.conector();
		String sql = "insert into receita(valor, mes, usuario_id) values(?,?,?)";
		try {
			pst = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setDouble(1, rec.getValor());
			// Converte a data de java.util.Date para java.sql.Date
			java.sql.Date sqlDate = new java.sql.Date(rec.getMes().getTime());
			pst.setDate(2, sqlDate);
			pst.setInt(3, usu.getId());

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				// Obtém as chaves geradas automaticamente
				ResultSet generatedKeys = pst.getGeneratedKeys();
				if (generatedKeys.next()) {
					idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
				}
				usuarioDAO.atualizaSaldo(usuarioDAO.somaSaldo(usu, rec.getValor()));
				telaReceita.novoSaldo(usu.getSaldo());
				cartoesDAO.atualizaSaldoCartaoDebito(usu);
				// Atualizar a tabela de cartões na interface gráfica
				List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
				telaCartoes.atualizarTabela(cartoesAtualizados);
				JOptionPane.showMessageDialog(null, "Receita cadastrada com sucesso!");

			} else {
				JOptionPane.showMessageDialog(null, "Falha ao cadastrar a receita.");
			}
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar a receita: " + e.getMessage());
		}
		return idGerado; // Retorna o ID gerado ou 0 em caso de erro
	}

	// Método para excluir a receita
	public int excluirReceita(int idReceita, double valor, Usuario usu, TelaReceita telaReceita,
			TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		conexao = ModuloConexao.conector();
		String sql = "DELETE FROM receita WHERE id = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setInt(1, idReceita);

			int excluir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a receita?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION) {
				pst.executeUpdate();
				usuarioDAO.atualizaSaldo(usuarioDAO.subtraiSaldo(usu, valor));
				telaReceita.novoSaldo(usu.getSaldo());
				cartoesDAO.atualizaSaldoCartaoDebito(usu);
				// Atualizar a tabela de cartões na interface gráfica
				List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
				telaCartoes.atualizarTabela(cartoesAtualizados);

				JOptionPane.showMessageDialog(null, "Receita excluída com sucesso!");
				return 1;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir receita: " + e.getMessage());
		}
		return 0;
	}

	// Método para atualizar receita com base no id
	public void atualizarReceita(Receita rec, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		conexao = ModuloConexao.conector();
		String query = "UPDATE receita SET valor=?, mes=? WHERE id=?"; // Usa a coluna "id" para identificar a receita
		try {
			pst = conexao.prepareStatement(query);
			pst.setDouble(1, rec.getValor());
			// Converta a data de java.util.Date para java.sql.Date
			java.sql.Date sqlDate = new java.sql.Date(rec.getMes().getTime());
			pst.setDate(2, sqlDate);
			pst.setInt(3, rec.getId()); // Usa o ID da receita para identificar o registro a ser atualizado

			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				usuarioDAO.somaSaldo(usu, rec.getValor());
				JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
				usuarioDAO.atualizaSaldo(usu);
				telaReceita.novoSaldo(usu.getSaldo());
				cartoesDAO.atualizaSaldoCartaoDebito(usu);
				// Atualizar a tabela de cartões na interface gráfica
				List<Cartao> cartoesAtualizados = cartoesDAO.ListarCartoes(usu);
				telaCartoes.atualizarTabela(cartoesAtualizados);
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações da receita: " + e.getMessage());
		}
	}

	// Listar as receitas do usuário
	public List<Receita> ListarReceitas(Usuario usuario) {
		List<Receita> receitas = new ArrayList<>();

		String sql = "SELECT * FROM receita WHERE usuario_id = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, usuario.getId()); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				double valor = rs.getDouble("valor");
				Date mes = rs.getDate("mes");

				// Cria um objeto Receita com os dados do banco de dados
				Receita receita = new Receita();
				receita.setId(id);
				receita.setValor(valor);
				receita.setMes(mes);

				receitas.add(receita);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

}
