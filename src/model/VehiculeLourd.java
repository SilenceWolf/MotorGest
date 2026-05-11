package model;

import java.time.LocalDate;

public class VehiculeLourd extends Vehicule implements Assignable, Maintenable {

    private double chargeMaxTonnes;
    private LocalDate prochainEntretien;

    public VehiculeLourd(String immatriculation, String marque, String modele, int kilometrage, double chargeMaxTonnes) {
        super(immatriculation, marque, modele, kilometrage);
        this.chargeMaxTonnes = chargeMaxTonnes;
        this.prochainEntretien = LocalDate.now().plusMonths(3);
    }

    @Override
    public String getCategorie() { return "Lourd"; }

    @Override
    public double coutKilometrique() {
        return 0.65 + (chargeMaxTonnes * 0.05);
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
        return 25000 - (kilometrage % 25000);
    }

    @Override
    public void planifierEntretien(LocalDate date) {
        this.prochainEntretien = date;
    }

    @Override
    public boolean entretienUrgent() {
        return getKmAvantEntretien() < 1000 || prochainEntretien.isBefore(LocalDate.now().plusDays(7));
    }

    public double getChargeMaxTonnes() { return chargeMaxTonnes; }
}
