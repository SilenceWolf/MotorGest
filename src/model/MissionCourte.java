package model;

import java.time.LocalDateTime;

public class MissionCourte extends Mission {

    public MissionCourte(String id, String depart, String arrivee, LocalDateTime dateDebut, int distanceKm) {
        super(id, depart, arrivee, dateDebut, distanceKm);
    }

    @Override
    public String getType() { return "Courte"; }

    @Override
    public double getTarifBase() {
        return 50.0;
    }
}
