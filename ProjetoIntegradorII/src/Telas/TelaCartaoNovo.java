package Telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Classes.Cartao;
import Classes.Categoria;
import Classes.Receita;
import Classes.Usuario;
import DAO.CartaoDAO;
import DAO.CategoriaDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Choice;
import java.awt.Color;
import Swing.RoundedButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TelaCartaoNovo extends JFrame {

	private JPanel contentPane;
	private JTextField txtNumero;
	private JTextField txtLimite;
	private JTextField txtFechamento;
	private TelaCartoes telaCartoes;
	CartaoDAO cartoesDAO = new CartaoDAO();

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
					TelaCartaoNovo frame = new TelaCartaoNovo(c, usu, telaCartoes);
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
	public TelaCartaoNovo(Cartao cartao, Usuario usu, TelaCartoes telaCartoes) {
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

		txtNumero = new JTextField();
		txtNumero.setBounds(37, 45, 181, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);

		txtLimite = new JTextField();
		txtLimite.setBounds(37, 91, 181, 20);
		contentPane.add(txtLimite);
		txtLimite.setColumns(10);

		txtFechamento = new JTextField();
		txtFechamento.setBounds(37, 138, 181, 20);
		contentPane.add(txtFechamento);
		txtFechamento.setColumns(10);

		// Criar escolhas (Choice) para "Tipo" e "Bandeira" e adicionar opções
		Choice choiceBandeira = new Choice();
		choiceBandeira.setBounds(37, 230, 181, 20);
		choiceBandeira.add("Selecione");
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
		choiceTipo.add("Selecione");
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

		RoundedButton btnADD = new RoundedButton("Adicionar", 20);
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartao.setNumero(Integer.parseInt(txtNumero.getText()));
				cartao.setFechamento(Integer.parseInt(txtFechamento.getText()));
				if (cartao.getTipo().equals("Débito")) {
					cartao.setLimite(usu.getSaldo());
				} else {
					cartao.setLimite(Double.parseDouble(txtLimite.getText()));
				}
				cartao.setUsuario_id(usu.getId());
				cartao.setValor_atual(0.0);

				CartaoDAO chama = new CartaoDAO();
				int idGerado = chama.cadastrar(cartao);
				if (idGerado != 0) {
					// O cadastro foi bem-sucedido, e você tem o ID gerado em idGerado
					System.out.println("ID do cartao: " + idGerado);
					cartao.setId(idGerado);
					telaCartoes.adicionarCartao(cartao);

				} else {
					JOptionPane.showMessageDialog(null, "Falha ao cadastrar o cartão.");
				}
				dispose();
			}
		});
		btnADD.setForeground(Color.WHITE);
		btnADD.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnADD.setBorder(null);
		btnADD.setBackground(new Color(40, 66, 159));
		btnADD.setBounds(87, 270, 89, 23);
		contentPane.add(btnADD);
	}
}
