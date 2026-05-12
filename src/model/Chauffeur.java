package model;

import java.util.HashSet;
import java.util.Set;

public class Chauffeur implements Identifiable {

    private String id;
    private String nom;
    private String prenom;
    private Set<TypePermis> permis;
    private boolean disponible;
    private int nbMissionsEffectuees;

    public Chauffeur(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.permis = new HashSet<>();
        this.disponible = true;
        this.nbMissionsEffectuees = 0;
    }

    @Override
    public String getIdentifiant() { return id; }

    public void ajouterPermis(TypePermis p) {
        permis.add(p);
    }

    public boolean possedePermis(TypePermis p) {
        return permis.contains(p);
    }

    public void incrementerMissions() {
        nbMissionsEffectuees++;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public Set<TypePermis> getPermis() { return permis; }
    public boolean isDisponible() { return disponible; }
    public int getNbMissionsEffectuees() { return nbMissionsEffectuees; }

    public void setDisponible(boolean d) { this.disponible = d; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + id + ")";
    }
}
