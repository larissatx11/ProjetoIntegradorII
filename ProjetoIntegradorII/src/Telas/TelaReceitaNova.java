package Telas;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.text.SimpleDateFormat;

import Classes.Cartao;
import Classes.Receita;
import Classes.Usuario;
import DAO.ReceitaDAO;
import Swing.RoundedButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

public class TelaReceitaNova extends JFrame {

	private JPanel contentPane;
	private JTextField txtValor;
	private JFormattedTextField txtMes;
	private TelaReceita telaReceita;
	private TelaCartoes telaCartoes;

	// private JFormattedTextField txtData;
	/**
	 * Launch the application.
	 */
	private void formatarCampo() {
		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			mask.install(txtMes);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario usu = new Usuario();
					TelaCartoes telaCartoes = new TelaCartoes(usu);
					TelaReceita telaReceita = new TelaReceita(usu, telaCartoes);
					TelaReceitaNova frame = new TelaReceitaNova(usu, telaReceita, telaCartoes);
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

	public TelaReceitaNova(Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;

		setTitle("Adicionar receita");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 296, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValor.setBounds(41, 43, 46, 14);
		contentPane.add(lblValor);

		JLabel lblMes = new JLabel("Data:");
		lblMes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMes.setBounds(41, 115, 46, 14);
		contentPane.add(lblMes);

		txtValor = new JTextField();
		txtValor.setBounds(81, 40, 156, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		txtMes = new JFormattedTextField();
		txtMes.setHorizontalAlignment(SwingConstants.LEADING);
		txtMes.setBounds(81, 112, 156, 20);
		contentPane.add(txtMes);
		txtMes.setColumns(10);

		formatarCampo();

		// Botão para salvar alterações
		RoundedButton btnADD = new RoundedButton("Adicionar", 20);
		btnADD.setBorder(null);
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Receita rec = new Receita();
				rec.setValor(Float.parseFloat(txtValor.getText()));
				// rec.setMes(Integer.parseInt(txtMes.getText()));
				rec.setUsuario_id(usu.getId());

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				String coe = txtMes.getText();
				Date data = null;
				try {
					data = formato.parse(coe);
					// rec.setMes(new java.util.Date(data.getTime()));
					rec.setMes(new java.sql.Date(data.getTime()));
				} catch (ParseException e1) {
					Logger.getLogger(TelaReceitaNova.class.getName()).log(Level.SEVERE, null, e1);
				}

				ReceitaDAO chama = new ReceitaDAO();
				int idGerado = chama.cadastrar(rec, usu, telaReceita, telaCartoes);
				if (idGerado != 0) {
					// O cadastro foi bem-sucedido, e você tem o ID gerado em idGerado
					System.out.println("ID da receita: " + idGerado);
					rec.setId(idGerado);
					telaReceita.adicionarReceita(idGerado, rec.getValor(), rec.getMes());
				} else {
					// O cadastro falhou
				}
				dispose();
			}
		});
		btnADD.setForeground(Color.WHITE);
		btnADD.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnADD.setBackground(new Color(40, 66, 159));
		btnADD.setBounds(96, 187, 89, 23);
		contentPane.add(btnADD);
	}
}
