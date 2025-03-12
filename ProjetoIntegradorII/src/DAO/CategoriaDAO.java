package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Classes.Categoria;
import Classes.Receita;
import Classes.Usuario;
import Conexao.ModuloConexao;

public class CategoriaDAO {
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public int cadastrar(Categoria cat, Usuario usu) {
		conexao = ModuloConexao.conector();
		int idGerado = 0; // Inicializa o ID como 0 (em caso de erro)
		String sql = "insert into categoria(nome, usuario_id) values(?, ?)";
		try {
			pst = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, (cat.getNome()));
			pst.setInt(2, usu.getId());

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				// Obtém as chaves geradas automaticamente
				ResultSet generatedKeys = pst.getGeneratedKeys();
				if (generatedKeys.next()) {
					idGerado = generatedKeys.getInt(1); // Obtém o ID gerado
				}
				JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao cadastrar a categoria.");
			}
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar a categoria: " + e.getMessage());
		}
		return idGerado; // Retorna o ID gerado ou 0 em caso de erro
	}

	// Método para excluir a categoria
	public void excluirReceita(int idReceita) {
		conexao = ModuloConexao.conector();
		String sql = "DELETE FROM categoria WHERE id = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setInt(1, idReceita);

			int excluir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a categoria?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION) {
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir categoria: " + e.getMessage());
		}
	}

	// Método para atualizar receita com base no id
	public void atualizarReceita(Categoria cat) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE categoria SET nome=? WHERE id=?"; // Usa a coluna "id" para identificar a receita

		try {
			pst = conexao.prepareStatement(query);
			pst.setString(1, cat.getNome());
			pst.setInt(2, cat.getId());// Usa o ID da receita para identificar o registro a ser atualizado

			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações da categoria: " + e.getMessage());
		}
	}

	// Listar as receitas do usuário
	public List<Categoria> ListarReceitas(Usuario usuario) {
		List<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT * FROM categoria WHERE usuario_id = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, usuario.getId()); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");

				// Cria um objeto Receita com os dados do banco de dados
				Categoria categoria = new Categoria();
				categoria.setId(id);
				categoria.setNome(nome);

				categorias.add(categoria);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categorias;
	}
}
