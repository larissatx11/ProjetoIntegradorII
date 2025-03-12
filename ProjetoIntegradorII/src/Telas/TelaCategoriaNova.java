package Telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Classes.Categoria;
import Classes.Usuario;
import DAO.CategoriaDAO;
import Swing.RoundedButton;

public class TelaCategoriaNova extends JFrame {

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
					TelaCategoria categoria = new TelaCategoria(usu);
					TelaCategoriaNova frame = new TelaCategoriaNova(usu, categoria);
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
	public TelaCategoriaNova(Usuario usu, TelaCategoria telaCategoria) {
		setTitle("Adicionar categoria");
		this.telaCategoria = telaCategoria;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 296, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(23, 76, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(81, 73, 176, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		// Botão para salvar alterações
		RoundedButton btnADD = new RoundedButton("Adicionar", 20);
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Categoria cat = new Categoria();
				 	cat.setNome(txtNome.getText());
				 	cat.setUsuario_id(usu.getId());
			        CategoriaDAO chama = new CategoriaDAO();
			        int idGerado = chama.cadastrar(cat, usu);
			        if (idGerado != 0) {
			            // O cadastro foi bem-sucedido, e você tem o ID gerado em idGerado
			            System.out.println("ID da categoria: " + idGerado);
			            cat.setId(idGerado);
			            telaCategoria.adicionarCategoria(idGerado, cat.getNome());

			        } else {
			            // O cadastro falhou
			        }
			        dispose();
			    }
			});
		btnADD.setBorder(null);
		btnADD.setForeground(Color.WHITE);
		btnADD.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnADD.setBackground(new Color(40, 66, 159));
		btnADD.setBounds(97, 170, 89, 23);
		contentPane.add(btnADD);					
	}
}
