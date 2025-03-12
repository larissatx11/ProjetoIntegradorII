package DAO;

import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Classes.Usuario;
import Conexao.ModuloConexao;
import Telas.TelaCadastro;
import Telas.TelaLogin;
import Telas.TelaPerfil;
import Telas.TelaPrincipal;

public class UsuarioDAO {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void cadastrar(Usuario usu) {
	    conexao = ModuloConexao.conector();
	    
	    // Verificar se o email já está cadastrado
	    String sqlVerificaEmail = "select * from usuario where email = ?";
	    try {
	        pst = conexao.prepareStatement(sqlVerificaEmail);
	        pst.setString(1, usu.getEmail());
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            // Se já existe um usuário com o mesmo email
	            JOptionPane.showMessageDialog(null, "Este email já está cadastrado. Por favor, use outro email.");
	        } else {
	            // Se não existe, faz o cadastro
	            String sqlCadastro = "insert into usuario(nome, email, senha, foto) values(?,?,?,?)";
	            pst = conexao.prepareStatement(sqlCadastro);
	            pst.setString(1, usu.getNome());
	            pst.setString(2, usu.getEmail());
	            pst.setString(3, usu.getSenha());
	            pst.setString(4, usu.getFoto());
	            pst.executeUpdate();

	            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

	            // Abrir tela de login após o cadastro bem-sucedido
	            TelaLogin login = new TelaLogin();
	            login.setVisible(true);
	            login.setLocationRelativeTo(null);
	        }

	        conexao.close(); // Fechar a conexão ao final

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage());
	    }
	}


	public int logar(Usuario usu) {
		conexao = ModuloConexao.conector();
		String sql = "select * from usuario where email =? and senha =?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, usu.getEmail());
			String captura = new String(usu.getSenha());
			pst.setString(2, captura);
			rs = pst.executeQuery();
			if (rs.next()) {
				usu.setNome(rs.getString("nome"));
				usu.setId(rs.getInt("id"));
				usu.setFoto(rs.getString("foto"));
				usu.setSaldo(rs.getDouble("saldo"));
				TelaPrincipal principal = new TelaPrincipal(usu);
				principal.setVisible(true);
				principal.setLocationRelativeTo(null);
				System.out.println("Id do usuario: " + usu.getId());
				conexao.close();
				return 1;
			} else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
				return 0;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}

	public void selecionarFoto(TelaCadastro cad) {
		JFileChooser arquivo = new JFileChooser();
		arquivo.setDialogTitle("SELECIONE UMA IMAGEM");
		arquivo.setFileFilter(
				new FileNameExtensionFilter("Arquivo de imagens(*.PNG, *.JPG, *.JPEG)", "png", "jpg", "jpeg"));
		// a linha abaixo seleciona apenas uma imagem
		arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int op = arquivo.showOpenDialog(null);
		if (op == JFileChooser.APPROVE_OPTION) {
			File selectedFile = arquivo.getSelectedFile();
			cad.txtFoto.setText(selectedFile.getAbsolutePath());
			ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			Image image = imageIcon.getImage().getScaledInstance(155, 135, Image.SCALE_DEFAULT);
			imageIcon = new ImageIcon(image);
			cad.lblCamera.setIcon(imageIcon);
		} else if (op == JFileChooser.CANCEL_OPTION) {
			System.out.println("No Data");
		}
	}

	public void atualizarUsuario(Usuario aux) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE usuario SET nome=?, email=?, senha=?, foto=? WHERE id=?"; // Usar a coluna "id" para
																							// identificar o usuário

		try {
			pst = conexao.prepareStatement(query);
			pst.setString(1, aux.getNome());
			pst.setString(2, aux.getEmail());
			pst.setString(3, aux.getSenha());
			pst.setString(4, aux.getFoto());
			pst.setInt(5, aux.getId()); // Usar o ID do usuário para identificar o registro a ser atualizado

			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(null, "Informações atualizadas com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações do usuário: " + e.getMessage());
		}
	}

	// Método para excluir a conta do usuário
	public void excluirConta(Usuario usu) { // Supondo que você passa o ID do usuário a ser excluído como argumento.
		conexao = ModuloConexao.conector();
		String sql = "DELETE FROM usuario WHERE id=?"; // Substitua 'id' pelo nome correto do campo de identificação na
														// sua tabela.
		try {
			pst = conexao.prepareStatement(sql);
			pst.setInt(1, usu.getId());

			int excluir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a conta?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (excluir == JOptionPane.YES_OPTION) {
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");	
				TelaPrincipal principal = new TelaPrincipal(usu);
				principal.dispose();
				TelaLogin login = new TelaLogin();
				login.setVisible(true);				
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir conta: " + e.getMessage());
		}
	}

	public Usuario somaSaldo(Usuario usu, double valor) {
		double soma = usu.getSaldo() + valor;
		System.out.println("Soma: " + usu.getSaldo());
		usu.setSaldo(soma);
		return usu;
	}

	public Usuario subtraiSaldo(Usuario usu, double valor) {
		double resto = usu.getSaldo() - valor;
		usu.setSaldo(resto);
		return usu;
	}

	public void atualizaSaldo(Usuario usu) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE usuario SET saldo=? WHERE id=?"; // Usa a coluna "id" para identificar a receita
		try {
			pst = conexao.prepareStatement(query);
			pst.setDouble(1, usu.getSaldo());
			pst.setInt(2, usu.getId());
			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				usu.setSaldo(usu.getSaldo());
				System.out.println("Saldo atualizado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o saldo: " + e.getMessage());
		}
	}

}
