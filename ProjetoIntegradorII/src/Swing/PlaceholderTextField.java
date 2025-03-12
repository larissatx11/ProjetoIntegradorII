package Swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {

    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().isEmpty()) {
                    repaint();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner()) {
            g.setColor(Color.GRAY);
            
            // Obtém as métricas da fonte atual
            FontMetrics metrics = g.getFontMetrics(getFont());
            
            // Calcula a posição x para centralizar o texto
            int x = getInsets().left;
            
            // Calcula a posição y para centralizar o texto verticalmente
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            
            // Desenha o placeholder centralizado
            g.drawString(placeholder, x, y);
        }
    }
}
