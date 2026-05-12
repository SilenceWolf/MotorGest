package view;
 
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class Theme {
 
    // === FONDS ===
    public static final Color FOND          = new Color(0x0F, 0x10, 0x14);
    public static final Color FOND_CARTE    = new Color(0x1A, 0x1B, 0x22);
    public static final Color FOND_HAUT     = new Color(0x07, 0x07, 0x0B);
    public static final Color FOND_CHAMP    = new Color(0x24, 0x25, 0x2E);
    public static final Color FOND_BARRE    = new Color(0x16, 0x17, 0x1E);
    public static final Color FOND_HOVER    = new Color(0x2B, 0x2D, 0x37);
 
    public static final Color BORDURE       = new Color(0x4A, 0x4D, 0x58);
    public static final Color BORDURE_FORTE = new Color(0x60, 0x63, 0x70);
 
    // === COULEURS VOITURE DE SPORT ===
    public static final Color PRIMAIRE      = new Color(0xC0, 0x10, 0x20);
    public static final Color PRIMAIRE_HOV  = new Color(0xE0, 0x15, 0x28);
    public static final Color ACCENT        = new Color(0xA8, 0x1F, 0x5F);
    public static final Color ACCENT_HOV    = new Color(0xC8, 0x28, 0x75);
    public static final Color VIOLET        = new Color(0x6A, 0x3F, 0xB8);
    public static final Color VIOLET_HOV    = new Color(0x82, 0x52, 0xD4);
    public static final Color DANGER        = new Color(0xE0, 0x35, 0x3E);
    public static final Color DANGER_HOV    = new Color(0xF0, 0x48, 0x52);
    // Gris-bouton plus fonce qu'avant pour eviter l'effet "pale"
    public static final Color SECONDAIRE    = new Color(0x2E, 0x30, 0x3A);
    public static final Color SECONDAIRE_HOV= new Color(0x40, 0x42, 0x4F);
 
    // === TEXTES ===
    public static final Color TEXTE         = new Color(0xEC, 0xEC, 0xF0);
    public static final Color TEXTE_DOUX    = new Color(0xC2, 0xC5, 0xCE);
    public static final Color TEXTE_CLAIR   = Color.WHITE;
    public static final Color TEXTE_ACCENT  = new Color(0xFF, 0x4F, 0x6E);
 
    // === BADGES ===
    public static final Color SUCCES_BG     = new Color(0x14, 0x3C, 0x2E);
    public static final Color SUCCES_FG     = new Color(0x3F, 0xE0, 0x9A);
    public static final Color WARN_BG       = new Color(0x4A, 0x32, 0x10);
    public static final Color WARN_FG       = new Color(0xFF, 0xC0, 0x42);
    public static final Color INFO_BG       = new Color(0x27, 0x1A, 0x42);
    public static final Color INFO_FG       = new Color(0xB8, 0x95, 0xFF);
    public static final Color DANGER_BG     = new Color(0x4A, 0x14, 0x1F);
    public static final Color DANGER_FG     = new Color(0xFF, 0x6B, 0x7A);
 
    // === TABLES ===
    public static final Color TABLE_HEADER  = new Color(0x22, 0x23, 0x2C);
    public static final Color TABLE_SELECT  = new Color(0x4A, 0x18, 0x24);
    public static final Color TABLE_ALT     = new Color(0x20, 0x21, 0x2A);
 
    // === POLICES ===
    public static final Font POLICE         = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font POLICE_BOLD    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font POLICE_TITRE   = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font POLICE_SOUSTITRE = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font POLICE_BOUTON  = new Font("Segoe UI", Font.BOLD, 12);
 
    /**
     * A appeler une seule fois au demarrage de l'application,
     * AVANT toute creation de fenetre/composant Swing.
     * Pose un Look & Feel Nimbus configure pour le theme sombre.
     */
    public static void installer() {
        try {
            // Nimbus est le seul L&F Swing qui se laisse vraiment reskinner
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        ColorUIResource cFond       = new ColorUIResource(FOND);
        ColorUIResource cFondCarte  = new ColorUIResource(FOND_CARTE);
        ColorUIResource cFondChamp  = new ColorUIResource(FOND_CHAMP);
        ColorUIResource cBordure    = new ColorUIResource(BORDURE);
        ColorUIResource cTexte      = new ColorUIResource(TEXTE);
        ColorUIResource cTexteDoux  = new ColorUIResource(TEXTE_DOUX);
        ColorUIResource cTexteClair = new ColorUIResource(TEXTE_CLAIR);
        ColorUIResource cPrimaire   = new ColorUIResource(PRIMAIRE);
        ColorUIResource cAccent     = new ColorUIResource(TEXTE_ACCENT);
        FontUIResource  fPolice     = new FontUIResource(POLICE);
        FontUIResource  fBold       = new FontUIResource(POLICE_BOLD);
 
        // === Nimbus defaults (couleurs primaires du theme) ===
        UIManager.put("control",            cFondCarte);          // fond panneaux par defaut
        UIManager.put("info",               cFondCarte);
        UIManager.put("nimbusBase",         new ColorUIResource(0x16, 0x17, 0x1E));
        UIManager.put("nimbusBlueGrey",     new ColorUIResource(0x2E, 0x30, 0x3A));
        UIManager.put("nimbusLightBackground", cFondCarte);       // fond text/list/table
        UIManager.put("nimbusSelectionBackground", cPrimaire);
        UIManager.put("nimbusSelection",    cPrimaire);
        UIManager.put("text",               cTexte);
        UIManager.put("menuText",           cTexte);
        UIManager.put("textForeground",     cTexte);
        UIManager.put("controlText",        cTexte);
        UIManager.put("nimbusDisabledText", cTexteDoux);
        UIManager.put("nimbusFocus",        new ColorUIResource(PRIMAIRE));
        UIManager.put("nimbusBorder",       cBordure);
        UIManager.put("background",         cFondCarte);
        UIManager.put("nimbusInfoBlue",     cPrimaire);
 
        // === Specifiques par composant ===
        UIManager.put("Panel.background",       cFondCarte);
        UIManager.put("RootPane.background",    cFond);
        UIManager.put("Label.foreground",       cTexte);
        UIManager.put("Label.background",       cFondCarte);
 
        UIManager.put("MenuBar.background",     new ColorUIResource(FOND_BARRE));
        UIManager.put("MenuBar.foreground",     cTexte);
        UIManager.put("MenuBar:Menu[Selected].textForeground", cTexteClair);
        UIManager.put("Menu.background",        new ColorUIResource(FOND_BARRE));
        UIManager.put("Menu.foreground",        cTexte);
        UIManager.put("MenuItem.background",    cFondCarte);
        UIManager.put("MenuItem.foreground",    cTexte);
        UIManager.put("MenuItem[MouseOver].background", cPrimaire);
        UIManager.put("PopupMenu.background",   cFondCarte);
 
        UIManager.put("TabbedPane.background",        cFond);
        UIManager.put("TabbedPane.foreground",        cTexte);
        UIManager.put("TabbedPane.contentAreaColor",  cFondCarte);
        UIManager.put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(6, 14, 6, 14));
        UIManager.put("TabbedPane:TabbedPaneTab[Enabled].textForeground",  cTexte);
        UIManager.put("TabbedPane:TabbedPaneTab[Selected].textForeground", cAccent);
        UIManager.put("TabbedPane:TabbedPaneTab[MouseOver].textForeground", cTexteClair);
 
        UIManager.put("ComboBox.background",      cFondChamp);
        UIManager.put("ComboBox.foreground",      cTexte);
        UIManager.put("ComboBox:\"ComboBox.listRenderer\".background",   cFondChamp);
        UIManager.put("ComboBox:\"ComboBox.listRenderer\".foreground",   cTexte);
        UIManager.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background", cPrimaire);
        UIManager.put("ComboBox:\"ComboBox.listRenderer\"[Selected].textForeground", cTexteClair);
        UIManager.put("ComboBox.buttonBackground", cFondChamp);
 
        UIManager.put("List.background",          cFondCarte);
        UIManager.put("List.foreground",          cTexte);
        UIManager.put("List[Selected].textForeground", cTexteClair);
 
        UIManager.put("TextField.background",     cFondChamp);
        UIManager.put("TextField.foreground",     cTexte);
        UIManager.put("TextField.caretForeground",cTexte);
        UIManager.put("FormattedTextField.background", cFondChamp);
        UIManager.put("FormattedTextField.foreground", cTexte);
        UIManager.put("PasswordField.background", cFondChamp);
        UIManager.put("PasswordField.foreground", cTexte);
        UIManager.put("TextArea.background",      cFondChamp);
        UIManager.put("TextArea.foreground",      cTexte);
 
        UIManager.put("CheckBox.background",      cFondCarte);
        UIManager.put("CheckBox.foreground",      cTexte);
        UIManager.put("RadioButton.background",   cFondCarte);
        UIManager.put("RadioButton.foreground",   cTexte);
 
        UIManager.put("ScrollPane.background",    cFondCarte);
        UIManager.put("Viewport.background",      cFondCarte);
        UIManager.put("ScrollBar.background",     cFondCarte);
        UIManager.put("ScrollBar.thumb",          new ColorUIResource(0x3A, 0x3C, 0x46));
        UIManager.put("ScrollBar.track",          cFondCarte);
 
        UIManager.put("OptionPane.background",    cFondCarte);
        UIManager.put("OptionPane.foreground",    cTexte);
        UIManager.put("OptionPane.messageForeground", cTexte);
 
        UIManager.put("Table.background",         cFondCarte);
        UIManager.put("Table.foreground",         cTexte);
        UIManager.put("Table.gridColor",          cBordure);
        UIManager.put("Table.selectionBackground", new ColorUIResource(TABLE_SELECT));
        UIManager.put("Table.selectionForeground", cTexteClair);
        UIManager.put("TableHeader.background",   new ColorUIResource(TABLE_HEADER));
        UIManager.put("TableHeader.foreground",   cAccent);
        UIManager.put("TableHeader.font",         fBold);
 
        // Police globale
        UIManager.put("defaultFont", fPolice);
    }
 
    public static JButton bouton(String texte, Color base, Color hover) {
        JButton b = new JButton(texte);
        b.setFont(POLICE_BOUTON);
        b.setForeground(TEXTE_CLAIR);
        b.setBackground(base);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        // Force le rendu meme sous Nimbus (qui aime ignorer setBackground)
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(base); }
        });
        return b;
    }
 
    public static JButton boutonPrimaire(String texte) { return bouton(texte, PRIMAIRE, PRIMAIRE_HOV); }
    public static JButton boutonSucces(String texte)   { return bouton(texte, ACCENT, ACCENT_HOV); }
    public static JButton boutonViolet(String texte)   { return bouton(texte, VIOLET, VIOLET_HOV); }
    public static JButton boutonDanger(String texte)   { return bouton(texte, DANGER, DANGER_HOV); }
    public static JButton boutonNeutre(String texte)   { return bouton(texte, SECONDAIRE, SECONDAIRE_HOV); }
 
    // Surcharges de compatibilite : icone ignoree
    public static JButton bouton(String texte, String icone, Color base, Color hover) { return bouton(texte, base, hover); }
    public static JButton boutonPrimaire(String texte, String icone) { return boutonPrimaire(texte); }
    public static JButton boutonSucces(String texte, String icone)   { return boutonSucces(texte); }
    public static JButton boutonViolet(String texte, String icone)   { return boutonViolet(texte); }
    public static JButton boutonDanger(String texte, String icone)   { return boutonDanger(texte); }
    public static JButton boutonNeutre(String texte, String icone)   { return boutonNeutre(texte); }
 
    public static JTextField champTexte(int colonnes) {
        JTextField t = new JTextField(colonnes);
        t.setFont(POLICE);
        t.setBackground(FOND_CHAMP);
        t.setForeground(TEXTE);
        t.setCaretColor(TEXTE);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDURE, 1),
                new EmptyBorder(6, 8, 6, 8)));
        return t;
    }
 
    public static <T> JComboBox<T> combo(T[] items) {
        JComboBox<T> c = new JComboBox<>(items);
        c.setFont(POLICE);
        c.setBackground(FOND_CHAMP);
        c.setForeground(TEXTE);
        // Force le UI Metal : Nimbus ignore setBackground sur les combos.
        // MetalComboBoxUI laisse setBackground prendre l'effet attendu.
        c.setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
            @Override
            protected javax.swing.JButton createArrowButton() {
                javax.swing.JButton b = new javax.swing.plaf.metal.MetalComboBoxButton(
                        comboBox, new javax.swing.plaf.metal.MetalComboBoxIcon(),
                        false, currentValuePane, listBox) {
                    @Override
                    public void paintComponent(Graphics g) {
                        g.setColor(FOND_CHAMP);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        // dessin de la fleche
                        int w = getWidth(), h = getHeight();
                        int cx = w / 2, cy = h / 2;
                        g.setColor(TEXTE);
                        int[] xs = {cx - 4, cx + 4, cx};
                        int[] ys = {cy - 2, cy - 2, cy + 3};
                        g.fillPolygon(xs, ys, 3);
                    }
                };
                b.setBackground(FOND_CHAMP);
                b.setBorder(BorderFactory.createEmptyBorder());
                return b;
            }
        });
        c.setBorder(BorderFactory.createLineBorder(BORDURE, 1));
        c.setOpaque(true);
        c.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                l.setBackground(isSelected ? PRIMAIRE : FOND_CHAMP);
                l.setForeground(isSelected ? TEXTE_CLAIR : TEXTE);
                l.setBorder(new EmptyBorder(4, 8, 4, 8));
                l.setOpaque(true);
                return l;
            }
        });
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
        t.setForeground(TEXTE_ACCENT);
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
 
    public static JPanel enteteSection(String titre, String sousTitre, String icone) {
        return enteteSection(titre, sousTitre);
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
 
    /**
     * A appeler sur la barre d'etat (en bas de la fenetre) ou
     * tout autre panel qui devrait avoir un fond sombre uniforme.
     */
    public static JPanel barreEtat() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        p.setBackground(FOND_BARRE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDURE));
        return p;
    }
 
    public static void appliquerStyleTable(JTable table) {
        table.setFont(POLICE);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setForeground(TEXTE);
        table.setBackground(FOND_CARTE);
        table.setSelectionBackground(TABLE_SELECT);
        table.setSelectionForeground(TEXTE_CLAIR);
        table.setGridColor(BORDURE);
        table.setFillsViewportHeight(true);
 
        JTableHeader h = table.getTableHeader();
        h.setFont(POLICE_BOLD);
        h.setBackground(TABLE_HEADER);
        h.setForeground(TEXTE_ACCENT);
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDURE_FORTE));
        h.setReorderingAllowed(false);
        h.setOpaque(true);
        h.setPreferredSize(new Dimension(h.getPreferredSize().width, 36));
        // Renderer custom pour forcer le fond sombre (Nimbus ignore setBackground sinon)
        h.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(LEFT);
                setOpaque(true);
            }
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                l.setBackground(TABLE_HEADER);
                l.setForeground(TEXTE_ACCENT);
                l.setFont(POLICE_BOLD);
                l.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, BORDURE),
                        new EmptyBorder(6, 10, 6, 10)));
                return l;
            }
        });
    }
 
    /**
     * MenuBar avec fond sombre uni - subclasse pour contourner le peintre Nimbus.
     */
    public static JMenuBar barreMenu() {
        JMenuBar mb = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(FOND_BARRE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(BORDURE);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        mb.setOpaque(true);
        mb.setBackground(FOND_BARRE);
        mb.setBorder(BorderFactory.createEmptyBorder());
        return mb;
    }
}
