package view;

import controller.GestionnaireFlotte;
import controller.PersistanceCSV;
import model.Chauffeur;
import model.Vehicule;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class ActionsCSV {

    public static void sauvegarder(JFrame parent, GestionnaireFlotte gf) {
        JFileChooser ch = new JFileChooser(new File("resources"));
        ch.setDialogTitle("Sauvegarder dans le dossier...");
        ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (ch.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) return;
        File dir = ch.getSelectedFile();
        try {
            PersistanceCSV.sauvegarderVehicules(gf.getVehicules(), new File(dir, "vehicules.csv").getAbsolutePath());
            PersistanceCSV.sauvegarderChauffeurs(gf.getChauffeurs(), new File(dir, "chauffeurs.csv").getAbsolutePath());
            PersistanceCSV.sauvegarderMissions(gf.getMissions(), new File(dir, "missions.csv").getAbsolutePath());
            JOptionPane.showMessageDialog(parent, "Sauvegarde OK dans " + dir.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Erreur sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void charger(JFrame parent, GestionnaireFlotte gf) {
        JFileChooser ch = new JFileChooser(new File("resources"));
        ch.setDialogTitle("Selectionner le dossier de donnees...");
        ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (ch.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return;
        File dir = ch.getSelectedFile();
        try {
            File fv = new File(dir, "vehicules.csv");
            File fc = new File(dir, "chauffeurs.csv");
            int n = 0;
            if (fv.exists()) {
                List<Vehicule> vs = PersistanceCSV.chargerVehicules(fv.getAbsolutePath());
                for (Vehicule v : vs) {
                    if (gf.trouverVehicule(v.getImmatriculation()) == null) {
                        gf.ajouterVehicule(v);
                        n++;
                    }
                }
            }
            if (fc.exists()) {
                List<Chauffeur> cs = PersistanceCSV.chargerChauffeurs(fc.getAbsolutePath());
                for (Chauffeur c : cs) {
                    if (gf.trouverChauffeur(c.getId()) == null) {
                        gf.ajouterChauffeur(c);
                        n++;
                    }
                }
            }
            JOptionPane.showMessageDialog(parent, n + " elements charges");
            ((FenetrePrincipale) parent).rafraichirTout();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Erreur chargement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
