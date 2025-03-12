package Telas;

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

import Classes.Cartao;
import Classes.Receita;
import Classes.Usuario;
import DAO.CartaoDAO;
import DAO.ReceitaDAO;
import Swing.CentralizedTableCellRenderer;
import Swing.CustomHeaderRenderer;
import Swing.PlaceholderTextField;
import Swing.RoundedButton;
import Swing.RoundedLabel;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Choice;

public class TelaCartoes extends JPanel {
	private DefaultTableModel tabelaCartoes;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TelaCartoes(Usuario usu) {
		setBounds(0, 0, 691, 498);
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel lblCartoes = new JLabel("Cartões");
		lblCartoes.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblCartoes.setForeground(new Color(40, 66, 159));
		lblCartoes.setBounds(28, 0, 142, 85);
		add(lblCartoes);

		// DefaultTableModel com as colunas
		tabelaCartoes = new DefaultTableModel();
		tabelaCartoes.addColumn("ID");
		tabelaCartoes.addColumn("Número");
		tabelaCartoes.addColumn("Tipo");
		tabelaCartoes.addColumn("Bandeira");
		tabelaCartoes.addColumn("Limite (R$)");
		tabelaCartoes.addColumn("Valor Atual (R$)");
		tabelaCartoes.addColumn("Fechamento");

		// JTable com o DefaultTableModel
		table = new JTable(tabelaCartoes);

		// JScrollPane para a JTable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(28, 85, 635, 350);
		add(scrollPane);

		// Mostrar na tabela os cartoes já cadastrados do usuário
		carregarCartoes(usu);

		// Renderizador personalizado para centralizar as colunas
		CentralizedTableCellRenderer renderer = new CentralizedTableCellRenderer();

		table.getColumnModel().getColumn(0).setCellRenderer(renderer); // Ajuste o índice da coluna conforme necessário
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		table.getColumnModel().getColumn(6).setCellRenderer(renderer);
		
		ImageIcon imageLupa = new ImageIcon(TelaReceita.class.getResource("/Icons/lupa.png"));
		Image imagemOriginalLupa = imageLupa.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionadaLupa = imagemOriginalLupa.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon lupa = new ImageIcon(imagemRedimensionadaLupa);
		
		JLabel lblLupa = new JLabel(lupa);
		lblLupa.setBounds(317, 43, 22, 22);
		add(lblLupa);
		
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCartoes);
		table.setRowSorter(sorter);
		Comparator<Integer> intComparator = Comparator.comparingInt(Integer::intValue);
		Comparator<Double> doubleComparator = Comparator.comparingDouble(Double::doubleValue);

        sorter.setComparator(0, intComparator);
        sorter.setComparator(1, intComparator);
        sorter.setComparator(4, doubleComparator);
        sorter.setComparator(5, doubleComparator);
        sorter.setComparator(6, intComparator);  

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

		ImageIcon imageIcon3 = new ImageIcon(TelaReceita.class.getResource("/Icons/icon_add.png"));
		Image imagemOriginal3 = imageIcon3.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada3 = imagemOriginal3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon adicionar = new ImageIcon(imagemRedimensionada3);

		JLabel lblAdd = new JLabel(adicionar);
		lblAdd.setBounds(619, 39, 34, 30);
		add(lblAdd);

		// Botão para adicionar cartao
		RoundedButton btnAdd = new RoundedButton("", 25);
		btnAdd.setBorder(null);
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario aux = new Usuario();
				aux.setId(usu.getId());
				aux.setSaldo(usu.getSaldo());
				Cartao cartao = new Cartao();
				TelaCartaoNovo cartaoNovo = new TelaCartaoNovo(cartao, aux, TelaCartoes.this);
				cartaoNovo.setVisible(true);
				cartaoNovo.setLocationRelativeTo(null);
			}
		});
		btnAdd.setBounds(619, 40, 35, 30);
		btnAdd.setBackground(new Color(40, 66, 159));
		add(btnAdd);

		ImageIcon imageIcon = new ImageIcon(TelaReceita.class.getResource("/Icons/icon_lixeira.png"));
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon lixeira = new ImageIcon(imagemRedimensionada);

		JLabel lblLixeira = new JLabel(lixeira);
		lblLixeira.setBounds(567, 39, 34, 30);
		add(lblLixeira);

		// Botão para excluir cartão
		RoundedButton btnExcluir = new RoundedButton("", 25);
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int idCartao = (int) tabelaCartoes.getValueAt(selectedRow, 0); // Suponhamos que o ID esteja na coluna
																				// 0
					CartaoDAO cartaoDAO = new CartaoDAO();
					int resposta = cartaoDAO.excluirCartao(idCartao);
					if (resposta == 0) {
						tabelaCartoes.removeRow(selectedRow);
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
		lblPincel.setBounds(512, 39, 35, 30);
		add(lblPincel);

		// Botão para editar cartão
		RoundedButton btnEditar = new RoundedButton("", 25);
		btnEditar.setBorder(null);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					Cartao cartao = new Cartao();

					int idCartao = (int) tabelaCartoes.getValueAt(selectedRow, 0);
					int numero = (int) tabelaCartoes.getValueAt(selectedRow, 1);
					String tipo = (String) tabelaCartoes.getValueAt(selectedRow, 2);
					String bandeira = (String) tabelaCartoes.getValueAt(selectedRow, 3);
					double limite = (double) tabelaCartoes.getValueAt(selectedRow, 4);
					double valorAtual = (double) tabelaCartoes.getValueAt(selectedRow, 5);
					int fechamento = (int) tabelaCartoes.getValueAt(selectedRow, 6);

					cartao.setId(idCartao);
					cartao.setNumero(numero);
					cartao.setTipo(tipo);
					cartao.setBandeira(bandeira);
					cartao.setLimite(limite);
					cartao.setValor_atual(valorAtual);
					cartao.setFechamento(fechamento);

					TelaEditarCartao cartaoEdit = new TelaEditarCartao(cartao, usu, TelaCartoes.this);
					cartaoEdit.setVisible(true);
					cartaoEdit.setLocationRelativeTo(null);

				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma receita para editar.");
				}
			}
		});

		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBackground(new Color(40, 66, 159));
		btnEditar.setBounds(513, 40, 35, 30);
		add(btnEditar);
	}

	// Listar os cartões cadastrados do usuário na tabela
	public void carregarCartoes(Usuario usuario) {
		// Limpa a tabela antes de adicionar novos dados
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		// Obtém a lista de cartões do usuário
		CartaoDAO cartoesDAO = new CartaoDAO();

		List<Cartao> cartoes = cartoesDAO.ListarCartoes(usuario);

		// Adiciona os cartões à tabela
		for (Cartao cartao : cartoes) {
			model.addRow(new Object[] { cartao.getId(), cartao.getNumero(), cartao.getTipo(), cartao.getBandeira(),
					cartao.getLimite(), cartao.getValor_atual(), cartao.getFechamento() });
		}
	}

	// Atualizar valores nos campos da tabela
	public void atualizarTabela(List<Cartao> cartoes) {
		tabelaCartoes.setRowCount(0); // Limpa todas as linhas existentes na tabela
		for (Cartao cartao : cartoes) {
			tabelaCartoes.addRow(new Object[] { cartao.getId(), cartao.getNumero(), cartao.getTipo(), cartao.getBandeira(),
					cartao.getLimite(), cartao.getValor_atual(), cartao.getFechamento() });
		}
	}

	// Adicionar um novo cartão à tabela
	public void adicionarCartao(Cartao cartao) {
		tabelaCartoes.addRow(new Object[] { cartao.getId(), cartao.getNumero(), cartao.getTipo(), cartao.getBandeira(),
				cartao.getLimite(), cartao.getValor_atual(), cartao.getFechamento() });
	}
	
}
