package view;

import model.Chauffeur;
import model.TypePermis;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogChauffeur extends JDialog {

    private Chauffeur resultat;
    private JTextField idField;
    private JTextField nomField;
    private JTextField prenomField;
    private JCheckBox[] permisChecks;

    public DialogChauffeur(JFrame parent, Chauffeur existant) {
        super(parent, existant == null ? "Nouveau chauffeur" : "Modifier chauffeur", true);
        setSize(440, 360);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Theme.FOND_CARTE);
        construire(existant);
    }

    private void construire(Chauffeur existant) {
        add(creerEntete(existant == null), BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 8));
        centre.setBackground(Theme.FOND_CARTE);
        centre.setBorder(new EmptyBorder(16, 24, 8, 24));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Theme.FOND_CARTE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("ID"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        idField = Theme.champTexte(18);
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Nom"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        nomField = Theme.champTexte(18);
        form.add(nomField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(Theme.etiquette("Prenom"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        prenomField = Theme.champTexte(18);
        form.add(prenomField, gbc);

        centre.add(form, BorderLayout.NORTH);

        JPanel pPermis = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        pPermis.setBackground(Theme.FOND_CARTE);
        pPermis.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.BORDURE),
                "Permis", 0, 0, Theme.POLICE_BOLD, Theme.TEXTE));
        TypePermis[] types = TypePermis.values();
        permisChecks = new JCheckBox[types.length];
        for (int i = 0; i < types.length; i++) {
            permisChecks[i] = new JCheckBox(types[i].name());
            permisChecks[i].setBackground(Theme.FOND_CARTE);
            permisChecks[i].setFont(Theme.POLICE);
            pPermis.add(permisChecks[i]);
        }
        centre.add(pPermis, BorderLayout.CENTER);

        add(centre, BorderLayout.CENTER);
        add(creerBoutons(), BorderLayout.SOUTH);

        if (existant != null) {
            idField.setText(existant.getId());
            idField.setEditable(false);
            nomField.setText(existant.getNom());
            prenomField.setText(existant.getPrenom());
            for (int i = 0; i < types.length; i++) {
                if (existant.possedePermis(types[i])) permisChecks[i].setSelected(true);
            }
        }
    }

    private JPanel creerEntete(boolean creation) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.PRIMAIRE);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel t = new JLabel(creation ? "Nouveau chauffeur" : "Modifier chauffeur");
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

    private void valider() {
        String id = idField.getText().trim();
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        if (id.isEmpty() || nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }
        Chauffeur c = new Chauffeur(id, nom, prenom);
        TypePermis[] types = TypePermis.values();
        for (int i = 0; i < types.length; i++) {
            if (permisChecks[i].isSelected()) c.ajouterPermis(types[i]);
        }
        resultat = c;
        dispose();
    }

    public Chauffeur getResultat() { return resultat; }
}
