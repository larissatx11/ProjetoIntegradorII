package Telas;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Classes.Usuario;
import Conexao.ModuloConexao;
import DAO.UsuarioDAO;
import Swing.RoundedButton;
import Swing.RoundedLabel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class TelaPerfil extends JPanel {
	public JTextField txtEmail;
	public JTextField txtSenha;
	public JTextField txtNome;
	private ImageIcon camera;

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * Create the panel.
	 */
	public TelaPerfil(Usuario usu) {

		setBounds(0, 0, 691, 498);
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lblOU = new JLabel("ou");
		lblOU.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblOU.setHorizontalAlignment(SwingConstants.CENTER);
		lblOU.setBounds(323, 395, 46, 14);
		add(lblOU);

		txtSenha = new JTextField(usu.getSenha());
		txtSenha.setColumns(10);
		txtSenha.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSenha.setBackground(Color.WHITE);
		txtSenha.setBounds(357, 297, 190, 26);
		add(txtSenha);

		txtEmail = new JTextField(usu.getEmail());
		txtEmail.setColumns(10);
		txtEmail.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(357, 220, 190, 26);
		add(txtEmail);

		RoundedLabel rndLabelEmail = new RoundedLabel(30, 30);
		rndLabelEmail.setForeground(Color.WHITE);
		rndLabelEmail.setBackground(Color.WHITE);
		rndLabelEmail.setBounds(347, 218, 211, 30);
		add(rndLabelEmail);

		txtNome = new JTextField(usu.getNome());
		txtNome.setColumns(10);
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.setBackground(Color.WHITE);
		txtNome.setBounds(357, 145, 190, 26);
		add(txtNome);
		txtNome.setColumns(10);

		RoundedButton rndbtnExcluirConta = new RoundedButton("", 30);
		rndbtnExcluirConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioDAO usuDao = new UsuarioDAO();
				usuDao.excluirConta(usu);				
			}
		});
		rndbtnExcluirConta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndbtnExcluirConta.setBorder(null);
		rndbtnExcluirConta.setText("Excluir conta");
		rndbtnExcluirConta.setForeground(Color.WHITE);
		rndbtnExcluirConta.setFont(new Font("Century Gothic", Font.BOLD, 15));
		rndbtnExcluirConta.setBackground(new Color(40, 66, 159));
		rndbtnExcluirConta.setBounds(246, 413, 203, 34);
		add(rndbtnExcluirConta);

		JLabel lblEditarFoto = new JLabel("Editar foto:");
		lblEditarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarFoto.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblEditarFoto.setBounds(125, 122, 134, 14);
		add(lblEditarFoto);

		RoundedLabel rndLabelSenha = new RoundedLabel(30, 30);
		rndLabelSenha.setForeground(Color.WHITE);
		rndLabelSenha.setBackground(Color.WHITE);
		rndLabelSenha.setBounds(347, 296, 211, 30);
		add(rndLabelSenha);

		RoundedLabel rndLabelNome = new RoundedLabel(30, 30);
		rndLabelNome.setForeground(Color.WHITE);
		rndLabelNome.setBackground(Color.WHITE);
		rndLabelNome.setBounds(347, 143, 211, 30);
		add(rndLabelNome);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblSenha.setBounds(347, 272, 46, 14);
		add(lblSenha);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblEmail.setBounds(347, 195, 46, 14);
		add(lblEmail);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblNome.setBounds(347, 121, 46, 14);
		add(lblNome);

		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.setBounds(28, 0, 142, 85);
		lblPerfil.setAlignmentX(5.0f);
		lblPerfil.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblPerfil.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblPerfil.setForeground(new Color(40, 66, 159));
		add(lblPerfil);

		if (usu.getFoto() != null) {
			ImageIcon imageIcon1 = new ImageIcon(usu.getFoto());
			Image imagemOriginal1 = imageIcon1.getImage();
			// Redimensiona a imagem para as dimensões desejadas
			Image imagemRedimensionada1 = imagemOriginal1.getScaledInstance(135, 135, Image.SCALE_SMOOTH);
			// Cria um novo ImageIcon com a imagem redimensionada
			camera = new ImageIcon(imagemRedimensionada1);
		} else {
			ImageIcon imageIcon1 = new ImageIcon(TelaPerfil.class.getResource("/Icons/camera.png"));
			Image imagemOriginal1 = imageIcon1.getImage();
			// Redimensiona a imagem para as dimensões desejadas
			Image imagemRedimensionada1 = imagemOriginal1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			// Cria um novo ImageIcon com a imagem redimensionada
			camera = new ImageIcon(imagemRedimensionada1);
		}

		JLabel lblCamera = new JLabel(camera);
		lblCamera.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCamera.setBounds(125, 145, 135, 135);
		add(lblCamera);

		JTextField txtFoto = new JTextField(usu.getFoto());
		txtFoto.setVisible(false);
		txtFoto.setBounds(125, 278, 134, 19);
		add(txtFoto);
		txtFoto.setColumns(10);

		RoundedButton btnSelecionar = new RoundedButton("", 30);
		btnSelecionar.setBorder(null);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser arquivo = new JFileChooser();
				arquivo.setDialogTitle("SELECIONE UMA IMAGEM");
				arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int op = arquivo.showOpenDialog(null);
				if (op == JFileChooser.APPROVE_OPTION) {
					File selectedFile = arquivo.getSelectedFile();
					ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
					String caminho = selectedFile.getAbsolutePath();
					caminho = caminho.replace("\\", "/");
					txtFoto.setText(caminho);
					Image image = imageIcon.getImage().getScaledInstance(135, 135, Image.SCALE_DEFAULT);
					imageIcon = new ImageIcon(image);
					lblCamera.setIcon(imageIcon);
				} else if (op == JFileChooser.CANCEL_OPTION) {
					System.out.println("No Data");
				}
			}
		});
		btnSelecionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSelecionar.setText("Selecionar");
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnSelecionar.setBackground(new Color(40, 66, 159));
		btnSelecionar.setBounds(125, 298, 134, 28);
		add(btnSelecionar);

		RoundedButton rndbtnAtualizar = new RoundedButton("", 30);
		rndbtnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario aux = new Usuario();
				aux.setNome(txtNome.getText());
				aux.setEmail(txtEmail.getText());
				aux.setSenha(txtSenha.getText());
				aux.setFoto(txtFoto.getText());
				aux.setId(usu.getId());
				UsuarioDAO dao = new UsuarioDAO();
				dao.atualizarUsuario(aux);
			}
		});
		rndbtnAtualizar.setBorder(null);
		rndbtnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndbtnAtualizar.setText("Atualizar");
		rndbtnAtualizar.setForeground(Color.WHITE);
		rndbtnAtualizar.setFont(new Font("Century Gothic", Font.BOLD, 15));
		rndbtnAtualizar.setBackground(new Color(40, 66, 159));
		rndbtnAtualizar.setBounds(246, 358, 203, 34);
		add(rndbtnAtualizar);

		JLabel lblEditarPerfil = new JLabel("Editar Perfil");
		lblEditarPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarPerfil.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblEditarPerfil.setBounds(291, 85, 134, 14);
		add(lblEditarPerfil);

		RoundedLabel labelFundo = new RoundedLabel(20, 20);
		labelFundo.setBackground(new Color(230, 230, 230));
		labelFundo.setBounds(51, 73, 597, 395);
		add(labelFundo);

		JLabel lblConfiguraesDeUsurio = new JLabel("Configurações de Usuário");
		lblConfiguraesDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguraesDeUsurio.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblConfiguraesDeUsurio.setBounds(246, 43, 203, 19);
		add(lblConfiguraesDeUsurio);
	}
}
