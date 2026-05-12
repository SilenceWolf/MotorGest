package view;

import controller.GestionnaireFlotte;
import model.Chauffeur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.stream.Collectors;

public class PanneauChauffeurs extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private final FenetrePrincipale parent;
    private ChauffeurTableModel modele;
    private JTable table;
    private JTextField champRecherche;
    private JCheckBox chkDispo;

    public PanneauChauffeurs(GestionnaireFlotte gf, FenetrePrincipale parent) {
        this.gf = gf;
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
    }

    private void construire() {
        add(Theme.enteteSection("Chauffeurs", "Liste, recherche et gestion des chauffeurs", "[C]"), BorderLayout.NORTH);
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

        barre.add(Theme.etiquette("Recherche :"));
        champRecherche = Theme.champTexte(16);
        barre.add(champRecherche);

        barre.add(Box.createHorizontalStrut(12));
        chkDispo = new JCheckBox("Disponibles uniquement");
        chkDispo.setFont(Theme.POLICE);
        chkDispo.setBackground(Theme.FOND_CARTE);
        chkDispo.setForeground(Theme.TEXTE);
        barre.add(chkDispo);

        barre.add(Box.createHorizontalStrut(12));
        JButton btnFiltrer = Theme.boutonPrimaire("Filtrer", "[>]");
        JButton btnReset   = Theme.boutonNeutre("Reinitialiser", "[~]");
        barre.add(btnFiltrer);
        barre.add(btnReset);

        btnFiltrer.addActionListener(e -> appliquerFiltres());
        btnReset.addActionListener(e -> {
            champRecherche.setText("");
            chkDispo.setSelected(false);
            modele.setDonnees(gf.getChauffeurs());
        });

        return barre;
    }

    private JScrollPane creerTable() {
        modele = new ChauffeurTableModel(gf.getChauffeurs());
        table = new JTable(modele);
        table.setRowSorter(new TableRowSorter<>(modele));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Theme.appliquerStyleTable(table);

        BadgeCellRenderer badge = new BadgeCellRenderer();
        table.getColumnModel().getColumn(4).setCellRenderer(badge);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Theme.FOND_CARTE);
        return sp;
    }

    private JPanel creerBarreActions() {
        JPanel bas = Theme.barreActions();

        JButton btnAjouter   = Theme.boutonSucces("Ajouter", "[+]");
        JButton btnModifier  = Theme.boutonPrimaire("Modifier", "[*]");
        JButton btnSupprimer = Theme.boutonDanger("Supprimer", "[x]");

        bas.add(btnAjouter);
        bas.add(btnModifier);
        bas.add(btnSupprimer);

        btnAjouter.addActionListener(e -> {
            DialogChauffeur d = new DialogChauffeur(parent, null);
            d.setVisible(true);
            if (d.getResultat() != null) {
                try {
                    gf.ajouterChauffeur(d.getResultat());
                    parent.rafraichirTout();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnModifier.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { info("Selectionnez un chauffeur"); return; }
            int modelRow = table.convertRowIndexToModel(row);
            Chauffeur c = modele.getChauffeurAt(modelRow);
            DialogChauffeur d = new DialogChauffeur(parent, c);
            d.setVisible(true);
            if (d.getResultat() != null) {
                gf.supprimerChauffeur(c.getId());
                gf.ajouterChauffeur(d.getResultat());
                parent.rafraichirTout();
            }
        });

        btnSupprimer.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { info("Selectionnez un chauffeur"); return; }
            int modelRow = table.convertRowIndexToModel(row);
            Chauffeur c = modele.getChauffeurAt(modelRow);
            int conf = JOptionPane.showConfirmDialog(this, "Supprimer " + c + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                gf.supprimerChauffeur(c.getId());
                parent.rafraichirTout();
            }
        });

        return bas;
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void appliquerFiltres() {
        String mc = champRecherche.getText().trim().toLowerCase();
        boolean dispoOnly = chkDispo.isSelected();
        modele.setDonnees(gf.getChauffeurs().stream()
                .filter(c -> mc.isEmpty()
                        || c.getNom().toLowerCase().contains(mc)
                        || c.getPrenom().toLowerCase().contains(mc)
                        || c.getId().toLowerCase().contains(mc))
                .filter(c -> !dispoOnly || c.isDisponible())
                .collect(Collectors.toList()));
    }

    @Override
    public void rafraichir() {
        modele.setDonnees(gf.getChauffeurs());
    }
}
