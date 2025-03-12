package Telas;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import com.ibm.icu.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ItemListener;

import Classes.Cartao;
import Classes.Despesa;
import Classes.Usuario;
import DAO.CartaoDAO;
import DAO.DespesaDAO;
import DAO.FaturaDAO;
import DAO.UsuarioDAO;
import Swing.CentralizedTableCellRenderer;
import Swing.CustomHeaderRenderer;
import Swing.RoundedButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class TelaFatura extends JPanel {
	private DefaultTableModel tabelaCartao;
	private DefaultTableModel tabelaDespesas;
	private JTable tableCartao;
	private JTable tableDespesas;
	String cartaoSelecionado;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	double somaFatura;
	List<Despesa> despesasFatura;
	CartaoDAO cartaoDAO = new CartaoDAO();
	Cartao cartao = new Cartao();
	FaturaDAO faturaDAO = new FaturaDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	DespesaDAO despesaDAO = new DespesaDAO();
	private TelaReceita telaReceita;
	private TelaDespesa telaDespesa;
	private TelaCartoes telaCartoes;
	private Calendar cal;

	/**
	 * Create the panel.
	 */
	public TelaFatura(Usuario usu, TelaReceita telaReceita, TelaDespesa telaDespesa, TelaCartoes telaCartoes) {
		this.telaReceita = telaReceita;
		this.telaDespesa = telaDespesa;
		this.telaCartoes = telaCartoes;
		setBounds(0, 0, 691, 498);
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lblFatura = new JLabel("Fatura");
		lblFatura.setBounds(28, 0, 142, 85);
		lblFatura.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblFatura.setForeground(new Color(40, 66, 159));
		add(lblFatura);

		// DefaultTableModel com as colunas do cartão
		tabelaCartao = new DefaultTableModel();
		tabelaCartao.addColumn("ID");
		tabelaCartao.addColumn("Número");
		tabelaCartao.addColumn("Tipo");
		tabelaCartao.addColumn("Bandeira");
		tabelaCartao.addColumn("Limite (R$)");
		tabelaCartao.addColumn("Valor Atual (R$)");
		tabelaCartao.addColumn("Fechamento");

		// JTable com o DefaultTableModel
		tableCartao = new JTable(tabelaCartao);

		// JScrollPane para a JTable
		JScrollPane scrollPane = new JScrollPane(tableCartao);
		scrollPane.setBounds(10, 91, 671, 34);
		add(scrollPane);

		// Renderizador personalizado para centralizar as colunas
		CentralizedTableCellRenderer renderer = new CentralizedTableCellRenderer();

		tableCartao.getColumnModel().getColumn(0).setCellRenderer(renderer); // Ajuste o índice da coluna conforme necessário
		tableCartao.getColumnModel().getColumn(1).setCellRenderer(renderer);
		tableCartao.getColumnModel().getColumn(2).setCellRenderer(renderer);
		tableCartao.getColumnModel().getColumn(3).setCellRenderer(renderer);
		tableCartao.getColumnModel().getColumn(4).setCellRenderer(renderer);
		tableCartao.getColumnModel().getColumn(5).setCellRenderer(renderer);
		tableCartao.getColumnModel().getColumn(6).setCellRenderer(renderer);

		// Mudar cor do cabeçalho, fonte e centralizar texto
		JTableHeader header = tableCartao.getTableHeader();
		header.setDefaultRenderer(new CustomHeaderRenderer());

		// DefaultTableModel com as colunas das despesas
		tabelaDespesas = new DefaultTableModel();
		tabelaDespesas.addColumn("ID");
		tabelaDespesas.addColumn("Categoria");
		tabelaDespesas.addColumn("Valor");
		tabelaDespesas.addColumn("Data");
		tabelaDespesas.addColumn("Cartão");
		tabelaDespesas.addColumn("Parcelas");
		tabelaDespesas.addColumn("Status");
		tabelaDespesas.addColumn("Descrição");

		tableDespesas = new JTable(tabelaDespesas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Impede a edição das células
			}
		};

		// JTable com o DefaultTableModel
		tableDespesas.setRowSorter(new TableRowSorter(tabelaDespesas));

		// JScrollPane para a JTable
		JScrollPane scrollPane2 = new JScrollPane(tableDespesas);
		scrollPane2.setBounds(10, 194, 671, 241);
		add(scrollPane2);

		// Renderizador personalizado para centralizar as colunas
		CentralizedTableCellRenderer renderer2 = new CentralizedTableCellRenderer();

		tableDespesas.getColumnModel().getColumn(0).setCellRenderer(renderer2); // Ajuste o índice da coluna conforme
																			// necessário
		tableDespesas.getColumnModel().getColumn(1).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(2).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(3).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(4).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(5).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(6).setCellRenderer(renderer2);
		tableDespesas.getColumnModel().getColumn(7).setCellRenderer(renderer2);

		tableDespesas.getColumnModel().getColumn(0).setPreferredWidth(50); // Tamanho da coluna em pixel
		tableDespesas.getColumnModel().getColumn(1).setPreferredWidth(75);
		tableDespesas.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableDespesas.getColumnModel().getColumn(3).setPreferredWidth(60);
		tableDespesas.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableDespesas.getColumnModel().getColumn(5).setPreferredWidth(50);
		tableDespesas.getColumnModel().getColumn(6).setPreferredWidth(50);
		tableDespesas.getColumnModel().getColumn(7).setPreferredWidth(75);

		// Mudar cor do cabeçalho, fonte e centralizar texto
		JTableHeader header2 = tableDespesas.getTableHeader();
		header2.setDefaultRenderer(new CustomHeaderRenderer());

		Choice choice = new Choice();
		choice.setBounds(501, 65, 162, 20);
		add(choice);

		CartaoDAO chama = new CartaoDAO();
		List<Cartao> listaCartoes = chama.ListarCartoes(usu);

		choice.add("Selecione");

		for (Cartao cartao : listaCartoes) {
			if ("Crédito".equals(cartao.getTipo())) {
				String opcao = cartao.getBandeira() + " - " + cartao.getNumero();
				choice.add(opcao);
			}
		}

		choice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cartaoSelecionado = choice.getSelectedItem();
					carregarCartoes(cartaoSelecionado);
					carregarDespesas(cartaoSelecionado);
				}
			}
		});

		JLabel lblSelecionarCartao = new JLabel("Selecione o cartão:");
		lblSelecionarCartao.setBounds(501, 45, 162, 14);
		lblSelecionarCartao.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(lblSelecionarCartao);

		JLabel lblInfoCartao = new JLabel("Informações do cartão");
		lblInfoCartao.setBounds(28, 75, 173, 14);
		lblInfoCartao.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(lblInfoCartao);

		JLabel lblResumoFatura = new JLabel("Resumo da fatura");
		lblResumoFatura.setBounds(28, 174, 173, 14);
		lblResumoFatura.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(lblResumoFatura);

		Choice chcMeses = new Choice();
		chcMeses.setBounds(501, 168, 162, 20);
		chcMeses.addItem("Total");
		chcMeses.addItem("Janeiro");
		chcMeses.addItem("Fevereiro");
		chcMeses.addItem("Março");
		chcMeses.addItem("Abril");
		chcMeses.addItem("Maio");
		chcMeses.addItem("Junho");
		chcMeses.addItem("Julho");
		chcMeses.addItem("Agosto");
		chcMeses.addItem("Setembro");
		chcMeses.addItem("Outubro");
		chcMeses.addItem("Novembro");
		chcMeses.addItem("Dezembro");
		add(chcMeses);

		JLabel lblSoma = new JLabel("");
		lblSoma.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblSoma.setForeground(new Color(40, 66, 159));
		lblSoma.setHorizontalAlignment(SwingConstants.LEFT);
		lblSoma.setBounds(115, 452, 60, 20);
		add(lblSoma);

		// Adiciona um ItemListener ao Choice para lidar com a seleção do mês
		chcMeses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String mesSelecionado = (String) chcMeses.getSelectedItem();
					if (!mesSelecionado.equals("Total")) {
						int indiceMes = chcMeses.getSelectedIndex() - 1; // Desconta a opção "Total"
						if (indiceMes >= 0) {
							cal = Calendar.getInstance();
							cal.set(Calendar.MONTH, indiceMes);
							somaFatura = carregarDespesasDoMes(cartaoSelecionado, cal.getTime());
							lblSoma.setText("" + somaFatura);
						}
					} else {
						carregarDespesas(cartaoSelecionado);
					}
				}
			}
		});

		JLabel lblSelecionarMes = new JLabel("Selecione o mês:");
		lblSelecionarMes.setBounds(501, 148, 162, 14);
		lblSelecionarMes.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(lblSelecionarMes);

		JLabel lblTotalFatura = new JLabel("Total da Fatura: ");
		lblTotalFatura.setForeground(new Color(40, 66, 159));
		lblTotalFatura.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblTotalFatura.setBounds(10, 455, 106, 14);
		add(lblTotalFatura);

		RoundedButton btnPagar = new RoundedButton("Pagar", 30);
		btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mesSelecionado = (String) chcMeses.getSelectedItem();
				if (!mesSelecionado.equals("Total")) {
					int indiceMes = chcMeses.getSelectedIndex() - 1; // Desconta a opção "Total"
					if (indiceMes >= 0) {
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.MONTH, indiceMes);
						despesasFatura = faturaDAO.obterDespesasDoMes(cal.getTime(), cartaoSelecionado);
					}
				}
				// Adiciona as receitas à tabela
				for (Despesa despesa : despesasFatura) {
					if (despesa.isPago()) {
						JOptionPane.showMessageDialog(null, "A fatura já foi paga!");
					} else {
						usuarioDAO.atualizaSaldo(usuarioDAO.subtraiSaldo(usu, despesa.getValor()));
						telaReceita.novoSaldo(usu.getSaldo());
						faturaDAO.atualizaPago(despesa);
						// Atualiza as informações na tabela
						List<Despesa> despesaAtualizada = despesaDAO.ListarDespesas(usu);
						telaDespesa.atualizarTabela(despesaAtualizada);

						// Atualiza o Valor_Atual do cartão
						cartao = cartaoDAO.obterCartao(despesa.getCartao());
						cartao.setValor_atual(cartao.getValor_atual() - despesa.getValor());
						faturaDAO.atualizaValorAtual(cartao);
						List<Cartao> cartoesAtualizados = cartaoDAO.ListarCartoes(usu);
						telaCartoes.atualizarTabela(cartoesAtualizados);

						carregarDespesasDoMes(cartaoSelecionado, cal.getTime());
					}
				}
			}
		});
		btnPagar.setBounds(574, 449, 89, 23);
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnPagar.setBackground(new Color(40, 66, 159));
		add(btnPagar);

	}

	// Listar os cartões cadastrados do usuário na tabela
	public void carregarCartoes(String cartaoSelecionado) {
		// Limpa a tabela antes de adicionar novos dados
		DefaultTableModel model = (DefaultTableModel) tableCartao.getModel();
		model.setRowCount(0);

		List<Cartao> cartoes = faturaDAO.ListarCartoes(cartaoSelecionado);

		// Adiciona os cartões à tabela
		for (Cartao cartao : cartoes) {
			model.addRow(new Object[] { cartao.getId(), cartao.getNumero(), cartao.getTipo(), cartao.getBandeira(),
					cartao.getLimite(), cartao.getValor_atual(), cartao.getFechamento() });
		}
	}

	public void carregarDespesas(String cartaoSelecionado) {
		// Limpa a tabela antes de adicionar novos dados
		DefaultTableModel model = (DefaultTableModel) tableDespesas.getModel();
		model.setRowCount(0);

		List<Despesa> despesas = faturaDAO.ListarDespesas(cartaoSelecionado);

		// Adiciona as receitas à tabela
		for (Despesa despesa : despesas) {
			String data = dateFormat.format(despesa.getData());
			String pago = "Não";
			if (despesa.isPago()) {
				pago = "Sim";
			} else {
				pago = "Não";
			}
			model.addRow(new Object[] { despesa.getId(), despesa.getCategoria(), despesa.getValor(), data,
					despesa.getCartao(), despesa.getNumeroDeParcelas(), pago, despesa.getDescricao() });
		}
	}

	public double carregarDespesasDoMes(String cartaoSelecionado, Date mesSelecionado) {
		// Limpa a tabela antes de adicionar novos dados
		double soma = 0;
		DefaultTableModel model = (DefaultTableModel) tableDespesas.getModel();
		model.setRowCount(0);

		List<Despesa> despesas = faturaDAO.obterDespesasDoMes(mesSelecionado, cartaoSelecionado);

		// Adiciona as receitas à tabela
		for (Despesa despesa : despesas) {
			soma += despesa.getValor();
			String data = dateFormat.format(despesa.getData());
			String pago = despesa.isPago() ? "Sim" : "Não";
			model.addRow(new Object[] { despesa.getId(), despesa.getCategoria(), despesa.getValor(), data,
					despesa.getCartao(), despesa.getNumeroDeParcelas(), pago, despesa.getDescricao() });
		}
		return soma;
	}
}
