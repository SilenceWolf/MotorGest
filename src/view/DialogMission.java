package view;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DialogMission extends JDialog {

    private Mission resultat;
    private JComboBox<String> typeCombo;
    private JTextField idField;
    private JTextField departField;
    private JTextField arriveeField;
    private JTextField dateField;
    private JTextField distanceField;
    private JTextField dureeField;
    private JLabel dureeLabel;

    public DialogMission(JFrame parent) {
        super(parent, "Nouvelle mission", true);
        setSize(460, 420);
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
        gbc.insets = new Insets(6, 0, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Type"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        typeCombo = Theme.combo(new String[]{"Courte", "Longue"});
        form.add(typeCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("ID"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        idField = Theme.champTexte(20);
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Depart"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        departField = Theme.champTexte(20);
        form.add(departField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Arrivee"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        arriveeField = Theme.champTexte(20);
        form.add(arriveeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Date debut"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        dateField = Theme.champTexte(20);
        dateField.setText(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        form.add(dateField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Distance (km)"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        distanceField = Theme.champTexte(20);
        form.add(distanceField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        dureeLabel = Theme.etiquette("Duree (jours)");
        form.add(dureeLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        dureeField = Theme.champTexte(20);
        form.add(dureeField, gbc);
        dureeLabel.setVisible(false);
        dureeField.setVisible(false);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1;
        JLabel info = Theme.etiquetteDouce("Format date : jj/MM/aaaa HH:mm");
        form.add(info, gbc);

        typeCombo.addActionListener(e -> {
            boolean longue = "Longue".equals(typeCombo.getSelectedItem());
            dureeLabel.setVisible(longue);
            dureeField.setVisible(longue);
            revalidate();
            repaint();
        });

        add(form, BorderLayout.CENTER);
        add(creerBoutons(), BorderLayout.SOUTH);
    }

    private JPanel creerEntete() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.PRIMAIRE);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel t = new JLabel("Nouvelle mission");
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(Theme.TEXTE_CLAIR);
        p.add(t, BorderLayout.WEST);
        return p;
    }

    private JPanel creerBoutons() {
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        bas.setBackground(Theme.FOND);
        bas.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDURE));
        JButton ok      = Theme.boutonSucces("Valider", "[v]");
        JButton annuler = Theme.boutonNeutre("Annuler", "[x]");
        bas.add(annuler);
        bas.add(ok);
        ok.addActionListener(e -> valider());
        annuler.addActionListener(e -> dispose());
        return bas;
    }

    private void valider() {
        try {
            String id = idField.getText().trim();
            String dep = departField.getText().trim();
            String arr = arriveeField.getText().trim();
            int dist = Integer.parseInt(distanceField.getText().trim());
            LocalDateTime date = LocalDateTime.parse(dateField.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            if (id.isEmpty() || dep.isEmpty() || arr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Champs obligatoires manquants");
                return;
            }
            if ("Courte".equals(typeCombo.getSelectedItem())) {
                resultat = new MissionCourte(id, dep, arr, date, dist);
            } else {
                int duree = Integer.parseInt(dureeField.getText().trim());
                resultat = new MissionLongue(id, dep, arr, date, dist, duree);
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valeur numerique invalide");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
        }
    }

    public Mission getResultat() { return resultat; }
}
