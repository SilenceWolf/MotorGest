package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Theme {

    public static final Color FOND          = new Color(0xF4, 0xF6, 0xF9);
    public static final Color FOND_CARTE    = Color.WHITE;
    public static final Color BORDURE       = new Color(0xE2, 0xE6, 0xEC);

    public static final Color PRIMAIRE      = new Color(0x1F, 0x3A, 0x5F);
    public static final Color PRIMAIRE_HOV  = new Color(0x2A, 0x4D, 0x7A);
    public static final Color ACCENT        = new Color(0x10, 0xA3, 0x7C);
    public static final Color ACCENT_HOV    = new Color(0x14, 0xBC, 0x90);
    public static final Color DANGER        = new Color(0xD0, 0x42, 0x4F);
    public static final Color DANGER_HOV    = new Color(0xE0, 0x55, 0x62);
    public static final Color SECONDAIRE    = new Color(0x5B, 0x6B, 0x82);
    public static final Color SECONDAIRE_HOV= new Color(0x7A, 0x89, 0x9F);

    public static final Color TEXTE         = new Color(0x1A, 0x1F, 0x2B);
    public static final Color TEXTE_DOUX    = new Color(0x6B, 0x73, 0x82);
    public static final Color TEXTE_CLAIR   = Color.WHITE;

    public static final Color SUCCES_BG     = new Color(0xE6, 0xF7, 0xF0);
    public static final Color SUCCES_FG     = new Color(0x0F, 0x80, 0x60);
    public static final Color WARN_BG       = new Color(0xFD, 0xF3, 0xDC);
    public static final Color WARN_FG       = new Color(0xA8, 0x6B, 0x00);
    public static final Color INFO_BG       = new Color(0xE5, 0xEE, 0xF8);
    public static final Color INFO_FG       = new Color(0x1F, 0x3A, 0x5F);
    public static final Color DANGER_BG     = new Color(0xFC, 0xE6, 0xE8);
    public static final Color DANGER_FG     = new Color(0xB0, 0x35, 0x42);

    public static final Color TABLE_HEADER  = new Color(0xEC, 0xEF, 0xF4);
    public static final Color TABLE_SELECT  = new Color(0xDD, 0xE7, 0xF5);
    public static final Color TABLE_ALT     = new Color(0xFA, 0xFB, 0xFD);

    public static final Font POLICE         = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font POLICE_BOLD    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font POLICE_TITRE   = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font POLICE_SOUSTITRE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font POLICE_BOUTON  = new Font("Segoe UI", Font.BOLD, 12);

    public static JButton bouton(String texte, Color base, Color hover) {
        JButton b = new JButton(texte);
        b.setFont(POLICE_BOUTON);
        b.setForeground(TEXTE_CLAIR);
        b.setBackground(base);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(base); }
        });
        return b;
    }

    public static JButton boutonPrimaire(String texte) { return bouton(texte, PRIMAIRE, PRIMAIRE_HOV); }
    public static JButton boutonSucces(String texte)   { return bouton(texte, ACCENT, ACCENT_HOV); }
    public static JButton boutonDanger(String texte)   { return bouton(texte, DANGER, DANGER_HOV); }
    public static JButton boutonNeutre(String texte)   { return bouton(texte, SECONDAIRE, SECONDAIRE_HOV); }

    public static JTextField champTexte(int colonnes) {
        JTextField t = new JTextField(colonnes);
        t.setFont(POLICE);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDURE, 1),
                new EmptyBorder(6, 8, 6, 8)));
        return t;
    }

    public static <T> JComboBox<T> combo(T[] items) {
        JComboBox<T> c = new JComboBox<>(items);
        c.setFont(POLICE);
        c.setBackground(Color.WHITE);
        return c;
    }

    public static JLabel etiquette(String texte) {
        JLabel l = new JLabel(texte);
        l.setFont(POLICE_BOLD);
        l.setForeground(TEXTE);
        return l;
    }

    public static JLabel etiquetteDouce(String texte) {
        JLabel l = new JLabel(texte);
        l.setFont(POLICE);
        l.setForeground(TEXTE_DOUX);
        return l;
    }

    public static JPanel enteteSection(String titre, String sousTitre) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FOND);
        p.setBorder(new EmptyBorder(12, 16, 12, 16));

        JPanel gauche = new JPanel();
        gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS));
        gauche.setOpaque(false);

        JLabel t = new JLabel(titre);
        t.setFont(POLICE_TITRE);
        t.setForeground(PRIMAIRE);
        gauche.add(t);

        if (sousTitre != null && !sousTitre.isEmpty()) {
            JLabel s = new JLabel(sousTitre);
            s.setFont(POLICE_SOUSTITRE);
            s.setForeground(TEXTE_DOUX);
            s.setBorder(new EmptyBorder(2, 0, 0, 0));
            gauche.add(s);
        }

        p.add(gauche, BorderLayout.WEST);
        return p;
    }

    public static JPanel carte() {
        JPanel p = new JPanel();
        p.setBackground(FOND_CARTE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDURE, 1),
                new EmptyBorder(12, 12, 12, 12)));
        return p;
    }

    public static Border bordureCarte() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDURE, 1),
                new EmptyBorder(8, 8, 8, 8));
    }

    public static JPanel barreActions() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        p.setBackground(FOND_CARTE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDURE));
        return p;
    }

    public static void appliquerStyleTable(JTable table) {
        table.setFont(POLICE);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(TABLE_SELECT);
        table.setSelectionForeground(TEXTE);
        table.setGridColor(BORDURE);
        table.getTableHeader().setFont(POLICE_BOLD);
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(TEXTE);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDURE));
        table.getTableHeader().setReorderingAllowed(false);
        table.setBackground(FOND_CARTE);
    }
}
