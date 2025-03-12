package Telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Classes.Cartao;
import Classes.Receita;
import Classes.Usuario;
import DAO.ReceitaDAO;
import DAO.UsuarioDAO;
import Swing.RoundedButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class TelaEditarReceita extends JFrame {

	private JPanel contentPane;
	private TelaReceita telaReceita;
	private TelaCartoes telaCartoes;
	private JTextField txtValor;
	JFormattedTextField txtMes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario usu = new Usuario();
					Receita receita = new Receita();					
					TelaCartoes telaCartoes = new TelaCartoes(usu);
					TelaReceita telaReceita = new TelaReceita(usu, telaCartoes);
					TelaEditarReceita frame = new TelaEditarReceita(receita, usu, telaReceita, telaCartoes);
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
	public TelaEditarReceita(Receita receita, Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		setTitle("Editar Receita");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 275, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValor.setBounds(28, 66, 46, 14);
		contentPane.add(lblValor);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblData.setBounds(28, 116, 46, 14);
		contentPane.add(lblData);

		txtValor = new JTextField(String.valueOf(receita.getValor()));
		txtValor.setBounds(78, 63, 145, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateValue = receita.getMes();
		String formattedDate = dateFormat.format(dateValue);

		txtMes = new JFormattedTextField(formattedDate);
		txtMes.setBounds(78, 113, 145, 20);
		contentPane.add(txtMes);
		txtMes.setColumns(10);

		// Botão que chama o método atualizarReceita
		RoundedButton btnEditar = new RoundedButton("Salvar", 20);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtém os novos valores dos campos de texto
				float novoValor = Float.parseFloat(txtValor.getText());
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String dataStr = txtMes.getText();
					Date data = formato.parse(dataStr);
					receita.setMes(data);

				} catch (ParseException ex) {
					// Trate exceções de parsing de data, se necessário
				}

				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.subtraiSaldo(usu, receita.getValor());
				
				// Atualiza os atributos da receita
				receita.setValor(novoValor);
				
				// Executa a lógica para salvar a receita atualizada no banco de dados
				ReceitaDAO receitaDAO = new ReceitaDAO();
				receitaDAO.atualizarReceita(receita, usu, telaReceita, telaCartoes);

				// Atualiza as informações na tabela
				List<Receita> receitasAtualizadas = receitaDAO.ListarReceitas(usu);
				telaReceita.atualizarTabela(receitasAtualizadas);

				// Fecha a janela de edição
				dispose();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setForeground(new Color(255, 255, 255));
		btnEditar.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		btnEditar.setBounds(88, 181, 89, 23);
		btnEditar.setBackground(new Color(40, 66, 159));
		contentPane.add(btnEditar);

		JLabel lblAtualizar = new JLabel("Atualizar informações:");
		lblAtualizar.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		lblAtualizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtualizar.setBounds(44, 11, 179, 14);
		contentPane.add(lblAtualizar);
	}
}
