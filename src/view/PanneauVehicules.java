package view;

import controller.GestionnaireFlotte;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PanneauVehicules extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private final FenetrePrincipale parent;
    private VehiculeTableModel modele;
    private JTable table;
    private TableRowSorter<VehiculeTableModel> sorter;

    private JTextField champRecherche;
    private JComboBox<String> filtreCategorie;
    private JComboBox<String> filtreEtat;

    public PanneauVehicules(GestionnaireFlotte gf, FenetrePrincipale parent) {
        this.gf = gf;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
    }

    private void construire() {
        add(Theme.enteteSection("Vehicules", "Liste, recherche et gestion des vehicules de la flotte"), BorderLayout.NORTH);
        add(creerCentre(), BorderLayout.CENTER);
    }

    private JPanel creerCentre() {
        JPanel centre = new JPanel(new BorderLayout(0, 0));
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

        barre.add(Theme.etiquette("Recherche :"));
        champRecherche = Theme.champTexte(14);
        barre.add(champRecherche);

        barre.add(Box.createHorizontalStrut(12));
        barre.add(Theme.etiquette("Categorie :"));
        filtreCategorie = Theme.combo(new String[]{"Toutes", "Leger", "Lourd", "Special"});
        barre.add(filtreCategorie);

        barre.add(Box.createHorizontalStrut(12));
        barre.add(Theme.etiquette("Etat :"));
        String[] etats = new String[EtatVehicule.values().length + 1];
        etats[0] = "Tous";
        for (int i = 0; i < EtatVehicule.values().length; i++) etats[i + 1] = EtatVehicule.values()[i].name();
        filtreEtat = Theme.combo(etats);
        barre.add(filtreEtat);

        barre.add(Box.createHorizontalStrut(12));
        JButton btnReset = Theme.boutonNeutre("Reinitialiser");
        barre.add(btnReset);

        btnReset.addActionListener(e -> {
            champRecherche.setText("");
            filtreCategorie.setSelectedIndex(0);
            filtreEtat.setSelectedIndex(0);
            modele.setDonnees(gf.getVehicules());
        });

        filtreCategorie.addActionListener(e -> appliquerFiltres());
        filtreEtat.addActionListener(e -> appliquerFiltres());

        champRecherche.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { appliquerFiltres(); }
            @Override public void removeUpdate(DocumentEvent e) { appliquerFiltres(); }
            @Override public void changedUpdate(DocumentEvent e) { appliquerFiltres(); }
        });

        return barre;
    }

    private JScrollPane creerTable() {
        modele = new VehiculeTableModel(gf.getVehicules());
        table = new JTable(modele);
        sorter = new TableRowSorter<>(modele);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Theme.appliquerStyleTable(table);

        BadgeCellRenderer badge = new BadgeCellRenderer();
        table.getColumnModel().getColumn(5).setCellRenderer(badge);
        table.getColumnModel().getColumn(6).setCellRenderer(badge);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Theme.FOND_CARTE);
        return sp;
    }

    private JPanel creerBarreActions() {
        JPanel bas = Theme.barreActions();

        JButton btnAjouter   = Theme.boutonSucces("Ajouter");
        JButton btnModifier  = Theme.boutonPrimaire("Modifier");
        JButton btnDetails   = Theme.boutonNeutre("Details");
        JButton btnSupprimer = Theme.boutonDanger("Supprimer");

        bas.add(btnAjouter);
        bas.add(btnModifier);
        bas.add(btnDetails);
        bas.add(btnSupprimer);

        btnAjouter.addActionListener(e -> {
            DialogVehicule d = new DialogVehicule(parent, null);
            d.setVisible(true);
            if (d.getResultat() != null) {
                try {
                    gf.ajouterVehicule(d.getResultat());
                    parent.rafraichirTout();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSupprimer.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { info("Selectionnez un vehicule"); return; }
            int modelRow = table.convertRowIndexToModel(row);
            Vehicule v = modele.getVehiculeAt(modelRow);
            int conf = JOptionPane.showConfirmDialog(this, "Supprimer " + v.getImmatriculation() + " ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                gf.supprimerVehicule(v.getImmatriculation());
                parent.rafraichirTout();
            }
        });

        btnModifier.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { info("Selectionnez un vehicule"); return; }
            int modelRow = table.convertRowIndexToModel(row);
            Vehicule v = modele.getVehiculeAt(modelRow);
            DialogVehicule d = new DialogVehicule(parent, v);
            d.setVisible(true);
            if (d.getResultat() != null) {
                gf.supprimerVehicule(v.getImmatriculation());
                gf.ajouterVehicule(d.getResultat());
                parent.rafraichirTout();
            }
        });

        btnDetails.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { info("Selectionnez un vehicule"); return; }
            int modelRow = table.convertRowIndexToModel(row);
            afficherDetails(modele.getVehiculeAt(modelRow));
        });

        return bas;
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void appliquerFiltres() {
        String mc = champRecherche.getText().trim();
        String cat = filtreCategorie.getSelectedIndex() == 0 ? null : (String) filtreCategorie.getSelectedItem();
        EtatVehicule et = filtreEtat.getSelectedIndex() == 0 ? null : EtatVehicule.valueOf((String) filtreEtat.getSelectedItem());
        modele.setDonnees(gf.rechercherVehicules(mc, cat, et));
    }

    private void afficherDetails(Vehicule v) {
        StringBuilder sb = new StringBuilder();
        sb.append("Immatriculation : ").append(v.getImmatriculation()).append("\n");
        sb.append("Categorie : ").append(v.getCategorie()).append("\n");
        sb.append("Marque/Modele : ").append(v.getMarque()).append(" ").append(v.getModele()).append("\n");
        sb.append("Kilometrage : ").append(v.getKilometrage()).append(" km\n");
        sb.append("Etat : ").append(v.getEtat()).append("\n");
        sb.append("Cout au km : ").append(String.format("%.2f", v.coutKilometrique())).append(" EUR\n");
        if (v instanceof Maintenable) {
            Maintenable m = (Maintenable) v;
            sb.append("Prochain entretien : ").append(m.getProchainEntretien()).append("\n");
            sb.append("Km avant entretien : ").append(m.getKmAvantEntretien()).append("\n");
            sb.append("Urgent : ").append(m.entretienUrgent() ? "OUI" : "NON").append("\n");
        }
        if (v instanceof Urgence) {
            Urgence u = (Urgence) v;
            sb.append("Niveau urgence : ").append(u.getNiveauUrgence()).append("\n");
            sb.append("Motif : ").append(u.getMotifUrgence()).append("\n");
        }
        if (v instanceof VehiculeLeger) sb.append("Places : ").append(((VehiculeLeger) v).getNbPlaces()).append("\n");
        if (v instanceof VehiculeLourd) sb.append("Charge max : ").append(((VehiculeLourd) v).getChargeMaxTonnes()).append(" t\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Details vehicule", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void rafraichir() {
        modele.setDonnees(gf.getVehicules());
    }
}
