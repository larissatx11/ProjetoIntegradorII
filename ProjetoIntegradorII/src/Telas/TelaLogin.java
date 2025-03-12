package Telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.Usuario;
import DAO.UsuarioDAO;
import Swing.PlaceholderPasswordField;
import Swing.PlaceholderTextField;
import Swing.RoundedButton;
import Swing.RoundedLabel;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.Cursor;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private PlaceholderPasswordField txtSenha;
	private PlaceholderTextField txtEmail;
	private JTextField txtAux;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {
		setTitle("Tela de Login");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 540);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		ImageIcon imageIcon2 = new ImageIcon(TelaLogin.class.getResource("/Icons/celularLogin.png"));
		Image imagemOriginal2 = imageIcon2.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada2 = imagemOriginal2.getScaledInstance(400, 424, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon icon_celular = new ImageIcon(imagemRedimensionada2);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblcelular = new JLabel(icon_celular);
		lblcelular.setBounds(390, 27, 400, 450);
		contentPane.add(lblcelular);

		JPanel panelBranco = new JPanel();
		panelBranco.setBackground(Color.WHITE);
		panelBranco.setBounds(0, 0, 516, 520);
		contentPane.add(panelBranco);
		panelBranco.setLayout(null);

		ImageIcon imageIcon = new ImageIcon(TelaLogin.class.getResource("/Icons/senha.png"));
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon icon_senha = new ImageIcon(imagemRedimensionada);

		RoundedButton rndBtnLogin = new RoundedButton("Clique Aqui", 30);
		rndBtnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndBtnLogin.setBounds(128, 280, 179, 28);
		panelBranco.add(rndBtnLogin);
		rndBtnLogin.setForeground(Color.WHITE);
		rndBtnLogin.setFont(new Font("Century Gothic", Font.BOLD, 15));
		rndBtnLogin.setText("Login");
		rndBtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usu = new Usuario();
				usu.setEmail(txtEmail.getText());
				usu.setSenha(txtSenha.getText());
				UsuarioDAO usuDAO = new UsuarioDAO();
				if (usuDAO.logar(usu) == 1) {
					dispose();
				} else {
					txtEmail.setText(null);
					txtSenha.setText(null);
				}
			}
		});
		rndBtnLogin.setBackground(new Color(40, 66, 159));
		JLabel lblNaoTemConta = new JLabel(
				"<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Não tem uma conta?<br>Clique abaixo para se cadastrar</html>");
		lblNaoTemConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaoTemConta.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblNaoTemConta.setBounds(97, 342, 242, 44);
		panelBranco.add(lblNaoTemConta);

		txtEmail = new PlaceholderTextField("Email");
		txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		txtEmail.setFont(new Font("Century Gothic", Font.BOLD, 13));
		txtEmail.setColumns(10);
		txtEmail.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtEmail.setBackground(new Color(230, 230, 230));
		txtEmail.setBounds(150, 142, 170, 33);
		txtEmail.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelBranco.add(txtEmail);
		JLabel lblSenha = new JLabel(icon_senha);
		lblSenha.setBounds(115, 206, 30, 30);
		panelBranco.add(lblSenha);

		ImageIcon imageIcon3 = new ImageIcon(TelaLogin.class.getResource("/Icons/user.png"));
		Image imagemOriginal3 = imageIcon3.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada3 = imagemOriginal3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon icon_user = new ImageIcon(imagemRedimensionada3);
		JLabel lblUser = new JLabel(icon_user);
		lblUser.setBounds(115, 143, 30, 30);
		panelBranco.add(lblUser);

		JLabel lblBemVindo = new JLabel("Bem-vindo de volta!");
		lblBemVindo.setForeground(new Color(40, 66, 159));
		lblBemVindo.setBackground(new Color(255, 255, 255));
		lblBemVindo.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblBemVindo.setBounds(109, 77, 200, 21);
		panelBranco.add(lblBemVindo);

		RoundedButton rndBtnCadastrar = new RoundedButton("", 30);
		rndBtnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rndBtnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastro chama = new TelaCadastro();
				chama.setVisible(true);
				// dispose();
			}
		});
		rndBtnCadastrar.setText("Cadastrar");
		rndBtnCadastrar.setForeground(Color.WHITE);
		rndBtnCadastrar.setFont(new Font("Century Gothic", Font.BOLD, 15));
		rndBtnCadastrar.setBackground(new Color(40, 66, 159));
		rndBtnCadastrar.setBounds(128, 411, 179, 28);
		panelBranco.add(rndBtnCadastrar);

		txtSenha = new PlaceholderPasswordField("Senha");
		txtSenha.setFont(new Font("Century Gothic", Font.BOLD, 13));
		txtSenha.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtSenha.setBackground(new Color(230, 230, 230));
		txtSenha.setColumns(10);
		txtSenha.setBounds(150, 205, 170, 33);
		txtSenha.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelBranco.add(txtSenha);

		RoundedLabel rndLblSenha = new RoundedLabel(20, 20);
		rndLblSenha.setBackground(new Color(230, 230, 230));
		rndLblSenha.setBounds(109, 205, 230, 33);
		panelBranco.add(rndLblSenha);

		RoundedLabel rndLblEmail = new RoundedLabel(20, 20);
		rndLblEmail.setBackground(new Color(230, 230, 230));
		rndLblEmail.setBounds(109, 142, 230, 33);
		panelBranco.add(rndLblEmail);

		txtAux = new JTextField();
		txtAux.setBounds(0, 0, 0, 0);
		panelBranco.add(txtAux);
		txtAux.setColumns(10);

		JPanel panelAzul = new JPanel();
		panelAzul.setBounds(516, 0, 344, 520);
		panelAzul.setBackground(new Color(40, 66, 159));
		contentPane.add(panelAzul);
		panelAzul.setLayout(null);

	}
}
