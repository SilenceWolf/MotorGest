package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BadgeCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String txt = value == null ? "" : value.toString();
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setFont(Theme.POLICE_BOLD);
        l.setBorder(new EmptyBorder(4, 8, 4, 8));

        Color bg = Theme.INFO_BG;
        Color fg = Theme.INFO_FG;

        switch (txt) {
            case "DISPONIBLE":
            case "OK":
            case "TERMINEE":
                bg = Theme.SUCCES_BG; fg = Theme.SUCCES_FG; break;
            case "EN_MISSION":
            case "EN_COURS":
                bg = Theme.INFO_BG; fg = Theme.INFO_FG; break;
            case "EN_MAINTENANCE":
            case "Urgent":
            case "URGENT":
            case "PLANIFIEE":
                bg = Theme.WARN_BG; fg = Theme.WARN_FG; break;
            case "HORS_SERVICE":
            case "ANNULEE":
                bg = Theme.DANGER_BG; fg = Theme.DANGER_FG; break;
            default:
                bg = Theme.INFO_BG; fg = Theme.INFO_FG;
        }

        if (isSelected) {
            l.setBackground(Theme.TABLE_SELECT);
            l.setForeground(fg);
            l.setOpaque(true);
        } else {
            l.setBackground(bg);
            l.setForeground(fg);
            l.setOpaque(true);
        }
        return l;
    }
}
