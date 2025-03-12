package Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
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

import Classes.Despesa;
import Classes.Usuario;
import DAO.DespesaDAO;
import Swing.CentralizedTableCellRenderer;
import Swing.CustomHeaderRenderer;
import Swing.PlaceholderTextField;
import Swing.RoundedButton;
import Swing.RoundedLabel;

public class TelaDespesa extends JPanel {
    private DefaultTableModel tabelaDespesas;
    private JTable table;
    public JLabel lblSaldo;
   // private static final SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
    Despesa despesa = new Despesa();
    private TelaReceita telaReceita;
    private TelaCartoes telaCartoes;
	/**
	 * Create the panel.
	 */
	public TelaDespesa(Usuario usu, TelaReceita telaReceita, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaCartoes = telaCartoes;
		
		setBounds(0, 0, 691, 498);
		setLayout(null);
		setBackground(Color.WHITE);
		
		JLabel lblDespesas = new JLabel("Despesas");
		lblDespesas.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblDespesas.setForeground(new Color(40, 66, 159));
		lblDespesas.setBounds(28, 0, 142, 85);
		add(lblDespesas);
		
		tabelaDespesas = new DefaultTableModel() {

        	//Altera o retorno para que a data volte como Date
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                    	return Double.class;
                    case 3:
                    	return Date.class;
                    case 4:
                    	return String.class;
                    case 5:
                    	return String.class;
                    case 6:
                    	return Integer.class;
                    case 7:
                    	return String.class;
                    default:
                        return String.class;
                }
            }
        };;
		
		 // DefaultTableModel com as colunas
        tabelaDespesas = new DefaultTableModel();
        tabelaDespesas.addColumn("ID");
        tabelaDespesas.addColumn("Categoria");
        tabelaDespesas.addColumn("Valor");
        tabelaDespesas.addColumn("Data");
        tabelaDespesas.addColumn("Forma de pagamento");
        tabelaDespesas.addColumn("Cartão");
        tabelaDespesas.addColumn("Parcelas");
        tabelaDespesas.addColumn("Status");
        tabelaDespesas.addColumn("Descrição");

        table = new JTable(tabelaDespesas) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // Impede a edição das células
		    }
		};
        
        // JTable com o DefaultTableModel
        //table.setRowSorter(new TableRowSorter(tabelaDespesas));

        // JScrollPane para a JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 85, 671, 350);
        add(scrollPane);
        
        
        // Renderizador personalizado para centralizar as colunas
        CentralizedTableCellRenderer renderer = new CentralizedTableCellRenderer();
        
        table.getColumnModel().getColumn(0).setCellRenderer(renderer); // Ajuste o índice da coluna conforme necessário
        table.getColumnModel().getColumn(1).setCellRenderer(renderer);
        table.getColumnModel().getColumn(2).setCellRenderer(renderer);
        table.getColumnModel().getColumn(3).setCellRenderer(renderer);
        table.getColumnModel().getColumn(4).setCellRenderer(renderer);
        table.getColumnModel().getColumn(5).setCellRenderer(renderer);
        table.getColumnModel().getColumn(6).setCellRenderer(renderer);
        table.getColumnModel().getColumn(7).setCellRenderer(renderer);
        table.getColumnModel().getColumn(8).setCellRenderer(renderer);
          
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // Tamanho da coluna em pixel
        table.getColumnModel().getColumn(1).setPreferredWidth(75);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(75); 
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(50); 
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(75);     
        
        carregarDespesas(usu);
		
		ImageIcon imageLupa = new ImageIcon(TelaReceita.class.getResource("/Icons/lupa.png"));
		Image imagemOriginalLupa = imageLupa.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionadaLupa = imagemOriginalLupa.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon lupa = new ImageIcon(imagemRedimensionadaLupa);
		
		JLabel lblLupa = new JLabel(lupa);
		lblLupa.setBounds(317, 43, 22, 22);
		add(lblLupa);
		
		 final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaDespesas);
			table.setRowSorter(sorter);
			Comparator<Integer> intComparator = Comparator.comparingInt(Integer::intValue);
			Comparator<Double> doubleComparator = Comparator.comparingDouble(Double::doubleValue);

	        sorter.setComparator(0, intComparator);
	        sorter.setComparator(2, doubleComparator);
	        
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
	        
        // Mudar cor do cabeçalho, fonte e centralizar texto
        JTableHeader header = table.getTableHeader();     
        header.setDefaultRenderer(new CustomHeaderRenderer());
        
        ImageIcon imageIcon3 =new ImageIcon(TelaReceita.class.getResource("/Icons/icon_add.png"));
		Image imagemOriginal3 = imageIcon3.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada3 = imagemOriginal3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon adicionar = new ImageIcon(imagemRedimensionada3);
		
        JLabel lblAdd = new JLabel(adicionar);
        lblAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblAdd.setBounds(619, 39, 34, 30);
        add(lblAdd);
        
        // Botão para adicionar receita
        RoundedButton btnAdd = new RoundedButton("", 25);
        btnAdd.setBorder(null);
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TelaDespesaNova recNova = new TelaDespesaNova(usu, TelaDespesa.this, telaReceita, telaCartoes);
        		recNova.setVisible(true);
        		recNova.setLocationRelativeTo(null);
        		
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
        lblLixeira.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLixeira.setBounds(567, 39, 34, 30);
        add(lblLixeira);
        
        // Botão para excluir receita
        RoundedButton btnExcluir = new RoundedButton("", 25);
        btnExcluir.setBorder(null);
        btnExcluir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 int selectedRow = table.getSelectedRow();
        	        if (selectedRow >= 0) {
        	            Despesa despesa = new Despesa();
        	            despesa.setId((int) tabelaDespesas.getValueAt(selectedRow, 0));
        	            despesa.setValor((double) tabelaDespesas.getValueAt(selectedRow, 2));
        	            despesa.setCartao((String) tabelaDespesas.getValueAt(selectedRow, 5));
        	            String pago = (String) tabelaDespesas.getValueAt(selectedRow, 7);
        	            if(pago.equals("Sim")) {
        	            	despesa.setPago(true); 
        	            }else {
        	            	despesa.setPago(false); 
        	            }        	                   	         
        	            DespesaDAO despesaDAO = new DespesaDAO();
                        int resposta = despesaDAO.excluirDespesa(despesa, usu, telaReceita, telaCartoes);
                        if(resposta == 1) {
                        	tabelaDespesas.removeRow(selectedRow);
                        }                  
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
        lblPincel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblPincel.setBounds(512, 39, 35, 30);
        add(lblPincel);
        
        // Botão para editar despesa
        RoundedButton btnEditar = new RoundedButton("", 25);
        btnEditar.setBorder(null);
        btnEditar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 int selectedRow = table.getSelectedRow();
        	        if (selectedRow >= 0) {
        	        	despesa.setId((int) tabelaDespesas.getValueAt(selectedRow, 0));
        	        	despesa.setCategoria((String)tabelaDespesas.getValueAt(selectedRow, 1));
                    	despesa.setValor((double) tabelaDespesas.getValueAt(selectedRow, 2));
                    	/*
                    	String dataString = (String) tabelaDespesas.getValueAt(selectedRow, 3);
    					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    					try {
    						Date dateValue = dateFormat.parse(dataString);
    						despesa.setData(dateValue);
    						System.out.println("Data formatada: " + dateFormat.format(dateValue));
    					} catch (ParseException a) {
    						a.printStackTrace();    						
    					}*/
						despesa.setData((Date) tabelaDespesas.getValueAt(selectedRow, 3));
    					despesa.setFormaDePagamento((String)tabelaDespesas.getValueAt(selectedRow, 4));
    					despesa.setCartao((String)tabelaDespesas.getValueAt(selectedRow, 5));
    					despesa.setNumeroDeParcelas((int)tabelaDespesas.getValueAt(selectedRow, 6));               
                    	if(tabelaDespesas.getValueAt(selectedRow, 7).equals("Sim")) {
                    		despesa.setPago(true);
                    	} else if (tabelaDespesas.getValueAt(selectedRow, 7).equals("Não")) {
                    		despesa.setPago(false);
                    	}
                    	despesa.setDescricao((String)tabelaDespesas.getValueAt(selectedRow, 8));                    	                    	                    	
                    	TelaEditarDespesa despesaEdit = new TelaEditarDespesa(despesa, usu, TelaDespesa.this, telaReceita, telaCartoes);
                    	despesaEdit.setVisible(true);
                    	despesaEdit.setLocationRelativeTo(null);
                	} else {
        	            JOptionPane.showMessageDialog(null, "Selecione uma despesa para editar.");
        	        }
        	    
        	}
        });
       
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(40, 66, 159));
        btnEditar.setBounds(513, 40, 35, 30);
        add(btnEditar);

	}

	public void carregarDespesas(Usuario usuario) {
		// Limpa a tabela antes de adicionar novos dados
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		// Obtém a lista de receitas do usuário
		DespesaDAO despesaDAO = new DespesaDAO();

		List<Despesa> despesas = despesaDAO.ListarDespesas(usuario);

		// Adiciona as receitas à tabela
		for (Despesa despesa : despesas) {
			String pago = "Não";
			if(despesa.isPago()) {
				pago = "Sim";
			} else {
				pago = "Não";
			}
			model.addRow(new Object[] { despesa.getId(), despesa.getCategoria(), despesa.getValor(), despesa.getData(), 
					despesa.getFormaDePagamento(), despesa.getCartao(), despesa.getNumeroDeParcelas(),
					pago, despesa.getDescricao()});
		}
	}
	
	// Atualizar valores nos campos da tabela
	public void atualizarTabela(List<Despesa> despesas) {
		tabelaDespesas.setRowCount(0);
		for (Despesa despesa : despesas) {
			String pago = "Não";
			if(despesa.isPago()) {
				pago = "Sim";
			} else {
				pago = "Não";
			}
			tabelaDespesas.addRow(new Object[] { despesa.getId(), despesa.getCategoria(), despesa.getValor(), despesa.getData(),
					despesa.getFormaDePagamento(), despesa.getCartao(), despesa.getNumeroDeParcelas(), 
					pago, despesa.getDescricao()});
		}
	}
	
	// Adicionar uma nova despesa à tabela
	public void adicionarDespesa(Despesa despesa) {
		String pago = "Não";
		if(despesa.isPago()) {
			pago = "Sim";
		} else {
			pago = "Não";
		}
		tabelaDespesas.addRow(new Object[] {despesa.getId(), despesa.getCategoria(), despesa.getValor(), despesa.getData(),
				despesa.getFormaDePagamento(), despesa.getCartao(), despesa.getNumeroDeParcelas(), 
				pago, despesa.getDescricao()});
	}

}
