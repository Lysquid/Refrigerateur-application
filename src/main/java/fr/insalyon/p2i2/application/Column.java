package fr.insalyon.p2i2.application;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Insets;

public class Column extends JPanel {

    public Column() {
        setBackground(Color.GREEN);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public Insets getInsets() {
        int insets = 20;
        return new Insets(insets, insets, insets, insets);
    }

}