package de.fhms.jp253163.avss.arch.clientserver.lms.client.view;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SwingPanel extends JPanel
{
    public SwingPanel(MessagePanel messagePanel, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(tabsPanel);
        Objects.requireNonNull(messagePanel);

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, messagePanel, 0, 0, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, tabsPanel, 0, 1, GridBagConstraints. LINE_END, layoutResult.getConstraints());
    }
}