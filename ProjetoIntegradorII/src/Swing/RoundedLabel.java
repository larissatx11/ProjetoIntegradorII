package Swing;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class RoundedLabel extends JLabel {
    private int arcWidth = 10; // Largura do arco para cantos arredondados
    private int arcHeight = 10; // Altura do arco para cantos arredondados

    public RoundedLabel(int width, int height) {
        arcWidth = width;
        arcHeight = height;
        setOpaque(false); // Torna o fundo do JLabel transparente
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Ative o antialiasing para obter cantos arredondados suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Desenhe um retângulo arredondado com cantos arredondados
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, arcWidth, arcHeight);
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        g2d.setColor(getForeground());
        g2d.drawString(getText(), 5, 15); // Ajuste a posição do texto conforme necessário

        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("JLabel com Cantos Arredondados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        RoundedLabel label = new RoundedLabel(10, 10);
        label.setBackground(Color.BLUE); // Cor de fundo do JLabel
        label.setForeground(Color.WHITE); // Cor do texto do JLabel

        panel.add(label);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
