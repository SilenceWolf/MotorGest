package model;

import java.time.LocalDate;

public class VehiculeSpecial extends Vehicule implements Urgence, Maintenable, Assignable {

    private String typeSpecial;
    private int niveauUrgence;
    private LocalDate prochainEntretien;

    public VehiculeSpecial(String immatriculation, String marque, String modele, int kilometrage, String typeSpecial, int niveauUrgence) {
        super(immatriculation, marque, modele, kilometrage);
        this.typeSpecial = typeSpecial;
        this.niveauUrgence = niveauUrgence;
        this.prochainEntretien = LocalDate.now().plusMonths(2);
    }

    @Override
    public String getCategorie() { return "Special"; }

    @Override
    public double coutKilometrique() {
        return 0.90;
    }

    @Override
    public int getNiveauUrgence() { return niveauUrgence; }

    @Override
    public String getMotifUrgence() { return "Vehicule special : " + typeSpecial; }

    @Override
    public boolean estDisponible() {
        return etat == EtatVehicule.DISPONIBLE;
    }

    @Override
    public void assigner() { etat = EtatVehicule.EN_MISSION; }

    @Override
    public void liberer() { etat = EtatVehicule.DISPONIBLE; }

    @Override
    public LocalDate getProchainEntretien() { return prochainEntretien; }

    @Override
    public int getKmAvantEntretien() {
        return 10000 - (kilometrage % 10000);
    }

    @Override
    public void planifierEntretien(LocalDate date) {
        this.prochainEntretien = date;
    }

    @Override
    public boolean entretienUrgent() {
        return getKmAvantEntretien() < 300 || prochainEntretien.isBefore(LocalDate.now().plusDays(3));
    }

    public String getTypeSpecial() { return typeSpecial; }
}
