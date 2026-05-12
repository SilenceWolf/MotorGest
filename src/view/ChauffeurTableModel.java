package view;
import model.Chauffeur;
import model.TypePermis;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ChauffeurTableModel extends AbstractTableModel {

    private final String[] colonnes = {"ID", "Nom", "Prenom", "Permis", "Disponible", "Missions"};
    private List<Chauffeur> donnees;

    public ChauffeurTableModel(List<Chauffeur> donnees) { this.donnees = donnees; }

    public void setDonnees(List<Chauffeur> donnees) {
        this.donnees = donnees;
        fireTableDataChanged();
    }

    public Chauffeur getChauffeurAt(int row) { return donnees.get(row); }

    @Override public int getRowCount() { return donnees.size(); }
    @Override public int getColumnCount() { return colonnes.length; }
    @Override public String getColumnName(int col) { return colonnes[col]; }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 4) return String.class;
        if (col == 5) return Integer.class;
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Chauffeur c = donnees.get(row);
        switch (col) {
            case 0: return c.getId();
            case 1: return c.getNom();
            case 2: return c.getPrenom();
            case 3:
                StringBuilder sb = new StringBuilder();
                for (TypePermis p : c.getPermis()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(p);
                }
                return sb.toString();
            case 4: return c.isDisponible() ? "DISPONIBLE" : "EN_MISSION";
            case 5: return c.getNbMissionsEffectuees();
            default: return "";
        }
    }
}
