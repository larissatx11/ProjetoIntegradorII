package Swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class CentralizedTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Use o renderizador padrão para configurar as propriedades básicas
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Centralize o conteúdo horizontalmente
        setHorizontalAlignment(SwingConstants.CENTER);

        return this;
    }
}

