package view;

import controller.GestionnaireFlotte;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanneauDisponibilites extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private DefaultTableModel modelVehic;
    private DefaultTableModel modelChauf;
    private JLabel kpiVehic;
    private JLabel kpiChauf;
    private JLabel kpiEntretien;

    public PanneauDisponibilites(GestionnaireFlotte gf) {
        this.gf = gf;
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
        rafraichir();
    }

    private void construire() {
        add(Theme.enteteSection("Disponibilites", "Vue temps reel des vehicules et chauffeurs disponibles"), BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 12));
        centre.setBackground(Theme.FOND);
        centre.setBorder(new EmptyBorder(0, 16, 16, 16));
        centre.add(creerKpis(), BorderLayout.NORTH);
        centre.add(creerTables(), BorderLayout.CENTER);
        add(centre, BorderLayout.CENTER);
    }

    private JPanel creerKpis() {
        JPanel p = new JPanel(new GridLayout(1, 3, 12, 0));
        p.setOpaque(false);
        kpiVehic = new JLabel("0 / 0");
        kpiChauf = new JLabel("0 / 0");
        kpiEntretien = new JLabel("0");
        styleKpi(kpiVehic, Theme.ACCENT);
        styleKpi(kpiChauf, Theme.PRIMAIRE);
        styleKpi(kpiEntretien, Theme.WARN_FG);
        p.add(wrapKpi(kpiVehic, "Vehicules disponibles", Theme.ACCENT));
        p.add(wrapKpi(kpiChauf, "Chauffeurs disponibles", Theme.PRIMAIRE));
        p.add(wrapKpi(kpiEntretien, "Entretien urgent", Theme.WARN_FG));
        return p;
    }

    private void styleKpi(JLabel l, Color c) {
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
        l.setForeground(c);
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

    private JPanel creerTables() {
        JPanel tables = new JPanel(new GridLayout(1, 2, 12, 0));
        tables.setOpaque(false);

        modelVehic = new DefaultTableModel(new String[]{"Immat", "Categorie", "Marque/Modele", "Etat", "Entretien"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        modelChauf = new DefaultTableModel(new String[]{"ID", "Nom", "Prenom", "Permis", "Missions"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tVehic = new JTable(modelVehic);
        Theme.appliquerStyleTable(tVehic);
        BadgeCellRenderer badge = new BadgeCellRenderer();
        tVehic.getColumnModel().getColumn(3).setCellRenderer(badge);
        tVehic.getColumnModel().getColumn(4).setCellRenderer(badge);

        JTable tChauf = new JTable(modelChauf);
        Theme.appliquerStyleTable(tChauf);

        tables.add(creerCarteTable("Vehicules disponibles", tVehic));
        tables.add(creerCarteTable("Chauffeurs disponibles", tChauf));
        return tables;
    }

    private JPanel creerCarteTable(String titre, JTable t) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.FOND_CARTE);
        p.setBorder(BorderFactory.createLineBorder(Theme.BORDURE, 1));

        JLabel l = new JLabel(titre);
        l.setFont(Theme.POLICE_BOLD);
        l.setForeground(Theme.PRIMAIRE);
        l.setBorder(new EmptyBorder(10, 14, 10, 14));
        p.add(l, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDURE));
        sp.getViewport().setBackground(Theme.FOND_CARTE);
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    @Override
    public void rafraichir() {
        modelVehic.setRowCount(0);
        for (Vehicule v : gf.vehiculesDisponibles()) {
            String urgent = (v instanceof Maintenable && ((Maintenable) v).entretienUrgent()) ? "Urgent" : "OK";
            modelVehic.addRow(new Object[]{v.getImmatriculation(), v.getCategorie(),
                    v.getMarque() + " " + v.getModele(), v.getEtat().toString(), urgent});
        }

        modelChauf.setRowCount(0);
        for (Chauffeur c : gf.chauffeursDisponibles()) {
            StringBuilder permis = new StringBuilder();
            for (TypePermis p : c.getPermis()) {
                if (permis.length() > 0) permis.append(", ");
                permis.append(p);
            }
            modelChauf.addRow(new Object[]{c.getId(), c.getNom(), c.getPrenom(),
                    permis.toString(), c.getNbMissionsEffectuees()});
        }

        kpiVehic.setText(gf.vehiculesDisponibles().size() + " / " + gf.getVehicules().size());
        kpiChauf.setText(gf.chauffeursDisponibles().size() + " / " + gf.getChauffeurs().size());
        kpiEntretien.setText(String.valueOf(gf.vehiculesAEntretenir().size()));
    }
}
