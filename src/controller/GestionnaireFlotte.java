package controller;

import model.*;
import util.Registre;

import java.util.*;
import java.util.stream.Collectors;

public class GestionnaireFlotte {

    private final Registre<Vehicule> vehicules;
    private final Registre<Chauffeur> chauffeurs;
    private final Registre<Mission> missions;
    private final List<Incident> incidents;
    private final PriorityQueue<Mission> filePriorite;

    public GestionnaireFlotte() {
        this.vehicules = new Registre<>();
        this.chauffeurs = new Registre<>();
        this.missions = new Registre<>();
        this.incidents = new ArrayList<>();
        this.filePriorite = new PriorityQueue<>(Comparator.comparing(Mission::getDateDebut));
    }

    public void ajouterVehicule(Vehicule v) { vehicules.ajouter(v); }
    public void ajouterChauffeur(Chauffeur c) { chauffeurs.ajouter(c); }

    public void ajouterMission(Mission m) {
        missions.ajouter(m);
        filePriorite.add(m);
    }

    public boolean supprimerVehicule(String immat) { return vehicules.supprimer(immat); }
    public boolean supprimerChauffeur(String id) { return chauffeurs.supprimer(id); }
    public boolean supprimerMission(String id) {
        Mission m = missions.trouver(id);
        if (m != null) filePriorite.remove(m);
        return missions.supprimer(id);
    }

    public List<Vehicule> getVehicules() { return vehicules.tout(); }
    public List<Chauffeur> getChauffeurs() { return chauffeurs.tout(); }
    public List<Mission> getMissions() { return missions.tout(); }
    public List<Incident> getIncidents() { return new ArrayList<>(incidents); }

    public Vehicule trouverVehicule(String immat) { return vehicules.trouver(immat); }
    public Chauffeur trouverChauffeur(String id) { return chauffeurs.trouver(id); }
    public Mission trouverMission(String id) { return missions.trouver(id); }

    public void affecterMission(String idMission, String immat, String idChauffeur)
            throws VehiculeIndisponibleException, PermisInsuffisantException {
        Mission m = missions.trouver(idMission);
        Vehicule v = vehicules.trouver(immat);
        Chauffeur c = chauffeurs.trouver(idChauffeur);

        if (v == null || c == null || m == null) {
            throw new IllegalArgumentException("Element introuvable");
        }
        if (v instanceof Assignable && !((Assignable) v).estDisponible()) {
            throw new VehiculeIndisponibleException("Vehicule " + immat + " non disponible");
        }
        if (v instanceof VehiculeLourd && !c.possedePermis(TypePermis.C)) {
            throw new PermisInsuffisantException("Permis C requis pour conduire " + immat);
        }
        if (!c.isDisponible()) {
            throw new VehiculeIndisponibleException("Chauffeur " + idChauffeur + " non disponible");
        }

        m.affecter(v, c);
        if (v instanceof Assignable) ((Assignable) v).assigner();
        c.setDisponible(false);
    }

    public void terminerMission(String idMission) {
        Mission m = missions.trouver(idMission);
        if (m == null) return;
        m.terminer();
        if (m.getVehicule() != null && m.getVehicule() instanceof Assignable) {
            ((Assignable) m.getVehicule()).liberer();
        }
        if (m.getChauffeur() != null) {
            m.getChauffeur().setDisponible(true);
            m.getChauffeur().incrementerMissions();
        }
    }

    public void ajouterIncident(Incident i) { incidents.add(i); }

    public List<Vehicule> rechercherVehicules(String motCle, String categorie, EtatVehicule etat) {
        return vehicules.tout().stream()
                .filter(v -> motCle == null || motCle.isEmpty()
                        || v.getImmatriculation().toLowerCase().contains(motCle.toLowerCase())
                        || v.getMarque().toLowerCase().contains(motCle.toLowerCase())
                        || v.getModele().toLowerCase().contains(motCle.toLowerCase()))
                .filter(v -> categorie == null || categorie.isEmpty() || v.getCategorie().equalsIgnoreCase(categorie))
                .filter(v -> etat == null || v.getEtat() == etat)
                .collect(Collectors.toList());
    }

    public List<Mission> rechercherMissions(StatutMission statut, String type, String depart) {
        return missions.tout().stream()
                .filter(m -> statut == null || m.getStatut() == statut)
                .filter(m -> type == null || type.isEmpty() || m.getType().equalsIgnoreCase(type))
                .filter(m -> depart == null || depart.isEmpty() || m.getDepart().toLowerCase().contains(depart.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Vehicule> vehiculesDisponibles() {
        return vehicules.filtrer(v -> v instanceof Assignable && ((Assignable) v).estDisponible());
    }

    public List<Chauffeur> chauffeursDisponibles() {
        return chauffeurs.filtrer(Chauffeur::isDisponible);
    }

    public List<Vehicule> vehiculesAEntretenir() {
        return vehicules.tout().stream()
                .filter(v -> v instanceof Maintenable)
                .filter(v -> ((Maintenable) v).entretienUrgent())
                .collect(Collectors.toList());
    }

    public PriorityQueue<Mission> getFilePriorite() { return filePriorite; }
}
