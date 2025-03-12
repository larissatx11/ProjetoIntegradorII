package Telas;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Classes.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.awt.Cursor;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	// Criar variaveis para as janelas que serão chamadas
	private TelaHome telaHome;
	private TelaReceita telaReceita;
	private TelaDespesa telaDespesa;
	private TelaCartoes telaCartoes;
	private TelaPerfil telaPerfil;
	private TelaCategoria telaCategoria;
	private TelaFatura telaFatura;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario usu = new Usuario();
					TelaPrincipal frame = new TelaPrincipal(usu);
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

	public TelaPrincipal(Usuario usu) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 540);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		// Atribui às variaveis das janelas a classe de cada uma
		telaHome = new TelaHome();
		telaCartoes = new TelaCartoes(usu);
		telaReceita = new TelaReceita(usu, telaCartoes);
		telaDespesa = new TelaDespesa(usu, telaReceita, telaCartoes);
		telaPerfil = new TelaPerfil(usu);
		telaCategoria = new TelaCategoria(usu);
		telaFatura = new TelaFatura(usu, telaReceita, telaDespesa, telaCartoes);

		JPanel paneMenu = new JPanel();
		paneMenu.setBounds(0, 0, 150, 501);
		paneMenu.setBackground(new Color(40, 66, 159));
		contentPane.add(paneMenu);
		paneMenu.setLayout(null);

		JPanel panelPerfil = new JPanel();
		panelPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelPerfil.addMouseListener(new PanelButtonMouseAdapter(panelPerfil) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaPerfil);
			}
		});
		panelPerfil.setBorder(null);
		panelPerfil.setBackground(new Color(40, 66, 159));
		panelPerfil.setBounds(0, 98, 149, 35);
		paneMenu.add(panelPerfil);
		panelPerfil.setLayout(null);

		JLabel lblIconPerfil = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_perfil.png"), 35, 35));
		lblIconPerfil.setBorder(null);
		lblIconPerfil.setBounds(10, 0, 46, 35);
		lblIconPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		panelPerfil.add(lblIconPerfil);

		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setBounds(66, 11, 46, 14);
		panelPerfil.add(lblPerfil);

		JPanel panelHome = new JPanel();
		panelHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelHome.setForeground(new Color(40, 66, 159));
		panelHome.addMouseListener(new PanelButtonMouseAdapter(panelHome) {
			@Override
			public void mouseClicked(MouseEvent e) { // Ação que, quando clica no JPanel, altera para a janela
														// correspondente
				menuClicked(telaHome);
			}
		});
		panelHome.setBackground(new Color(40, 66, 159));
		panelHome.setBounds(0, 52, 149, 35);
		paneMenu.add(panelHome);
		panelHome.setLayout(null);

		JLabel lblIconHome = new JLabel(GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_home.png"), 40, 35));
		lblIconHome.setBounds(10, 0, 46, 35);
		panelHome.add(lblIconHome);

		JLabel lblHome = new JLabel("Home");
		lblHome.setBounds(66, 11, 62, 14);
		lblHome.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblHome.setForeground(Color.WHITE);
		panelHome.add(lblHome);

		JPanel panelReceita = new JPanel();
		panelReceita.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelReceita.addMouseListener(new PanelButtonMouseAdapter(panelReceita) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaReceita);
			}
		});
		panelReceita.setBackground(new Color(40, 66, 159));
		panelReceita.setBounds(0, 144, 149, 35);
		paneMenu.add(panelReceita);
		panelReceita.setLayout(null);

		JLabel lblReceita = new JLabel("Receita");
		lblReceita.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblReceita.setForeground(Color.WHITE);
		lblReceita.setBounds(66, 11, 73, 14);
		panelReceita.add(lblReceita);

		JLabel lblIconReceita = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_receita.png"), 40, 35));
		lblIconReceita.setBounds(10, 0, 46, 35);
		panelReceita.add(lblIconReceita);

		JPanel panelDespesas = new JPanel();
		panelDespesas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelDespesas.addMouseListener(new PanelButtonMouseAdapter(panelDespesas) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaDespesa);
			}
		});
		panelDespesas.setBackground(new Color(40, 66, 159));
		panelDespesas.setBounds(0, 190, 149, 35);
		paneMenu.add(panelDespesas);
		panelDespesas.setLayout(null);

		JLabel lblDespesas = new JLabel("Despesas");
		lblDespesas.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblDespesas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDespesas.setForeground(Color.WHITE);
		lblDespesas.setBounds(66, 11, 65, 14);
		panelDespesas.add(lblDespesas);

		JLabel lblIconDespesas = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_despesas.png"), 42, 36));
		lblIconDespesas.setBounds(10, 0, 46, 35);
		panelDespesas.add(lblIconDespesas);

		JPanel panelCartoes = new JPanel();
		panelCartoes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCartoes.addMouseListener(new PanelButtonMouseAdapter(panelCartoes) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaCartoes);
			}
		});
		panelCartoes.setBackground(new Color(40, 66, 159));
		panelCartoes.setBounds(0, 236, 149, 35);
		paneMenu.add(panelCartoes);
		panelCartoes.setLayout(null);

		JLabel lblCartoes = new JLabel("Cartões");
		lblCartoes.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCartoes.setForeground(Color.WHITE);
		lblCartoes.setBounds(66, 11, 61, 14);
		panelCartoes.add(lblCartoes);

		JLabel lblIconCartoes = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_cartao.png"), 42, 35));
		lblIconCartoes.setBounds(10, 0, 46, 35);
		panelCartoes.add(lblIconCartoes);

		JPanel panelCategoria = new JPanel();
		panelCategoria.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCategoria.addMouseListener(new PanelButtonMouseAdapter(panelCategoria) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaCategoria);
			}
		});
		panelCategoria.setBackground(new Color(40, 66, 159));
		panelCategoria.setBounds(0, 281, 149, 35);
		paneMenu.add(panelCategoria);
		panelCategoria.setLayout(null);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCategoria.setForeground(Color.WHITE);
		lblCategoria.setBounds(66, 11, 80, 14);
		panelCategoria.add(lblCategoria);

		JLabel lblIconCategoria = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_categoria.png"), 45, 30));
		lblIconCategoria.setBounds(10, 0, 46, 35);
		panelCategoria.add(lblIconCategoria);

		JPanel panelFatura = new JPanel();
		panelFatura.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelFatura.addMouseListener(new PanelButtonMouseAdapter(panelFatura) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(telaFatura);
			}
		});
		panelFatura.setBackground(new Color(40, 66, 159));
		panelFatura.setBounds(0, 327, 149, 35);
		paneMenu.add(panelFatura);
		panelFatura.setLayout(null);

		JLabel lblFatura = new JLabel("Fatura");
		lblFatura.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblFatura.setForeground(Color.WHITE);
		lblFatura.setBounds(66, 11, 46, 14);
		panelFatura.add(lblFatura);

		JLabel lblIconFatura = new JLabel(
				GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_fatura.png"), 45, 30));
		lblIconFatura.setBounds(10, 0, 46, 35);
		panelFatura.add(lblIconFatura);

		JPanel panelSair = new JPanel();
		panelSair.addMouseListener(new PanelButtonMouseAdapter(panelSair) {
			@Override
			public void mouseClicked(MouseEvent e) {
				// exibe uma caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					TelaLogin chama = new TelaLogin();
					chama.setVisible(true);
					dispose();
				}
			}
		});

		panelSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelSair.setBackground(new Color(40, 66, 159));
		panelSair.setBounds(0, 375, 149, 35);
		paneMenu.add(panelSair);
		panelSair.setLayout(null);

		JLabel lblSair = new JLabel("Sair");
		lblSair.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblSair.setForeground(Color.WHITE);
		lblSair.setBounds(66, 11, 46, 14);
		panelSair.add(lblSair);

		JLabel lblIconSair = new JLabel(GerarImagem(TelaPrincipal.class.getResource("/Icons/icon_sair.png"), 40, 30));
		lblIconSair.setBounds(10, 0, 46, 35);
		panelSair.add(lblIconSair);

		JPanel panelMain = new JPanel();
		panelMain.setBounds(152, 0, 797, 497);
		contentPane.add(panelMain);
		panelMain.setLayout(null);

		// Adiciona todas as janelas no JPanel main
		panelMain.add(telaPerfil);
		panelMain.add(telaHome);
		panelMain.add(telaReceita);
		panelMain.add(telaDespesa);
		panelMain.add(telaCartoes);
		panelMain.add(telaCategoria);
		panelMain.add(telaFatura);

		menuClicked(telaHome);
	}

	public ImageIcon GerarImagem(URL url, int larg, int alt) {
		ImageIcon imageIcon = new ImageIcon(url);
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(larg, alt, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon imagem = new ImageIcon(imagemRedimensionada);
		return imagem;
	}

	// Função que permite que somente a janela que foi submetida como parametro
	// fique visivel
	public void menuClicked(JPanel panel) {
		telaPerfil.setVisible(false);
		telaHome.setVisible(false);
		telaReceita.setVisible(false);
		telaDespesa.setVisible(false);
		telaCartoes.setVisible(false);
		telaCategoria.setVisible(false);
		telaFatura.setVisible(false);

		panel.setVisible(true);
	}

	// Ações para a animação de cores dos JLabels dependendo das ações do mouse
	private class PanelButtonMouseAdapter extends MouseAdapter {
		JPanel panel;

		public PanelButtonMouseAdapter(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setBackground(new Color(192, 192, 192));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			panel.setBackground(new Color(40, 66, 159));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			panel.setBackground(new Color(40, 66, 159));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setBackground(new Color(40, 66, 159));
		}
	}
}
