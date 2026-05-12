package model;

import java.time.LocalDateTime;

public class MissionLongue extends Mission implements Trackable {

    private double latitude;
    private double longitude;
    private int dureeEstimeeJours;

    public MissionLongue(String id, String depart, String arrivee, LocalDateTime dateDebut, int distanceKm, int dureeEstimeeJours) {
        super(id, depart, arrivee, dateDebut, distanceKm);
        this.dureeEstimeeJours = dureeEstimeeJours;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    @Override
    public String getType() { return "Longue"; }

    @Override
    public double getTarifBase() {
        return 150.0 + dureeEstimeeJours * 80.0;
    }

    @Override
    public double getLatitude() { return latitude; }

    @Override
    public double getLongitude() { return longitude; }

    @Override
    public void mettreAJourPosition(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public int getDureeEstimeeJours() { return dureeEstimeeJours; }
}
