package de.fhms.jp253163.avss.arch.mvp.lms.view.factory;

import javax.swing.*;
import java.awt.*;

public class UIFactory
{
    public static LayoutResultDTO createGridBagLayoutFor(JComponent paneOrPanel) throws NullPointerException
    {
        // Set and prepare correct layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        layout.setConstraints(paneOrPanel, constraints);
        paneOrPanel.setLayout(layout);

        return new LayoutResultDTO(layout, constraints);
    }

    /**
     * Sources for help:
     *
     * https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fflylib.com%2Fbooks%2Fen%2F3.441.1.99%2F1%2F&ved=0CBYQjRxqFwoTCNCJvrik15QDFQAAAAAdAAAAABAF&opi=89978449
     * https://javabeginners.de/Layout/GridBagLayout.php
     *
     * @param panel
     * @param component
     * @param gridx
     * @param gridy
     * @param anchor E.g. GridBagConstraints.PAGE_START
     * @param constraints
     * @throws NullPointerException
     */
    public static void addComponentFor(JPanel panel, JComponent component, int gridx, int gridy, int anchor, GridBagConstraints constraints) throws NullPointerException
    {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.anchor = anchor;
        constraints.weightx = 2.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, constraints);
    }
}
