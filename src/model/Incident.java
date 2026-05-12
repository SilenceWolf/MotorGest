package model;

import java.time.LocalDate;

public class Incident {

    private String id;
    private LocalDate date;
    private String description;
    private int gravite;
    private double cout;
    private String immatVehicule;

    public Incident(String id, LocalDate date, String description, int gravite, double cout, String immatVehicule) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.gravite = gravite;
        this.cout = cout;
        this.immatVehicule = immatVehicule;
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public int getGravite() { return gravite; }
    public double getCout() { return cout; }
    public String getImmatVehicule() { return immatVehicule; }
}
