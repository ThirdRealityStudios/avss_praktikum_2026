package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SwingPanel extends JPanel
{
    private MessagePanel messagePanel;
    private TabsPanel tabsPanel;

    public SwingPanel(MessagePanel messagePanel, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(tabsPanel);
        Objects.requireNonNull(messagePanel);

        this.tabsPanel = tabsPanel;
        this.messagePanel = messagePanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, tabsPanel, 0, 0, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, messagePanel, 0, 1, GridBagConstraints.LINE_END, layoutResult.getConstraints());
    }
}
