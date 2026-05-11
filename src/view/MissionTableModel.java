package view;

import model.Mission;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MissionTableModel extends AbstractTableModel {

    private final String[] colonnes = {"ID", "Type", "Depart", "Arrivee", "Date debut", "Distance", "Statut", "Vehicule", "Chauffeur", "Cout (EUR)"};
    private List<Mission> donnees;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MissionTableModel(List<Mission> donnees) { this.donnees = donnees; }

    public void setDonnees(List<Mission> donnees) {
        this.donnees = donnees;
        fireTableDataChanged();
    }

    public Mission getMissionAt(int row) { return donnees.get(row); }

    @Override public int getRowCount() { return donnees.size(); }
    @Override public int getColumnCount() { return colonnes.length; }
    @Override public String getColumnName(int col) { return colonnes[col]; }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 5) return Integer.class;
        if (col == 9) return Double.class;
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Mission m = donnees.get(row);
        switch (col) {
            case 0: return m.getId();
            case 1: return m.getType();
            case 2: return m.getDepart();
            case 3: return m.getArrivee();
            case 4: return m.getDateDebut() != null ? m.getDateDebut().format(FMT) : "";
            case 5: return m.getDistanceKm();
            case 6: return m.getStatut().toString();
            case 7: return m.getVehicule() != null ? m.getVehicule().getImmatriculation() : "-";
            case 8: return m.getChauffeur() != null ? m.getChauffeur().getId() : "-";
            case 9: return m.calculerCout();
            default: return "";
        }
    }
}
