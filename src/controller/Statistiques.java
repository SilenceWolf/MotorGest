package controller;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class Statistiques {

    private final GestionnaireFlotte gf;

    public Statistiques(GestionnaireFlotte gf) {
        this.gf = gf;
    }

    public long nombreVehiculesParCategorie(String categorie) {
        return gf.getVehicules().stream()
                .filter(v -> v.getCategorie().equalsIgnoreCase(categorie))
                .count();
    }

    public Map<String, Long> repartitionParCategorie() {
        return gf.getVehicules().stream()
                .collect(Collectors.groupingBy(Vehicule::getCategorie, Collectors.counting()));
    }

    public Map<EtatVehicule, Long> repartitionParEtat() {
        return gf.getVehicules().stream()
                .collect(Collectors.groupingBy(Vehicule::getEtat, Collectors.counting()));
    }

    public Map<StatutMission, Long> repartitionMissionsParStatut() {
        return gf.getMissions().stream()
                .collect(Collectors.groupingBy(Mission::getStatut, Collectors.counting()));
    }

    public double kilometrageMoyen() {
        return gf.getVehicules().stream()
                .mapToInt(Vehicule::getKilometrage)
                .average()
                .orElse(0.0);
    }

    public double coutTotalMissions() {
        return gf.getMissions().stream()
                .mapToDouble(Mission::calculerCout)
                .sum();
    }

    public double coutTotalIncidents() {
        return gf.getIncidents().stream()
                .mapToDouble(Incident::getCout)
                .sum();
    }

    public int distanceTotaleParcourue() {
        return gf.getMissions().stream()
                .filter(m -> m.getStatut() == StatutMission.TERMINEE)
                .mapToInt(Mission::getDistanceKm)
                .sum();
    }

    public Optional<Chauffeur> chauffeurLePlusActif() {
        return gf.getChauffeurs().stream()
                .max(Comparator.comparingInt(Chauffeur::getNbMissionsEffectuees));
    }

    public List<Vehicule> topVehiculesKilometrage(int n) {
        return gf.getVehicules().stream()
                .sorted(Comparator.comparingInt(Vehicule::getKilometrage).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public long nbMissionsTerminees() {
        return gf.getMissions().stream()
                .filter(m -> m.getStatut() == StatutMission.TERMINEE)
                .count();
    }
}
