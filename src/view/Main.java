package view;

import controller.GestionnaireFlotte;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.background", Theme.FOND_CARTE);
            UIManager.put("Panel.background", Theme.FOND_CARTE);
            UIManager.put("OptionPane.messageFont", Theme.POLICE);
            UIManager.put("OptionPane.buttonFont", Theme.POLICE_BOUTON);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GestionnaireFlotte gf = new GestionnaireFlotte();
        chargerDonneesDemo(gf);

        SwingUtilities.invokeLater(() -> {
            FenetrePrincipale f = new FenetrePrincipale(gf);
            f.setVisible(true);
        });
    }

    private static void chargerDonneesDemo(GestionnaireFlotte gf) {
        gf.ajouterVehicule(new VehiculeLeger("AA-123-AA", "Renault", "Clio", 45000, 5));
        gf.ajouterVehicule(new VehiculeLeger("BB-456-BB", "Peugeot", "208", 32000, 5));
        gf.ajouterVehicule(new VehiculeLeger("GG-111-GG", "Citroen", "C3", 78000, 5));
        gf.ajouterVehicule(new VehiculeLourd("CC-789-CC", "Volvo", "FH16", 145000, 26.0));
        gf.ajouterVehicule(new VehiculeLourd("DD-321-DD", "Scania", "R450", 89000, 19.5));

        Chauffeur c1 = new Chauffeur("CH001", "Dupont", "Jean");
        c1.ajouterPermis(TypePermis.B);
        c1.ajouterPermis(TypePermis.C);
        Chauffeur c2 = new Chauffeur("CH002", "Martin", "Sophie");
        c2.ajouterPermis(TypePermis.B);
        Chauffeur c3 = new Chauffeur("CH003", "Bernard", "Luc");
        c3.ajouterPermis(TypePermis.B);
        c3.ajouterPermis(TypePermis.C);
        c3.ajouterPermis(TypePermis.CE);
        Chauffeur c4 = new Chauffeur("CH004", "Petit", "Marie");
        c4.ajouterPermis(TypePermis.B);

        gf.ajouterChauffeur(c1);
        gf.ajouterChauffeur(c2);
        gf.ajouterChauffeur(c3);
        gf.ajouterChauffeur(c4);

        gf.ajouterMission(new MissionCourte("M001", "Paris", "Lyon", LocalDateTime.now().plusDays(1), 465));
        gf.ajouterMission(new MissionCourte("M002", "Lyon", "Marseille", LocalDateTime.now().plusDays(2), 315));
        gf.ajouterMission(new MissionCourte("M003", "Lille", "Reims", LocalDateTime.now().plusDays(3), 210));
    }
}
