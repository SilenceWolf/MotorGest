package view;

import controller.GestionnaireFlotte;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PanneauMissions extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private final FenetrePrincipale parent;
    private MissionTableModel modele;
    private JTable table;
    private JComboBox<String> filtreStatut;
    private JComboBox<String> filtreType;
    private JTextField filtreDepart;

    public PanneauMissions(GestionnaireFlotte gf, FenetrePrincipale parent) {
        this.gf = gf;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
    }

    private void construire() {
        add(Theme.enteteSection("Missions", "Planification, affectation et suivi des missions", "[M]"), BorderLayout.NORTH);
        add(creerCentre(), BorderLayout.CENTER);
    }

    private JPanel creerCentre() {
        JPanel centre = new JPanel(new BorderLayout());
        centre.setBackground(Theme.FOND);
        centre.setBorder(new EmptyBorder(0, 16, 16, 16));

        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Theme.FOND_CARTE);
        carte.setBorder(BorderFactory.createLineBorder(Theme.BORDURE, 1));

        carte.add(creerBarreFiltres(), BorderLayout.NORTH);
        carte.add(creerTable(), BorderLayout.CENTER);
        carte.add(creerBarreActions(), BorderLayout.SOUTH);

        centre.add(carte, BorderLayout.CENTER);
        return centre;
    }

    private JPanel creerBarreFiltres() {
        JPanel barre = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        barre.setBackground(Theme.FOND_CARTE);
        barre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDURE),
                new EmptyBorder(4, 8, 4, 8)));

        barre.add(Theme.etiquette("Statut :"));
        String[] statuts = new String[StatutMission.values().length + 1];
        statuts[0] = "Tous";
        for (int i = 0; i < StatutMission.values().length; i++) statuts[i + 1] = StatutMission.values()[i].name();
        filtreStatut = Theme.combo(statuts);
        barre.add(filtreStatut);

        barre.add(Box.createHorizontalStrut(12));
        barre.add(Theme.etiquette("Type :"));
        filtreType = Theme.combo(new String[]{"Tous", "Courte"});
        barre.add(filtreType);

        barre.add(Box.createHorizontalStrut(12));
        barre.add(Theme.etiquette("Depart :"));
        filtreDepart = Theme.champTexte(10);
        barre.add(filtreDepart);

        barre.add(Box.createHorizontalStrut(12));
        JButton btnFiltrer = Theme.boutonPrimaire("Filtrer", "[>]");
        JButton btnReset   = Theme.boutonNeutre("Reinitialiser", "[~]");
        barre.add(btnFiltrer);
        barre.add(btnReset);

        btnFiltrer.addActionListener(e -> appliquerFiltres());
        btnReset.addActionListener(e -> {
            filtreStatut.setSelectedIndex(0);
            filtreType.setSelectedIndex(0);
            filtreDepart.setText("");
            modele.setDonnees(gf.getMissions());
        });

        return barre;
    }

    private JScrollPane creerTable() {
        modele = new MissionTableModel(gf.getMissions());
        table = new JTable(modele);
        table.setRowSorter(new TableRowSorter<>(modele));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Theme.appliquerStyleTable(table);

        BadgeCellRenderer badge = new BadgeCellRenderer();
        table.getColumnModel().getColumn(6).setCellRenderer(badge);

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Theme.FOND_CARTE);
        return sp;
    }

    private JPanel creerBarreActions() {
        JPanel bas = Theme.barreActions();

        JButton btnAjouter   = Theme.boutonSucces("Nouvelle mission", "[+]");
        JButton btnAffecter  = Theme.boutonPrimaire("Affecter", "[>]");
        JButton btnDemarrer  = Theme.boutonPrimaire("Demarrer", "[!]");
        JButton btnTerminer  = Theme.bouton("Terminer", "[v]", Theme.ACCENT, Theme.ACCENT_HOV);
        JButton btnSupprimer = Theme.boutonDanger("Supprimer", "[x]");

        bas.add(btnAjouter);
        bas.add(btnAffecter);
        bas.add(btnDemarrer);
        bas.add(btnTerminer);
        bas.add(btnSupprimer);

        btnAjouter.addActionListener(e -> {
            DialogMission d = new DialogMission(parent);
            d.setVisible(true);
            if (d.getResultat() != null) {
                try {
                    gf.ajouterMission(d.getResultat());
                    parent.rafraichirTout();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAffecter.addActionListener(e -> {
            Mission m = selection();
            if (m == null) return;
            if (m.getStatut() != StatutMission.PLANIFIEE) {
                info("Mission deja affectee ou terminee");
                return;
            }
            DialogAffectation d = new DialogAffectation(parent, gf, m);
            d.setVisible(true);
            parent.rafraichirTout();
        });

        btnDemarrer.addActionListener(e -> {
            Mission m = selection();
            if (m == null) return;
            if (m.getVehicule() == null || m.getChauffeur() == null) {
                info("Affectez d'abord vehicule et chauffeur");
                return;
            }
            m.demarrer();
            parent.rafraichirTout();
        });

        btnTerminer.addActionListener(e -> {
            Mission m = selection();
            if (m == null) return;
            gf.terminerMission(m.getId());
            parent.rafraichirTout();
        });

        btnSupprimer.addActionListener(e -> {
            Mission m = selection();
            if (m == null) return;
            int conf = JOptionPane.showConfirmDialog(this, "Supprimer mission " + m.getId() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                gf.supprimerMission(m.getId());
                parent.rafraichirTout();
            }
        });

        return bas;
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private Mission selection() {
        int row = table.getSelectedRow();
        if (row < 0) { info("Selectionnez une mission"); return null; }
        return modele.getMissionAt(table.convertRowIndexToModel(row));
    }

    private void appliquerFiltres() {
        StatutMission st = filtreStatut.getSelectedIndex() == 0 ? null : StatutMission.valueOf((String) filtreStatut.getSelectedItem());
        String tp = filtreType.getSelectedIndex() == 0 ? null : (String) filtreType.getSelectedItem();
        String dep = filtreDepart.getText().trim();
        modele.setDonnees(gf.rechercherMissions(st, tp, dep));
    }

    @Override
    public void rafraichir() {
        modele.setDonnees(gf.getMissions());
    }
}
