package Telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Classes.Usuario;
import DAO.UsuarioDAO;

import javax.swing.JPasswordField;
import Swing.RoundedLabel;
import javax.swing.JTextField;
import Swing.RoundedButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class TelaCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtEmail;
	private JPasswordField txtConfirmaSenha;
	private JPasswordField txtSenha;
	private JTextField textField;
	public JTextField txtFoto;
	public JLabel lblCamera;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastro() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 860, 540);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ImageIcon imageIcon = new ImageIcon(TelaCadastro.class.getResource("/Icons/pcCadastro.png"));
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon pc = new ImageIcon(imagemRedimensionada);
		
		JLabel lblpc = new JLabel(pc);
		lblpc.setBounds(330, 75, 443, 405);
		contentPane.add(lblpc);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 387, 520);
		contentPane.add(panel);
		panel.setLayout(null);
		
		RoundedButton rndbtnCadastrar = new RoundedButton("", 30);
		rndbtnCadastrar.setBorder(null);
		rndbtnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndbtnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtSenha.getText().equals(txtConfirmaSenha.getText())) {
					Usuario usu = new Usuario();
					usu.setNome(txtNome.getText());
					usu.setEmail(txtEmail.getText());
					usu.setSenha(txtSenha.getText());
					usu.setFoto(txtFoto.getText());
					UsuarioDAO chama = new UsuarioDAO();
					chama.cadastrar(usu);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Confirme a senha novamente!");
				}

			}
		});
		
		rndbtnCadastrar.setText("Cadastrar");
		rndbtnCadastrar.setForeground(Color.WHITE);
		rndbtnCadastrar.setFont(new Font("Century Gothic", Font.BOLD, 18));
		rndbtnCadastrar.setBackground(new Color(40, 66, 159));
		rndbtnCadastrar.setBounds(54, 449, 255, 34);
		panel.add(rndbtnCadastrar);
			
		
		JLabel lblCadastro = new JLabel("Cadastro");
		lblCadastro.setForeground(new Color(40, 66, 159));
		lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastro.setBounds(97, 7, 120, 42);
		lblCadastro.setFont(new Font("Century Gothic", Font.BOLD, 25));
		panel.add(lblCadastro);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		txtNome.setColumns(10);
		txtNome.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtNome.setBackground(new Color(230, 230, 230));
		txtNome.setBounds(54, 98, 245, 25);
		panel.add(txtNome);
		
		RoundedLabel rndNome = new RoundedLabel(20, 20);
		rndNome.setBackground(new Color(230, 230, 230));
		rndNome.setBounds(47, 98, 260, 25);
		panel.add(rndNome);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		txtEmail.setColumns(10);
		txtEmail.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEmail.setBackground(new Color(230, 230, 230));
		txtEmail.setBounds(54, 151, 245, 25);
		panel.add(txtEmail);
		
		RoundedLabel rndEmail = new RoundedLabel(20, 20);
		rndEmail.setBackground(new Color(230, 230, 230));
		rndEmail.setBounds(47, 151, 260, 25);
		panel.add(rndEmail);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(56, 209, 245, 25);
		panel.add(txtSenha);
		txtSenha.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		txtSenha.setColumns(10);
		txtSenha.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSenha.setBackground(new Color(230, 230, 230));
		
		RoundedLabel rndSenha = new RoundedLabel(20, 20);
		rndSenha.setBackground(new Color(230, 230, 230));
		rndSenha.setBounds(49, 209, 260, 25);
		panel.add(rndSenha);
		
		txtConfirmaSenha = new JPasswordField();
		txtConfirmaSenha.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		txtConfirmaSenha.setColumns(10);
		txtConfirmaSenha.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtConfirmaSenha.setBackground(new Color(230, 230, 230));
		txtConfirmaSenha.setBounds(56, 272, 245, 25);
		panel.add(txtConfirmaSenha);
		
		RoundedLabel rndSenha2 = new RoundedLabel(20, 20);
		rndSenha2.setBackground(new Color(230, 230, 230));
		rndSenha2.setBounds(49, 272, 260, 25);
		panel.add(rndSenha2);
		
		JLabel lblNome = new JLabel("Nome Completo");
		lblNome.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblNome.setBounds(54, 74, 122, 19);
		panel.add(lblNome);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblEmail.setBounds(56, 129, 122, 19);
		panel.add(lblEmail);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblSenha.setBounds(56, 187, 122, 19);
		panel.add(lblSenha);
		
		JLabel lblConfirmaSenha = new JLabel("Confirmar senha");
		lblConfirmaSenha.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblConfirmaSenha.setBounds(56, 245, 122, 19);
		panel.add(lblConfirmaSenha);
		
		textField = new JTextField();
		textField.setBounds(0, 0, 0, 0);
		panel.add(textField);
		textField.setColumns(10);
		
		ImageIcon imageIcon1 = new ImageIcon(TelaCadastro.class.getResource("/Icons/camera.png"));
		Image imagemOriginal1 = imageIcon1.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada1 = imagemOriginal1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon camera = new ImageIcon(imagemRedimensionada1);
		
		lblCamera = new JLabel(camera);
		lblCamera.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCamera.setBounds(54, 340, 78, 78);
		panel.add(lblCamera);
		
		txtFoto = new JTextField();
		txtFoto.setVisible(false);
		txtFoto.setBounds(47, 417, 96, 11);
		panel.add(txtFoto);
		txtFoto.setColumns(10);
		
		JPanel panelFoto = new JPanel();
		panelFoto.setBounds(54, 340, 78, 78);
		panel.add(panelFoto);
		
		RoundedButton rndbtnSelecionar = new RoundedButton("", 30);
		rndbtnSelecionar.setBorder(null);
		rndbtnSelecionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndbtnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				  UsuarioDAO usu = new UsuarioDAO();
//				  TelaCadastro tela = new TelaCadastro();
//			      usu.selecionarFoto(tela);
				JFileChooser arquivo = new JFileChooser();
				arquivo.setDialogTitle("SELECIONE UMA IMAGEM");
				arquivo.setFileFilter(
						new FileNameExtensionFilter("Arquivo de imagens(*.PNG, *.JPG, *.JPEG)", "png", "jpg", "jpeg"));
				// a linha abaixo seleciona apenas uma imagem
				arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int op = arquivo.showOpenDialog(null);
				if (op == JFileChooser.APPROVE_OPTION) {
					File selectedFile = arquivo.getSelectedFile();
					txtFoto.setText(selectedFile.getAbsolutePath());
					ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
					Image image = imageIcon.getImage().getScaledInstance(78, 78, Image.SCALE_DEFAULT);
					imageIcon = new ImageIcon(image);
					lblCamera.setIcon(imageIcon);
				} else if (op == JFileChooser.CANCEL_OPTION) {
					System.out.println("No Data");
				}
			}
		});
		rndbtnSelecionar.setText("Selecionar");
		rndbtnSelecionar.setForeground(Color.WHITE);
		rndbtnSelecionar.setFont(new Font("Century Gothic", Font.BOLD, 15));
		rndbtnSelecionar.setBackground(new Color(40, 66, 159));
		rndbtnSelecionar.setBounds(150, 365, 138, 28);
		panel.add(rndbtnSelecionar);
		
		JLabel lblFoto = new JLabel("Foto de perfil");
		lblFoto.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblFoto.setBounds(54, 321, 122, 19);
		panel.add(lblFoto);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(40, 66, 159));
		panel_1.setBounds(387, 0, 473, 520);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblContaNova = new JLabel("<html>Criar nova<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; conta</html>");
		lblContaNova.setForeground(Color.WHITE);
		lblContaNova.setHorizontalAlignment(SwingConstants.CENTER);
		lblContaNova.setFont(new Font("Century Gothic", Font.BOLD, 45));
		lblContaNova.setBounds(187, 10, 258, 104);
		panel_1.add(lblContaNova);
	}
}
