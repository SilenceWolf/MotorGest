package model;

import java.time.LocalDate;

public class VehiculeLeger extends Vehicule implements Assignable, Maintenable {

    private int nbPlaces;
    private LocalDate prochainEntretien;

    public VehiculeLeger(String immatriculation, String marque, String modele, int kilometrage, int nbPlaces) {
        super(immatriculation, marque, modele, kilometrage);
        this.nbPlaces = nbPlaces;
        this.prochainEntretien = LocalDate.now().plusMonths(6);
    }

    @Override
    public String getCategorie() { return "Leger"; }

    @Override
    public double coutKilometrique() {
        return 0.25;
    }

    @Override
    public boolean estDisponible() {
        return etat == EtatVehicule.DISPONIBLE;
    }

    @Override
    public void assigner() {
        etat = EtatVehicule.EN_MISSION;
    }

    @Override
    public void liberer() {
        etat = EtatVehicule.DISPONIBLE;
    }

    @Override
    public LocalDate getProchainEntretien() { return prochainEntretien; }

    @Override
    public int getKmAvantEntretien() {
        return 15000 - (kilometrage % 15000);
    }

    @Override
    public void planifierEntretien(LocalDate date) {
        this.prochainEntretien = date;
    }

    @Override
    public boolean entretienUrgent() {
        return getKmAvantEntretien() < 500 || prochainEntretien.isBefore(LocalDate.now().plusDays(7));
    }

    public int getNbPlaces() { return nbPlaces; }
}
