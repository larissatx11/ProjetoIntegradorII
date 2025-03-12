package Telas;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

public class TelaHome extends JPanel {

	/**
	 * Create the panel.
	 */
	public TelaHome() {
		setBounds(0, 0, 691, 498);
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);

		JLabel lblHome = new JLabel("Home");
		lblHome.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblHome.setForeground(new Color(40, 66, 159));
		lblHome.setBounds(28, 0, 142, 85);
		add(lblHome);

		ImageIcon imageIcon = new ImageIcon(TelaHome.class.getResource("/Icons/calculadora.png"));
		Image imagemOriginal = imageIcon.getImage();
		// Redimensiona a imagem para as dimensões desejadas
		Image imagemRedimensionada = imagemOriginal.getScaledInstance(600, 450, Image.SCALE_SMOOTH);
		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon imagem = new ImageIcon(imagemRedimensionada);

		JLabel lblFoto = new JLabel(imagem);
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBounds(72, 52, 588, 347);
		add(lblFoto);

		JLabel lblFrase = new JLabel("Domine suas finanças, liberte seu futuro.");
		lblFrase.setForeground(new Color(40, 66, 159));
		lblFrase.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblFrase.setHorizontalAlignment(SwingConstants.CENTER);
		lblFrase.setBounds(133, 410, 412, 22);
		add(lblFrase);
	}
}
