package view;

import controller.GestionnaireFlotte;
import controller.Statistiques;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class PanneauStatistiques extends JPanel implements Rafraichissable {

    private final GestionnaireFlotte gf;
    private final Statistiques stats;
    private HistogrammePanel histoCategorie;
    private HistogrammePanel histoStatutMission;

    private JLabel kpiVehicules;
    private JLabel kpiMissions;
    private JLabel kpiKmMoyen;
    private JLabel kpiCoutTotal;

    public PanneauStatistiques(GestionnaireFlotte gf) {
        this.gf = gf;
        this.stats = new Statistiques(gf);
        setLayout(new BorderLayout());
        setBackground(Theme.FOND);
        construire();
        rafraichir();
    }

    private void construire() {
        add(Theme.enteteSection("Statistiques", "Tableau de bord et indicateurs cles de la flotte", "[S]"), BorderLayout.NORTH);

        JPanel centre = new JPanel(new BorderLayout(0, 12));
        centre.setBackground(Theme.FOND);
        centre.setBorder(new EmptyBorder(0, 16, 16, 16));

        centre.add(creerKpis(), BorderLayout.NORTH);
        centre.add(creerGraphiques(), BorderLayout.CENTER);
        add(centre, BorderLayout.CENTER);
    }

    private JPanel creerKpis() {
        JPanel p = new JPanel(new GridLayout(1, 4, 12, 0));
        p.setOpaque(false);
        kpiVehicules = new JLabel("0");
        kpiMissions = new JLabel("0");
        kpiKmMoyen = new JLabel("0 km");
        kpiCoutTotal = new JLabel("0 EUR");
        for (JLabel l : new JLabel[]{kpiVehicules, kpiMissions, kpiKmMoyen, kpiCoutTotal}) {
            l.setFont(new Font("Segoe UI", Font.BOLD, 22));
        }
        kpiVehicules.setForeground(Theme.PRIMAIRE);
        kpiMissions.setForeground(Theme.ACCENT);
        kpiKmMoyen.setForeground(Theme.WARN_FG);
        kpiCoutTotal.setForeground(Theme.DANGER);

        p.add(wrapKpi(kpiVehicules, "Total vehicules", Theme.PRIMAIRE));
        p.add(wrapKpi(kpiMissions, "Total missions", Theme.ACCENT));
        p.add(wrapKpi(kpiKmMoyen, "Km moyen / vehicule", Theme.WARN_FG));
        p.add(wrapKpi(kpiCoutTotal, "Cout total missions", Theme.DANGER));
        return p;
    }

    private JPanel wrapKpi(JLabel valeur, String titre, Color barColor) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Theme.FOND_CARTE);
        carte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDURE, 1),
                new EmptyBorder(12, 16, 12, 16)));

        JPanel bar = new JPanel();
        bar.setBackground(barColor);
        bar.setPreferredSize(new Dimension(4, 0));
        carte.add(bar, BorderLayout.WEST);

        JPanel contenu = new JPanel(new BorderLayout(0, 4));
        contenu.setOpaque(false);
        contenu.setBorder(new EmptyBorder(0, 12, 0, 0));
        JLabel t = new JLabel(titre);
        t.setFont(Theme.POLICE_SOUSTITRE);
        t.setForeground(Theme.TEXTE_DOUX);
        contenu.add(t, BorderLayout.NORTH);
        contenu.add(valeur, BorderLayout.CENTER);

        carte.add(contenu, BorderLayout.CENTER);
        return carte;
    }

    private JPanel creerGraphiques() {
        JPanel p = new JPanel(new GridLayout(1, 2, 12, 0));
        p.setOpaque(false);
        histoCategorie = new HistogrammePanel("Repartition par categorie de vehicule");
        histoStatutMission = new HistogrammePanel("Missions par statut");
        p.add(histoCategorie);
        p.add(histoStatutMission);
        return p;
    }

    @Override
    public void rafraichir() {
        kpiVehicules.setText(String.valueOf(gf.getVehicules().size()));
        kpiMissions.setText(String.valueOf(gf.getMissions().size()));
        kpiKmMoyen.setText(String.format("%.0f km", stats.kilometrageMoyen()));
        kpiCoutTotal.setText(String.format("%.2f EUR", stats.coutTotalMissions()));

        histoCategorie.setData(stats.repartitionParCategorie());

        Map<StatutMission, Long> parStatut = stats.repartitionMissionsParStatut();
        java.util.Map<String, Long> conv = new java.util.LinkedHashMap<>();
        for (Map.Entry<StatutMission, Long> e : parStatut.entrySet()) {
            conv.put(e.getKey().name(), e.getValue());
        }
        histoStatutMission.setData(conv);
    }
}
