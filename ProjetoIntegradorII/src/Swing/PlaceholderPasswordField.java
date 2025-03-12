package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JPasswordField {

    private String placeholder;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;

        // Adiciona um ouvinte de foco para controlar quando o campo está vazio
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
            
            // Calcula a posição x para centralizar à esquerda
            int x = getInsets().left;
            
            // Calcula a posição y para centralizar verticalmente
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            
            // Desenha o placeholder
            g.drawString(placeholder, x, y);
        }
    }
}
