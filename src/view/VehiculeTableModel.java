package view;

import model.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VehiculeTableModel extends AbstractTableModel {

    private final String[] colonnes = {"Immat.", "Categorie", "Marque", "Modele", "Km", "Etat", "Entretien"};
    private List<Vehicule> donnees;

    public VehiculeTableModel(List<Vehicule> donnees) {
        this.donnees = donnees;
    }

    public void setDonnees(List<Vehicule> donnees) {
        this.donnees = donnees;
        fireTableDataChanged();
    }

    public Vehicule getVehiculeAt(int row) {
        return donnees.get(row);
    }

    @Override public int getRowCount() { return donnees.size(); }
    @Override public int getColumnCount() { return colonnes.length; }
    @Override public String getColumnName(int col) { return colonnes[col]; }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 4) return Integer.class;
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Vehicule v = donnees.get(row);
        switch (col) {
            case 0: return v.getImmatriculation();
            case 1: return v.getCategorie();
            case 2: return v.getMarque();
            case 3: return v.getModele();
            case 4: return v.getKilometrage();
            case 5: return v.getEtat().toString();
            case 6:
                if (v instanceof Maintenable) {
                    return ((Maintenable) v).entretienUrgent() ? "Urgent" : "OK";
                }
                return "-";
            default: return "";
        }
    }
}
