package Telas;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Classes.Cartao;
import Classes.Despesa;
import Classes.Usuario;
import Conexao.ModuloConexao;
import DAO.CartaoDAO;
import DAO.DespesaDAO;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import Swing.RoundedButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaDespesaNova extends JFrame {

	private JPanel contentPane;
	private JTextField txtValor;
	private JTextField txtDescricao;
	private TelaDespesa telaDespesa;
	private TelaReceita telaReceita;
	private TelaCartoes telaCartoes;
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtParcelas;
	JFormattedTextField txtData;
	int numero;
	CartaoDAO cartoesDAO = new CartaoDAO();
	Cartao cartao = new Cartao();
	Despesa despesa = new Despesa();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Usuario usu = new Usuario();
					TelaCartoes telaCartoes = new TelaCartoes(usu);
					TelaReceita telaReceita = new TelaReceita(usu, telaCartoes);
					TelaDespesa telaDespesa = new TelaDespesa(usu, telaReceita, telaCartoes);

					TelaDespesaNova frame = new TelaDespesaNova(usu, telaDespesa, telaReceita, telaCartoes);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void formatarCampo() {
		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			mask.install(txtData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 */
	public TelaDespesaNova(Usuario usu, TelaDespesa telaDespesa, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaDespesa = telaDespesa;
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Despesa nova");
		setBounds(100, 115, 270, 386);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblCategoria.setBounds(31, 10, 66, 24);
		contentPane.add(lblCategoria);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblValor.setBounds(31, 226, 46, 14);
		contentPane.add(lblValor);

		txtValor = new JTextField();
		txtValor.setColumns(10);
		txtValor.setBounds(31, 239, 181, 20);
		contentPane.add(txtValor);

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblDescricao.setBounds(31, 262, 81, 14);
		contentPane.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(31, 281, 181, 20);
		contentPane.add(txtDescricao);

		Choice chcCategoria = new Choice();
		chcCategoria.setBounds(31, 35, 181, 20);
		contentPane.add(chcCategoria);

		Choice chcFormaDePagamento = new Choice();
		chcFormaDePagamento.setBounds(31, 76, 181, 20);
		contentPane.add(chcFormaDePagamento);
		chcFormaDePagamento.add("Selecione");
		chcFormaDePagamento.add("Crédito");
		chcFormaDePagamento.add("Débito");
		chcFormaDePagamento.add("Dinheiro");

		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblFormaDePagamento.setBounds(31, 59, 122, 19);
		contentPane.add(lblFormaDePagamento);

		JLabel lblCartoes = new JLabel("Cartões");
		lblCartoes.setHorizontalAlignment(SwingConstants.LEFT);
		lblCartoes.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblCartoes.setBounds(31, 100, 122, 12);
		contentPane.add(lblCartoes);

		Choice chcCartoes = new Choice();
		chcCartoes.setBounds(31, 118, 181, 18);
		contentPane.add(chcCartoes);

		JLabel lblParcelas = new JLabel("Número de parcelas");
		lblParcelas.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblParcelas.setBounds(31, 142, 122, 14);
		contentPane.add(lblParcelas);

		txtParcelas = new JTextField();
		txtParcelas.setColumns(10);
		txtParcelas.setBounds(31, 157, 181, 20);
		contentPane.add(txtParcelas);

		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblData.setBounds(31, 180, 46, 14);
		contentPane.add(lblData);

		txtData = new JFormattedTextField();
		txtData.setHorizontalAlignment(SwingConstants.LEADING);
		txtData.setColumns(10);
		txtData.setBounds(31, 200, 181, 20);
		contentPane.add(txtData);

		RoundedButton btnAdd = new RoundedButton("Adicionar", 20);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				despesa.setCategoria(chcCategoria.getSelectedItem());
				despesa.setDescricao(txtDescricao.getText());
				if (chcFormaDePagamento.getSelectedItem().equals("Dinheiro")) {
					despesa.setCartao("Dinheiro");
				} else {
					despesa.setCartao(chcCartoes.getSelectedItem());
				}

				if (txtParcelas.getText().equals("")) {
					despesa.setNumeroDeParcelas(0);
				} else {
					despesa.setNumeroDeParcelas(Integer.parseInt(txtParcelas.getText()));
				}
				despesa.setValor(Float.parseFloat(txtValor.getText()));
				despesa.setFormaDePagamento(chcFormaDePagamento.getSelectedItem());
				despesa.setUsuario_id(usu.getId());

				// Restante do código para cadastrar a despesa no banco

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String aux = txtData.getText();
				Date data = null;
				try {
					data = formato.parse(aux);
//					despesa.setData(new java.util.Date(data.getTime()));
					despesa.setData(new java.sql.Date(data.getTime()));
				} catch (ParseException e1) {
					Logger.getLogger(TelaReceitaNova.class.getName()).log(Level.SEVERE, null, e1);
				}

				DespesaDAO chama = new DespesaDAO();
				int verifica = chama.verificarSaldo(despesa, usu, telaReceita, telaCartoes);
				if (verifica != 0) {
					telaDespesa.adicionarDespesa(despesa);
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível cadastrar a despesa, tente novamente!");
				}
				dispose();
			}
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnAdd.setBorder(null);
		btnAdd.setBackground(new Color(40, 66, 159));
		btnAdd.setBounds(80, 313, 89, 23);
		contentPane.add(btnAdd);

		formatarCampo();

		// Choice de Categorias
		conexao = ModuloConexao.conector();
		String sql = "select * from categoria";
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			ArrayList<String> lista = new ArrayList<>();
			chcCategoria.add("Selecione");
			while (rs.next()) {
				String opcao = rs.getString("nome");
				lista.add(opcao);
			}
			for (String categoria : lista) {
				chcCategoria.add(categoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		conexao = ModuloConexao.conector();

		CartaoDAO chama = new CartaoDAO();
		List<Cartao> listaCartoes = new ArrayList<>();
		listaCartoes = chama.ListarCartoes(usu);

		final List<Cartao> finalListaCartoes = listaCartoes;
		chcCartoes.add("Selecione");
		chcFormaDePagamento.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					String formaSelecionada = chcFormaDePagamento.getSelectedItem();
					chcCartoes.removeAll();
					chcCartoes.add("Selecione");
					chcCartoes.setEnabled(true);
					txtParcelas.setEnabled(true);
					for (Cartao cartao : finalListaCartoes) {
						String nome = cartao.getBandeira();
						numero = cartao.getNumero();
						String opcao = nome + " - " + numero;
						if (formaSelecionada.equals("Crédito") && cartao.getTipo().equals("Crédito")) {
							chcCartoes.add(opcao);
						} else if (formaSelecionada.equals("Débito") && cartao.getTipo().equals("Débito")) {
							chcCartoes.add(opcao);
							txtParcelas.setText(null);
							txtParcelas.setEnabled(false);
							despesa.setPago(true);
						} else if (formaSelecionada.equals("Dinheiro")) {
							chcCartoes.setEnabled(false);
							txtParcelas.setText(null);
							txtParcelas.setEnabled(false);
							despesa.setPago(true);
						}
					}
				}
			}
		});
	}
}