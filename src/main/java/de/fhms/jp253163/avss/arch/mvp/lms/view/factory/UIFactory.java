package de.fhms.jp253163.avss.arch.mvp.lms.view.factory;

import javax.swing.*;
import java.awt.*;

public class UIFactory
{
    public static LayoutResultDTO createGridBagLayoutFor(JPanel panel) throws NullPointerException
    {
        // Set and prepare correct layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        layout.setConstraints(panel, constraints);
        panel.setLayout(layout);

        return new LayoutResultDTO(layout, constraints);
    }

    public static void addComponentFor(JPanel panel, JComponent component, int gridx, int gridy, GridBagConstraints constraints) throws NullPointerException
    {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        panel.add(component, constraints);
    }
}
