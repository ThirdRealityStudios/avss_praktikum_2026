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

    /**
     * @param panel
     * @param component
     * @param gridx
     * @param gridy
     * @param anchor E.g. GridBagConstraints.EAST
     * @param constraints
     * @throws NullPointerException
     */
    public static void addComponentFor(JPanel panel, JComponent component, int gridx, int gridy, int anchor, GridBagConstraints constraints) throws NullPointerException
    {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.anchor = anchor;
        panel.add(component, constraints);
    }
}
