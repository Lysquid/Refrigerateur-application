package fr.insalyon.p2i2.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JLabel;

public class Graph extends Compo {

    private double xA;
    private double xB;
    private double yA;
    private double yB;
    private JLabel titleTag;
    private LinkedList<Double> abscisse;
    private LinkedList<Double> ordonnee;
    private Color color;
    private String title;

    public static final int POINTS = 180;

    public Graph(String title, Color color) {
        this.title = title;
        this.color = color;
        abscisse = new LinkedList<Double>();
        ordonnee = new LinkedList<Double>();

        titleTag = new JLabel(title);
        titleTag.setForeground(Color.BLACK);
        titleTag.setFont(boldFont);
        add(titleTag);
        xA = -5;
        xB = 180;
        yA = -10;
        yB = 10;
    }

    @Override
    public String toString() {
        return title;
    }

    public double min(LinkedList<Double> list) {
        double min = list.get(0);
        for (double m : list) {
            if (m < min) {
                min = m;
            }
        }
        return min;
    }

    public double max(LinkedList<Double> list) {
        double max = list.get(0);
        for (double m : list) {
            if (m > max) {
                max = m;
            }
        }
        return max;
    }

    public Point toScreen(double x, double y) {
        int hauteurVisibleFenetre = getSize().height + getInsets().top + getInsets().bottom;
        int largeurVisibleFenetre = getSize().width + getInsets().left + getInsets().right;
        int xEcran = (int) ((x - xA) / (xB - xA) * largeurVisibleFenetre);
        int yEcran = (int) ((y - yA) / (yB - yB) * hauteurVisibleFenetre);
        return new Point(xEcran + getInsets().left, yEcran + getInsets().top);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Axes
        Point A, B;
        A = toScreen(0, yA);
        B = toScreen(0, yB);
        g.setColor(Color.black);
        g.drawLine(A.x, A.y, B.x, B.y);

        A = toScreen(xA, 0);
        B = toScreen(xB, 0);
        g.drawLine(A.x, A.y, B.x, B.y);

        // Points
        g.setColor(color);
        double x = abscisse.get(0);
        double y = ordonnee.get(0);
        A = toScreen(x, y);
        for (int i = 1; i < abscisse.size(); i++) {
            x = abscisse.get(i);
            y = ordonnee.get(i);
            B = toScreen(x, y);
            g.drawLine(A.x, A.y, B.x, B.y);
            A = B;
        }
    }

    public void update(double nY) {
        if (abscisse.size() >= POINTS) {
            ordonnee.remove(0);
        } else {
            abscisse.add(abscisse.size() + 1d);
        }
        ordonnee.add(nY);
        yA = min(ordonnee) - 2;
        yB = max(ordonnee) + 2;
        repaint();
    }

    public void init(LinkedList<Double> l) {
        for (int i = 0; i < l.size(); i++) {
            abscisse.add((double) i + 1);
            ordonnee.add(l.get(i));
        }
        repaint();
    }

    /*
     * L'idée c'est qu'on limite la taille des deux listes à 120 (ou 180) élements
     * ce qui
     * correspond à 1 point par seconde pendant 2 (ou 3) minutes et à chaque fois
     * qu'il y a
     * une nouvelle mesure on vire l'element d'indice 0 et on rajoute la nouvelle
     * valeur à
     * la fin puis on repaint.
     */

}
