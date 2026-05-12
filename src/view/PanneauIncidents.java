package view;

import controller.GestionnaireFlotte;
import model.Incident;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class PanneauIncidents extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private DefaultTableModel modele;
    private JTable table;
    private JLabel kpiNombre;
    private JLabel kpiCout;

    public PanneauIncidents(GestionnaireFlotte gf) {
        this.gf = gf;
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
        rafraichir();
    }

    private void construire() {
        add(Theme.enteteSection("Incidents", "Suivi et declaration des incidents sur la flotte"), BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 12));
        centre.setBackground(Theme.FOND);
        centre.setBorder(new EmptyBorder(0, 16, 16, 16));

        centre.add(creerKpis(), BorderLayout.NORTH);
        centre.add(creerCarteTable(), BorderLayout.CENTER);
        add(centre, BorderLayout.CENTER);
    }

    private JPanel creerKpis() {
        JPanel p = new JPanel(new GridLayout(1, 2, 12, 0));
        p.setOpaque(false);
        kpiNombre = new JLabel("0");
        kpiCout = new JLabel("0 EUR");
        kpiNombre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        kpiCout.setFont(new Font("Segoe UI", Font.BOLD, 24));
        kpiNombre.setForeground(Theme.PRIMAIRE);
        kpiCout.setForeground(Theme.DANGER);
        p.add(wrapKpi(kpiNombre, "Nombre d'incidents", Theme.PRIMAIRE));
        p.add(wrapKpi(kpiCout, "Cout total", Theme.DANGER));
        return p;
    }

    private JPanel wrapKpi(JLabel valeur, String titre, Color barColor) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Theme.FOND_CARTE);
        carte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDURE, 1),
                new EmptyBorder(12, 16, 12, 16)));

        JPanel bar = new JPanel();
        bar.setBackground(barColor);
        bar.setPreferredSize(new Dimension(4, 0));
        carte.add(bar, BorderLayout.WEST);

        JPanel contenu = new JPanel(new BorderLayout(0, 4));
        contenu.setOpaque(false);
        contenu.setBorder(new EmptyBorder(0, 12, 0, 0));
        JLabel t = new JLabel(titre);
        t.setFont(Theme.POLICE_SOUSTITRE);
        t.setForeground(Theme.TEXTE_DOUX);
        contenu.add(t, BorderLayout.NORTH);
        contenu.add(valeur, BorderLayout.CENTER);

        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    private JPanel creerCarteTable() {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Theme.FOND_CARTE);
        carte.setBorder(BorderFactory.createLineBorder(Theme.BORDURE, 1));

        modele = new DefaultTableModel(new String[]{"ID", "Date", "Vehicule", "Gravite", "Description", "Cout (EUR)"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(modele);
        Theme.appliquerStyleTable(table);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Theme.FOND_CARTE);
        carte.add(sp, BorderLayout.CENTER);

        JPanel bas = Theme.barreActions();
        JButton btnAjouter = Theme.boutonSucces("Declarer incident");
        bas.add(btnAjouter);
        carte.add(bas, BorderLayout.SOUTH);

        btnAjouter.addActionListener(e -> declarer());

        return carte;
    }

    private void declarer() {
        JTextField id = Theme.champTexte(12);
        JTextField immat = Theme.champTexte(12);
        JTextField gravite = Theme.champTexte(12);
        gravite.setText("1");
        JTextField cout = Theme.champTexte(12);
        cout.setText("0");
        JTextField desc = Theme.champTexte(20);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Theme.FOND_CARTE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"ID", "Immat vehicule", "Gravite (1-5)", "Cout (EUR)", "Description"};
        JTextField[] fields = {id, immat, gravite, cout, desc};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            p.add(Theme.etiquette(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            p.add(fields[i], gbc);
        }

        int res = JOptionPane.showConfirmDialog(this, p, "Nouvel incident", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Incident i = new Incident(id.getText().trim(), LocalDate.now(), desc.getText().trim(),
                        Integer.parseInt(gravite.getText().trim()),
                        Double.parseDouble(cout.getText().trim()),
                        immat.getText().trim());
                gf.ajouterIncident(i);
                rafraichir();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void rafraichir() {
        modele.setRowCount(0);
        double total = 0;
        for (Incident i : gf.getIncidents()) {
            modele.addRow(new Object[]{i.getId(), i.getDate(), i.getImmatVehicule(),
                    i.getGravite(), i.getDescription(), String.format("%.2f", i.getCout())});
            total += i.getCout();
        }
        kpiNombre.setText(String.valueOf(gf.getIncidents().size()));
        kpiCout.setText(String.format("%.2f EUR", total));
    }
}
