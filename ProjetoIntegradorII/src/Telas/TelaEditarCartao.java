package Telas;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Classes.Cartao;
import Classes.Usuario;
import DAO.CartaoDAO;
import Swing.RoundedButton;

public class TelaEditarCartao extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumero;
	private JTextField txtLimite;
	private JTextField txtFechamento;
	private TelaCartoes telaCartoes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cartao c = new Cartao();
					Usuario usu = new Usuario();
					TelaCartoes telaCartoes = new TelaCartoes(usu);
					TelaEditarCartao frame = new TelaEditarCartao(c, usu, telaCartoes);
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
	public TelaEditarCartao(Cartao cartao, Usuario usu, TelaCartoes telaCartoes) {
		this.telaCartoes = telaCartoes;
		setTitle("Adicionar cartão");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 270, 348);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNumero = new JLabel("Número");
		lblNumero.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblNumero.setBounds(37, 30, 46, 14);
		contentPane.add(lblNumero);

		JLabel lblLimite = new JLabel("Limite");
		lblLimite.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblLimite.setBounds(37, 76, 46, 14);
		contentPane.add(lblLimite);

		JLabel lblFechamento = new JLabel("Fechamento");
		lblFechamento.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblFechamento.setBounds(37, 122, 81, 14);
		contentPane.add(lblFechamento);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblTipo.setBounds(37, 171, 46, 14);
		contentPane.add(lblTipo);

		JLabel lblBandeira = new JLabel("Bandeira");
		lblBandeira.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblBandeira.setBounds(37, 215, 67, 14);
		contentPane.add(lblBandeira);

		txtNumero = new JTextField(String.valueOf(cartao.getNumero()));
		txtNumero.setBounds(37, 45, 181, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);

		txtLimite = new JTextField(String.valueOf(cartao.getLimite()));
		txtLimite.setBounds(37, 91, 181, 20);
		contentPane.add(txtLimite);
		txtLimite.setColumns(10);

		txtFechamento = new JTextField(String.valueOf(cartao.getFechamento()));
		txtFechamento.setBounds(37, 138, 181, 20);
		contentPane.add(txtFechamento);
		txtFechamento.setColumns(10);

		// Criar escolhas (Choice) para "Tipo" e "Bandeira" e adicionar opções
		Choice choiceBandeira = new Choice();
		choiceBandeira.setBounds(37, 230, 181, 20);
		choiceBandeira.add(cartao.getBandeira());
		choiceBandeira.add("Visa");
		choiceBandeira.add("MasterCard");
		choiceBandeira.add("American Express");
		choiceBandeira.add("Elo");
		choiceBandeira.addItemListener((ItemListener) new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (choiceBandeira.getSelectedItem().equals("Selecione")) {
					System.out.println("Bandeira de cartão inválida");
				} else if (choiceBandeira.getSelectedItem().equals("Visa")) {
					cartao.setBandeira("Visa");
				} else if (choiceBandeira.getSelectedItem().equals("MasterCard")) {
					cartao.setBandeira("MasterCard");
				} else if (choiceBandeira.getSelectedItem().equals("American Express")) {
					cartao.setBandeira("American Express");
				} else if (choiceBandeira.getSelectedItem().equals("Elo")) {
					cartao.setBandeira("Elo");
				}
			}
		});

		contentPane.add(choiceBandeira);

		Choice choiceTipo = new Choice();
		choiceTipo.setBounds(37, 187, 181, 20);
		choiceTipo.add(cartao.getTipo());
		choiceTipo.add("Débito");
		choiceTipo.add("Crédito");
		choiceTipo.addItemListener((ItemListener) new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (choiceTipo.getSelectedItem().equals("Selecione")) {
					System.out.println("Tipo de cartão inválido");
				} else if (choiceTipo.getSelectedItem().equals("Débito")) {
					cartao.setTipo("Débito");
				} else if (choiceTipo.getSelectedItem().equals("Crédito")) {
					cartao.setTipo("Crédito");
				}
			}
		});
		contentPane.add(choiceTipo);

		// Botão para salvar as alterações
		RoundedButton btnEditar = new RoundedButton("Salvar", 20);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Obtém os novos valores dos campos de texto
				int novoNumero = Integer.parseInt(txtNumero.getText());
				Double novoLimite = Double.parseDouble(txtLimite.getText());
				int novoFechamento = Integer.parseInt(txtFechamento.getText());
				String novaBandeira = choiceBandeira.getSelectedItem();
				String novoTipo = choiceTipo.getSelectedItem();

				// Atualiza os atributos do cartao
				if (choiceTipo.getSelectedItem().equals("Débito")) {
					cartao.setLimite(usu.getSaldo());
				} else {
					cartao.setLimite(novoLimite);
				}
				cartao.setNumero(novoNumero);
				cartao.setFechamento(novoFechamento);
				cartao.setBandeira(novaBandeira);
				cartao.setTipo(novoTipo); 
				cartao.setUsuario_id(usu.getId());
				// Executa a lógica para salvar o cartao atualizado no banco de dados
				CartaoDAO cartaoDAO = new CartaoDAO();
				cartaoDAO.atualizarCartao(cartao);

				// Atualiza as informações na tabela
				List<Cartao> cartoesAtualizados = cartaoDAO.ListarCartoes(usu);
				telaCartoes.atualizarTabela(cartoesAtualizados);

				dispose();
			}
		});
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnEditar.setBorder(null);
		btnEditar.setBackground(new Color(40, 66, 159));
		btnEditar.setBounds(87, 270, 89, 23);
		contentPane.add(btnEditar);

	}

}
