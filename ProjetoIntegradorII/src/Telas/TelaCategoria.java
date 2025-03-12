package Telas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Classes.Categoria;
import Classes.Receita;
import Classes.Usuario;
import DAO.CategoriaDAO;
import DAO.ReceitaDAO;
import Swing.CentralizedTableCellRenderer;
import Swing.CustomHeaderRenderer;
import Swing.PlaceholderTextField;
import Swing.RoundedButton;
import Swing.RoundedLabel;

public class TelaCategoria extends JPanel {
    private DefaultTableModel tabelaCategoria;
    private JTable table;
	/**
	 * Create the panel.
	 */
	public TelaCategoria(Usuario usu) {
		setBounds(0, 0, 691, 498);
		setLayout(null);
		setBackground(Color.WHITE);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblCategoria.setForeground(new Color(40, 66, 159));
		lblCategoria.setBounds(28, 0, 188, 85);
		add(lblCategoria);
		
		 // DefaultTableModel com as colunas id, valor e mês
        tabelaCategoria = new DefaultTableModel();
        tabelaCategoria.addColumn("ID");
        tabelaCategoria.addColumn("Nome");

        // JTable com o DefaultTableModel
        table = new JTable(tabelaCategoria);

        // JScrollPane para a JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(28, 85, 635, 350);
        add(scrollPane);
        
        // Renderizador personalizado para centralizar as colunas
        CentralizedTableCellRenderer renderer = new CentralizedTableCellRenderer();
        
        table.getColumnModel().getColumn(0).setCellRenderer(renderer); // Ajuste o índice da coluna conforme necessário
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
            
        // Mudar cor do cabeçalho, fonte e centralizar texto
        JTableHeader header = table.getTableHeader();     
        header.setDefaultRenderer(new CustomHeaderRenderer());
        
        // Listar as receitas cadastradas do usuário
        carregarCategorias(usu);
        
        ImageIcon imageLupa = new ImageIcon(TelaReceita.class.getResource("/Icons/lupa.png"));
		Image imagemOriginalLupa = imageLupa.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionadaLupa = imagemOriginalLupa.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon lupa = new ImageIcon(imagemRedimensionadaLupa);
		
		JLabel lblLupa = new JLabel(lupa);
		lblLupa.setBounds(317, 43, 22, 22);
		add(lblLupa);
		
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCategoria);
		table.setRowSorter(sorter);
		Comparator<Integer> intComparator = Comparator.comparingInt(Integer::intValue);
        sorter.setComparator(0, intComparator); 

        PlaceholderTextField txtFiltrar = new PlaceholderTextField("Filtrar por campos");
		txtFiltrar.setHorizontalAlignment(SwingConstants.LEFT);
		txtFiltrar.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		txtFiltrar.setColumns(10);
		txtFiltrar.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFiltrar.setBackground(new Color(230, 230, 230));
		txtFiltrar.setBounds(335, 45, 162, 20);
		txtFiltrar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		txtFiltrar.addKeyListener(new KeyAdapter() {
			
			private void filtrar() {
				String busca = txtFiltrar.getText().trim();
				
				if(busca.length() == 0) {
					sorter.setRowFilter(null);
				}else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + busca));
				}
			}
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar();
			}
		});
		
		   add(txtFiltrar);
		   
		RoundedLabel rndLblFundo = new RoundedLabel(20, 20);
        rndLblFundo.setBackground(new Color(230, 230, 230));
		rndLblFundo.setBounds(317, 42, 182, 25);
        add(rndLblFundo);
        
        ImageIcon imageIcon3 =new ImageIcon(TelaReceita.class.getResource("/Icons/icon_add.png"));
		Image imagemOriginal3 = imageIcon3.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada3 = imagemOriginal3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon adicionar = new ImageIcon(imagemRedimensionada3);
		
        JLabel lblAdd = new JLabel(adicionar);
        lblAdd.setBounds(619, 39, 34, 30);
        add(lblAdd);
        
        // Botão para adicionar receita
        RoundedButton btnAdd = new RoundedButton("", 25);
        btnAdd.setBorder(null);
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Usuario aux = new Usuario();
        		aux.setId(usu.getId());
        		TelaCategoriaNova catNova = new TelaCategoriaNova(aux, TelaCategoria.this);
        		catNova.setVisible(true);
        		catNova.setLocationRelativeTo(null);
        	}
        });
        btnAdd.setBounds(619, 40, 35, 30);
        btnAdd.setBackground(new Color(40, 66, 159));
        add(btnAdd);

        ImageIcon imageIcon =new ImageIcon(TelaReceita.class.getResource("/Icons/icon_lixeira.png"));
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon lixeira = new ImageIcon(imagemRedimensionada);
		
        JLabel lblLixeira = new JLabel(lixeira);
        lblLixeira.setBounds(567, 39, 34, 30);
        add(lblLixeira);
        
        // Botão para excluir receita
        RoundedButton btnExcluir = new RoundedButton("", 25);
        btnExcluir.setBorder(null);
        btnExcluir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 int selectedRow = table.getSelectedRow();
        	        if (selectedRow >= 0) {
        	            int idCategoria = (int) tabelaCategoria.getValueAt(selectedRow, 0); // Pegar o ID que está na coluna 0
        	            CategoriaDAO categoriaDAO = new CategoriaDAO();
        	            categoriaDAO.excluirReceita(idCategoria);

                        tabelaCategoria.removeRow(selectedRow);
        	        } else {
        	            JOptionPane.showMessageDialog(null, "Selecione uma receita para excluir.");
        	        }
        	}
        });
        btnExcluir.setLayout(new BorderLayout());
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setBackground(new Color(40, 66, 159));
        btnExcluir.setBounds(567, 40, 35, 30);
        add(btnExcluir);
        
        ImageIcon imageIcon2 = new ImageIcon(TelaReceita.class.getResource("/Icons/editar.png"));
		Image imagemOriginal2 = imageIcon2.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada2 = imagemOriginal2.getScaledInstance(19, 19, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon editar = new ImageIcon(imagemRedimensionada2);
		
        JLabel lblPincel = new JLabel(editar);
        lblPincel.setBounds(512, 39, 35, 30);
        add(lblPincel);
        
        // Botão para editar receita
        RoundedButton btnEditar = new RoundedButton("", 25);
        btnEditar.setBorder(null);
        btnEditar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 int selectedRow = table.getSelectedRow();
        	        if (selectedRow >= 0) {
        	        	Categoria categoria = new Categoria();
        	            int idCategoria = (int) tabelaCategoria.getValueAt(selectedRow, 0);
                    	String nome = (String) tabelaCategoria.getValueAt(selectedRow, 1);
                    	categoria.setId(idCategoria);
                    	categoria.setNome(nome);
                		TelaEditarCategoria catEdit = new TelaEditarCategoria(categoria, usu, TelaCategoria.this);
                		catEdit.setVisible(true);
                		catEdit.setLocationRelativeTo(null);
                           	        
                		} else {
        	            JOptionPane.showMessageDialog(null, "Selecione uma categoria para editar.");
        	        }
        	}
        });
       
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(40, 66, 159));
        btnEditar.setBounds(513, 40, 35, 30);
        add(btnEditar);
	}
     // Listar as receitas cadastradas do usuário na tabela
    	public void carregarCategorias(Usuario usuario) {
    	    // Limpa a tabela antes de adicionar novos dados
    	    DefaultTableModel model = (DefaultTableModel) table.getModel();
    	    model.setRowCount(0);
    	    
    	    // Obtém a lista de receitas do usuário
    	    CategoriaDAO catDAO = new CategoriaDAO();
    	   
    	    List<Categoria> categorias = catDAO.ListarReceitas(usuario);
    	    
    	    // Adiciona as receitas à tabela
    	    for (Categoria categoria : categorias) {
    	        model.addRow(new Object[]{categoria.getId(), categoria.getNome()});
    	    }
    	}

    	// Atualizar valores nos campos da tabela
    	public void atualizarTabela(List<Categoria> categorias) {
    	    tabelaCategoria.setRowCount(0); // Limpa todas as linhas existentes na tabela
    	    for (Categoria categoria : categorias) {
    	        tabelaCategoria.addRow(new Object[]{categoria.getId(), categoria.getNome()});
    	    }
    	}

    	 // Adicionar uma nova receita à tabela
        public void adicionarCategoria(int id, String nome) {
            // Adicione uma nova linha à tabela com os dados da receita
            tabelaCategoria.addRow(new Object[]{id, nome});
       
	}
}
