package model;

public abstract class Vehicule implements Identifiable {

    protected String immatriculation;
    protected String marque;
    protected String modele;
    protected int kilometrage;
    protected EtatVehicule etat;

    public Vehicule(String immatriculation, String marque, String modele, int kilometrage) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.kilometrage = kilometrage;
        this.etat = EtatVehicule.DISPONIBLE;
    }

    @Override
    public String getIdentifiant() { return immatriculation; }

    public abstract String getCategorie();

    public abstract double coutKilometrique();

    public String getImmatriculation() { return immatriculation; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public int getKilometrage() { return kilometrage; }
    public EtatVehicule getEtat() { return etat; }

    public void setKilometrage(int km) { this.kilometrage = km; }
    public void setEtat(EtatVehicule e) { this.etat = e; }

    @Override
    public String toString() {
        return immatriculation + " - " + marque + " " + modele;
    }
}
