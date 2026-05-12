package controller;

import model.*;

import java.io.*;
import java.util.*;

public class PersistanceCSV {

    public static void sauvegarderVehicules(List<Vehicule> vehicules, String fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            pw.println("categorie;immat;marque;modele;kilometrage;etat;extra");
            for (Vehicule v : vehicules) {
                String extra = "";
                if (v instanceof VehiculeLeger) extra = String.valueOf(((VehiculeLeger) v).getNbPlaces());
                else if (v instanceof VehiculeLourd) extra = String.valueOf(((VehiculeLourd) v).getChargeMaxTonnes());
                pw.println(v.getCategorie() + ";" + v.getImmatriculation() + ";" + v.getMarque() + ";"
                        + v.getModele() + ";" + v.getKilometrage() + ";" + v.getEtat() + ";" + extra);
            }
        }
    }

    public static List<Vehicule> chargerVehicules(String fichier) throws IOException {
        List<Vehicule> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne = br.readLine();
            while ((ligne = br.readLine()) != null) {
                if (ligne.isBlank()) continue;
                String[] p = ligne.split(";", -1);
                Vehicule v;
                switch (p[0]) {
                    case "Leger":
                        v = new VehiculeLeger(p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[6]));
                        break;
                    case "Lourd":
                        v = new VehiculeLourd(p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[6]));
                        break;
                    default:
                        continue;
                }
                v.setEtat(EtatVehicule.valueOf(p[5]));
                result.add(v);
            }
        }
        return result;
    }

    public static void sauvegarderChauffeurs(List<Chauffeur> chauffeurs, String fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            pw.println("id;nom;prenom;permis;dispo;nbMissions");
            for (Chauffeur c : chauffeurs) {
                StringBuilder permis = new StringBuilder();
                for (TypePermis tp : c.getPermis()) permis.append(tp).append(",");
                pw.println(c.getId() + ";" + c.getNom() + ";" + c.getPrenom() + ";" + permis + ";"
                        + c.isDisponible() + ";" + c.getNbMissionsEffectuees());
            }
        }
    }

    public static List<Chauffeur> chargerChauffeurs(String fichier) throws IOException {
        List<Chauffeur> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne = br.readLine();
            while ((ligne = br.readLine()) != null) {
                if (ligne.isBlank()) continue;
                String[] p = ligne.split(";", -1);
                Chauffeur c = new Chauffeur(p[0], p[1], p[2]);
                if (!p[3].isBlank()) {
                    for (String per : p[3].split(",")) {
                        if (!per.isBlank()) c.ajouterPermis(TypePermis.valueOf(per));
                    }
                }
                c.setDisponible(Boolean.parseBoolean(p[4]));
                result.add(c);
            }
        }
        return result;
    }

    public static void sauvegarderMissions(List<Mission> missions, String fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            pw.println("type;id;depart;arrivee;dateDebut;distance;statut;immatVehicule;idChauffeur");
            for (Mission m : missions) {
                String immat = m.getVehicule() != null ? m.getVehicule().getImmatriculation() : "";
                String idCh = m.getChauffeur() != null ? m.getChauffeur().getId() : "";
                pw.println(m.getType() + ";" + m.getId() + ";" + m.getDepart() + ";" + m.getArrivee() + ";"
                        + m.getDateDebut() + ";" + m.getDistanceKm() + ";" + m.getStatut() + ";" + immat + ";" + idCh);
            }
        }
    }
}
