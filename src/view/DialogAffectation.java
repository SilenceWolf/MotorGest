package view;

import controller.GestionnaireFlotte;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogAffectation extends JDialog {

    private final GestionnaireFlotte gf;
    private final Mission mission;
    private JComboBox<Vehicule> comboVehicules;
    private JComboBox<Chauffeur> comboChauffeurs;

    public DialogAffectation(JFrame parent, GestionnaireFlotte gf, Mission mission) {
        super(parent, "Affecter mission " + mission.getId(), true);
        this.gf = gf;
        this.mission = mission;
        setSize(460, 280);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Theme.FOND_CARTE);
        construire();
    }

    private void construire() {
        add(creerEntete(), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Theme.FOND_CARTE);
        form.setBorder(new EmptyBorder(16, 24, 8, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Trajet"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        JLabel trajet = new JLabel(mission.getDepart() + "  vers  " + mission.getArrivee());
        trajet.setFont(Theme.POLICE);
        trajet.setForeground(Theme.TEXTE);
        form.add(trajet, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Vehicule"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        comboVehicules = new JComboBox<>();
        comboVehicules.setFont(Theme.POLICE);
        comboVehicules.setBackground(Color.WHITE);
        for (Vehicule v : gf.vehiculesDisponibles()) comboVehicules.addItem(v);
        form.add(comboVehicules, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Chauffeur"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        comboChauffeurs = new JComboBox<>();
        comboChauffeurs.setFont(Theme.POLICE);
        comboChauffeurs.setBackground(Color.WHITE);
        for (Chauffeur c : gf.chauffeursDisponibles()) comboChauffeurs.addItem(c);
        form.add(comboChauffeurs, gbc);

        add(form, BorderLayout.CENTER);
        add(creerBoutons(), BorderLayout.SOUTH);
    }

    private JPanel creerEntete() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.PRIMAIRE);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel t = new JLabel("Affectation de mission " + mission.getId());
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(Theme.TEXTE_CLAIR);
        p.add(t, BorderLayout.WEST);
        return p;
    }

    private JPanel creerBoutons() {
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        bas.setBackground(Theme.FOND);
        bas.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDURE));
        JButton ok      = Theme.boutonSucces("Affecter", "[v]");
        JButton annuler = Theme.boutonNeutre("Annuler", "[x]");
        bas.add(annuler);
        bas.add(ok);
        ok.addActionListener(e -> valider());
        annuler.addActionListener(e -> dispose());
        return bas;
    }

    private void valider() {
        Vehicule v = (Vehicule) comboVehicules.getSelectedItem();
        Chauffeur c = (Chauffeur) comboChauffeurs.getSelectedItem();
        if (v == null || c == null) {
            JOptionPane.showMessageDialog(this, "Aucun vehicule ou chauffeur disponible");
            return;
        }
        try {
            gf.affecterMission(mission.getId(), v.getImmatriculation(), c.getId());
            JOptionPane.showMessageDialog(this, "Mission affectee");
            dispose();
        } catch (VehiculeIndisponibleException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Indisponible", JOptionPane.WARNING_MESSAGE);
        } catch (PermisInsuffisantException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Permis", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
