package model;

public interface Trackable {
    double getLatitude();
    double getLongitude();
    void mettreAJourPosition(double lat, double lon);
}
