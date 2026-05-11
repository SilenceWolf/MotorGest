package model;

import java.time.LocalDateTime;

public abstract class Mission implements Facturable, Identifiable {

    protected String id;
    protected String depart;
    protected String arrivee;
    protected LocalDateTime dateDebut;
    protected LocalDateTime dateFin;
    protected int distanceKm;
    protected StatutMission statut;
    protected Vehicule vehicule;
    protected Chauffeur chauffeur;

    public Mission(String id, String depart, String arrivee, LocalDateTime dateDebut, int distanceKm) {
        this.id = id;
        this.depart = depart;
        this.arrivee = arrivee;
        this.dateDebut = dateDebut;
        this.distanceKm = distanceKm;
        this.statut = StatutMission.PLANIFIEE;
    }

    @Override
    public String getIdentifiant() { return id; }

    public abstract String getType();

    public abstract double getTarifBase();

    @Override
    public double calculerCout() {
        double base = getTarifBase();
        if (vehicule != null) {
            base += vehicule.coutKilometrique() * distanceKm;
        }
        return base;
    }

    public void affecter(Vehicule v, Chauffeur c) {
        this.vehicule = v;
        this.chauffeur = c;
    }

    public void demarrer() {
        this.statut = StatutMission.EN_COURS;
    }

    public void terminer() {
        this.statut = StatutMission.TERMINEE;
        this.dateFin = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getDepart() { return depart; }
    public String getArrivee() { return arrivee; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public int getDistanceKm() { return distanceKm; }
    public StatutMission getStatut() { return statut; }
    public Vehicule getVehicule() { return vehicule; }
    public Chauffeur getChauffeur() { return chauffeur; }

    public void setStatut(StatutMission s) { this.statut = s; }

    @Override
    public String toString() {
        return id + " : " + depart + " -> " + arrivee;
    }
}
