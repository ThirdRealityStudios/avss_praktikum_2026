package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SwingPanel extends JScrollPane
{
    public SwingPanel(MessagePanel messagePanel, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(tabsPanel);
        Objects.requireNonNull(messagePanel);

        JPanel panel = new JPanel();

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(panel);

        UIFactory.addComponentFor(panel, tabsPanel, 0, 0, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(panel, messagePanel, 0, 1, GridBagConstraints.LINE_END, layoutResult.getConstraints());

        add(panel);
    }
}