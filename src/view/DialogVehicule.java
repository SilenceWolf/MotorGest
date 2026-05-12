package view;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogVehicule extends JDialog {

    private Vehicule resultat;

    private JComboBox<String> typeCombo;
    private JTextField immatField;
    private JTextField marqueField;
    private JTextField modeleField;
    private JTextField kmField;
    private JTextField extra1Field;
    private JTextField extra2Field;
    private JLabel extra1Label;
    private JLabel extra2Label;

    public DialogVehicule(JFrame parent, Vehicule existant) {
        super(parent, existant == null ? "Nouveau vehicule" : "Modifier vehicule", true);
        setSize(440, 420);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Theme.FOND_CARTE);
        construire(existant);
    }

    private void construire(Vehicule existant) {
        add(creerEntete(existant == null), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Theme.FOND_CARTE);
        form.setBorder(new EmptyBorder(16, 24, 8, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Type"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        typeCombo = Theme.combo(new String[]{"Leger", "Lourd", "Special"});
        form.add(typeCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Immatriculation"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        immatField = Theme.champTexte(18);
        form.add(immatField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Marque"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        marqueField = Theme.champTexte(18);
        form.add(marqueField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Modele"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        modeleField = Theme.champTexte(18);
        form.add(modeleField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Kilometrage"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        kmField = Theme.champTexte(18);
        form.add(kmField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        extra1Label = Theme.etiquette("Nb places");
        form.add(extra1Label, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        extra1Field = Theme.champTexte(18);
        form.add(extra1Field, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        extra2Label = Theme.etiquette("Niveau urgence");
        form.add(extra2Label, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        extra2Field = Theme.champTexte(18);
        form.add(extra2Field, gbc);
        extra2Label.setVisible(false);
        extra2Field.setVisible(false);

        typeCombo.addActionListener(e -> majChampsExtras());

        if (existant != null) {
            immatField.setText(existant.getImmatriculation());
            immatField.setEditable(false);
            marqueField.setText(existant.getMarque());
            modeleField.setText(existant.getModele());
            kmField.setText(String.valueOf(existant.getKilometrage()));
            if (existant instanceof VehiculeLeger) {
                typeCombo.setSelectedItem("Leger");
                extra1Field.setText(String.valueOf(((VehiculeLeger) existant).getNbPlaces()));
            } else if (existant instanceof VehiculeLourd) {
                typeCombo.setSelectedItem("Lourd");
                extra1Field.setText(String.valueOf(((VehiculeLourd) existant).getChargeMaxTonnes()));
            } else if (existant instanceof VehiculeSpecial) {
                typeCombo.setSelectedItem("Special");
                extra1Field.setText(((VehiculeSpecial) existant).getTypeSpecial());
                extra2Field.setText(String.valueOf(((VehiculeSpecial) existant).getNiveauUrgence()));
            }
            typeCombo.setEnabled(false);
            majChampsExtras();
        }

        add(form, BorderLayout.CENTER);
        add(creerBoutons(), BorderLayout.SOUTH);
    }

    private JPanel creerEntete(boolean creation) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.PRIMAIRE);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel t = new JLabel(creation ? "Nouveau vehicule" : "Modifier vehicule");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(Theme.TEXTE_CLAIR);
        p.add(t, BorderLayout.WEST);
        return p;
    }

    private JPanel creerBoutons() {
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        bas.setBackground(Theme.FOND);
        bas.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDURE));
        JButton ok      = Theme.boutonSucces("Valider");
        JButton annuler = Theme.boutonNeutre("Annuler");
        bas.add(annuler);
        bas.add(ok);
        ok.addActionListener(e -> valider());
        annuler.addActionListener(e -> dispose());
        return bas;
    }

    private void majChampsExtras() {
        String type = (String) typeCombo.getSelectedItem();
        if ("Leger".equals(type)) {
            extra1Label.setText("Nb places");
            extra2Label.setVisible(false);
            extra2Field.setVisible(false);
        } else if ("Lourd".equals(type)) {
            extra1Label.setText("Charge (t)");
            extra2Label.setVisible(false);
            extra2Field.setVisible(false);
        } else {
            extra1Label.setText("Type special");
            extra2Label.setText("Niveau urgence");
            extra2Label.setVisible(true);
            extra2Field.setVisible(true);
        }
        revalidate();
        repaint();
    }

    private void valider() {
        try {
            String immat = immatField.getText().trim();
            String marque = marqueField.getText().trim();
            String modele = modeleField.getText().trim();
            int km = Integer.parseInt(kmField.getText().trim());

            if (immat.isEmpty() || marque.isEmpty() || modele.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
                return;
            }
            if (km < 0) {
                JOptionPane.showMessageDialog(this, "Kilometrage invalide");
                return;
            }

            String type = (String) typeCombo.getSelectedItem();
            if ("Leger".equals(type)) {
                int places = Integer.parseInt(extra1Field.getText().trim());
                resultat = new VehiculeLeger(immat, marque, modele, km, places);
            } else if ("Lourd".equals(type)) {
                double charge = Double.parseDouble(extra1Field.getText().trim());
                resultat = new VehiculeLourd(immat, marque, modele, km, charge);
            } else {
                String typeSpe = extra1Field.getText().trim();
                int niv = Integer.parseInt(extra2Field.getText().trim());
                resultat = new VehiculeSpecial(immat, marque, modele, km, typeSpe, niv);
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valeur numerique invalide");
        }
    }

    public Vehicule getResultat() { return resultat; }
}
