package Swing;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class CustomHeaderRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Defina a cor de fundo e a cor da fonte desejadas para o cabeçalho da coluna
        setBackground(new Color(40, 66, 159)); // Cor de fundo
        setForeground(Color.WHITE); // Cor da fonte

        // Defina a fonte desejada para o cabeçalho da coluna
        Font headerFont = new Font("Arial", Font.BOLD, 12);
        setFont(headerFont);

        // Centralize o texto no cabeçalho da coluna
        setHorizontalAlignment(SwingConstants.CENTER);

        return this;
    }
}
