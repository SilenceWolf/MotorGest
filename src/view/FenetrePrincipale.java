package view;

import controller.GestionnaireFlotte;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FenetrePrincipale extends JFrame {

    private final GestionnaireFlotte gf;
    private JTabbedPane onglets;

    public FenetrePrincipale(GestionnaireFlotte gf) {
        this.gf = gf;
        setTitle("MotorGest - Gestion de flotte");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.FOND);
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        add(creerBandeauHaut(), BorderLayout.NORTH);

        onglets = new JTabbedPane();
        onglets.setFont(Theme.POLICE_BOLD);
        onglets.setBackground(Theme.FOND);
        onglets.setBorder(new EmptyBorder(8, 12, 12, 12));

        onglets.addTab("  Vehicules  ", new PanneauVehicules(gf, this));
        onglets.addTab("  Chauffeurs  ", new PanneauChauffeurs(gf, this));
        onglets.addTab("  Missions  ", new PanneauMissions(gf, this));
        onglets.addTab("  Disponibilites  ", new PanneauDisponibilites(gf));
        onglets.addTab("  Incidents  ", new PanneauIncidents(gf));
        onglets.addTab("  Statistiques  ", new PanneauStatistiques(gf));

        add(onglets, BorderLayout.CENTER);
        add(creerBarreEtat(), BorderLayout.SOUTH);

        setJMenuBar(creerMenu());
    }

    private JPanel creerBandeauHaut() {
        JPanel bandeau = new JPanel(new BorderLayout());
        bandeau.setBackground(Theme.PRIMAIRE);
        bandeau.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel logo = new JLabel("MotorGest");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Theme.TEXTE_CLAIR);
        logo.setIcon(creerIconeLogo());
        logo.setIconTextGap(12);
        bandeau.add(logo, BorderLayout.WEST);

        JLabel sousTitre = new JLabel("Gestion de flotte de vehicules");
        sousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sousTitre.setForeground(new Color(255, 255, 255, 180));
        bandeau.add(sousTitre, BorderLayout.EAST);

        return bandeau;
    }

    private Icon creerIconeLogo() {
        int taille = 32;
        BufferedImage img = new BufferedImage(taille, taille, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Theme.ACCENT);
        g.fillRoundRect(0, 0, taille, taille, 8, 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.BOLD, 18));
        FontMetrics fm = g.getFontMetrics();
        String txt = "M";
        int x = (taille - fm.stringWidth(txt)) / 2;
        int y = (taille - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(txt, x, y);
        g.dispose();
        return new ImageIcon(img);
    }

    private JPanel creerBarreEtat() {
        JPanel barre = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
        barre.setBackground(new Color(0xE9, 0xEC, 0xF2));
        barre.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDURE));

        JLabel info = new JLabel("Pret. Astuce : Ctrl+S pour sauvegarder, Ctrl+O pour charger.");
        info.setFont(Theme.POLICE_SOUSTITRE);
        info.setForeground(Theme.TEXTE_DOUX);
        barre.add(info);

        return barre;
    }

    private JMenuBar creerMenu() {
        JMenuBar mb = new JMenuBar();
        mb.setBackground(Theme.FOND_CARTE);
        mb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDURE));

        JMenu fichier = new JMenu("Fichier");
        fichier.setFont(Theme.POLICE);

        JMenuItem charger = new JMenuItem("Charger CSV...");
        JMenuItem sauver  = new JMenuItem("Sauvegarder CSV...");
        JMenuItem quitter = new JMenuItem("Quitter");
        charger.setFont(Theme.POLICE);
        sauver.setFont(Theme.POLICE);
        quitter.setFont(Theme.POLICE);

        charger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        sauver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        charger.addActionListener(e -> ActionsCSV.charger(this, gf));
        sauver.addActionListener(e -> ActionsCSV.sauvegarder(this, gf));
        quitter.addActionListener(e -> System.exit(0));

        fichier.add(charger);
        fichier.add(sauver);
        fichier.addSeparator();
        fichier.add(quitter);

        JMenu aide = new JMenu("Aide");
        aide.setFont(Theme.POLICE);
        JMenuItem apropos = new JMenuItem("A propos");
        apropos.setFont(Theme.POLICE);
        apropos.addActionListener(e -> afficherAPropos());
        aide.add(apropos);

        mb.add(fichier);
        mb.add(aide);
        return mb;
    }

    private void afficherAPropos() {
        String msg = "MotorGest v1.0\nGestion de flotte de vehicules\n\nKalotta - Matheo - Yohan - Louis\nBachelor 3 Informatique - ESIEE-IT";
        JOptionPane.showMessageDialog(this, msg, "A propos", JOptionPane.INFORMATION_MESSAGE);
    }

    public void rafraichirTout() {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < onglets.getTabCount(); i++) {
                Component c = onglets.getComponentAt(i);
                if (c instanceof Rafraichissable) {
                    ((Rafraichissable) c).rafraichir();
                }
            }
        });
    }
}
