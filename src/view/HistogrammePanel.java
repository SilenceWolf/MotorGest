package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class HistogrammePanel extends JPanel {

    private Map<String, Long> data = new LinkedHashMap<>();
    private final String titre;

    public HistogrammePanel(String titre) {
        this.titre = titre;
        setBackground(Theme.FOND_CARTE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDURE, 1),
                new EmptyBorder(12, 12, 12, 12)));
        setPreferredSize(new Dimension(400, 280));
    }

    public void setData(Map<String, Long> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(Theme.PRIMAIRE);
        g2.setFont(Theme.POLICE_BOLD);
        g2.drawString(titre, 8, 20);

        if (data == null || data.isEmpty()) {
            g2.setColor(Theme.TEXTE_DOUX);
            g2.setFont(Theme.POLICE);
            g2.drawString("Aucune donnee", w / 2 - 40, h / 2);
            return;
        }

        int margeBas = 50;
        int margeHaut = 40;
        int margeGauche = 30;
        int margeDroite = 20;

        long max = 1;
        for (Long v : data.values()) if (v > max) max = v;

        int n = data.size();
        int largeurDispo = w - margeGauche - margeDroite;
        int largeurBarre = Math.max(20, largeurDispo / (n * 2));
        int espace = largeurBarre;
        int x = margeGauche + espace / 2;

        g2.setColor(Theme.BORDURE);
        g2.drawLine(margeGauche, h - margeBas, w - margeDroite, h - margeBas);

        Color[] couleurs = {
                new Color(0x1F, 0x3A, 0x5F),
                new Color(0x10, 0xA3, 0x7C),
                new Color(0xD0, 0x86, 0x42),
                new Color(0x6A, 0x4C, 0x93),
                new Color(0xC0, 0x39, 0x2B),
                new Color(0x38, 0x86, 0x97)
        };
        int i = 0;
        for (Map.Entry<String, Long> e : data.entrySet()) {
            int hauteur = (int) ((h - margeBas - margeHaut) * ((double) e.getValue() / max));
            g2.setColor(couleurs[i % couleurs.length]);
            g2.fillRoundRect(x, h - margeBas - hauteur, largeurBarre, hauteur, 6, 6);

            g2.setColor(Theme.TEXTE);
            g2.setFont(Theme.POLICE);
            FontMetrics fm = g2.getFontMetrics();
            int lw = fm.stringWidth(e.getKey());
            g2.drawString(e.getKey(), x + (largeurBarre - lw) / 2, h - margeBas + 18);
            String valStr = String.valueOf(e.getValue());
            int vw = fm.stringWidth(valStr);
            g2.setFont(Theme.POLICE_BOLD);
            g2.drawString(valStr, x + (largeurBarre - vw) / 2, h - margeBas - hauteur - 6);

            x += largeurBarre + espace;
            i++;
        }
    }
}
