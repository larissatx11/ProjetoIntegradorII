package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int cornerRadius;

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint(); // Redesenha o botão com o novo raio dos cantos
    }

    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinte o botão com a cor de fundo padrão
        if (getModel().isArmed()) {
            g2d.setColor(getBackground().darker());
        } else {
            g2d.setColor(getBackground());
        }

        // Desenhe o botão com cantos arredondados
        int width = getWidth();
        int height = getHeight();
        int arc = cornerRadius; // Raio dos cantos arredondados (ajuste conforme necessário)
        g2d.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, arc, arc));

        // Pinte o texto do botão
        g2d.setColor(getForeground());
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (width - metrics.stringWidth(getText())) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Botão Arredondado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RoundedButton roundedButton = new RoundedButton("Clique Aqui", 20); // Raio dos cantos: 20
            frame.add(roundedButton);

            JButton changeRadiusButton = new JButton("Alterar Raio");
            changeRadiusButton.addActionListener(e -> {
                int newRadius = Integer.parseInt(JOptionPane.showInputDialog("Digite um novo raio:"));
                roundedButton.setCornerRadius(newRadius);
            });
            frame.add(changeRadiusButton, BorderLayout.SOUTH);

            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
