package model;

import java.time.LocalDate;

public interface Maintenable {
    LocalDate getProchainEntretien();
    int getKmAvantEntretien();
    void planifierEntretien(LocalDate date);
    boolean entretienUrgent();
}
