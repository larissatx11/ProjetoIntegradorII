package Telas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Classes.Categoria;
import Classes.Receita;
import Classes.Usuario;
import DAO.CategoriaDAO;
import DAO.ReceitaDAO;
import Swing.RoundedButton;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class TelaEditarCategoria extends JFrame {

	private JPanel contentPane;
	private TelaCategoria telaCategoria;
	private JTextField txtNome;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario usu = new Usuario();
					TelaCategoria telaCategoria = new TelaCategoria(usu);
					Categoria categoria = new Categoria();
					TelaEditarCategoria frame = new TelaEditarCategoria(categoria, usu, telaCategoria);
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
	public TelaEditarCategoria(Categoria categoria, Usuario usu, TelaCategoria telaCategoria) {
		setTitle("Editar categoria");
		setBackground(new Color(40, 66, 159));
		this.telaCategoria = telaCategoria;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 275, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNome.setBounds(23, 100, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField(categoria.getNome());
		txtNome.setBounds(67, 97, 168, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		// Botão que chama o método atualizarReceita
		RoundedButton btnEditar = new RoundedButton("Salvar", 20);
		btnEditar.setBorder(null);
		btnEditar.setForeground(new Color(255, 255, 255));
		btnEditar.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
	                // Obtém os novos valores dos campos de texto
	                String novoNome = txtNome.getText();

	                // Atualiza os atributos da receita
	                categoria.setNome(novoNome);

	                // Executa a lógica para salvar a receita atualizada no banco de dados
	                CategoriaDAO catDAO = new CategoriaDAO();
	                catDAO.atualizarReceita(categoria);
	                
	                // Atualiza as informações na tabela
	                List<Categoria> categoriasAtualizadas = catDAO.ListarReceitas(usu);
	                telaCategoria.atualizarTabela(categoriasAtualizadas);

	                // Fecha a janela de edição
	                dispose();	          
			}
		});
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
