package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.data;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerDataTabPanel extends JScrollPane
{
    private JTable table;

    public BorrowerDataTabPanel(JTable table)
    {
        Objects.requireNonNull(table);
        this.table = table;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, table, 0, 0, GridBagConstraints.CENTER, layoutResult.getConstraints());
    }

    public JTable getTable()
    {
        return table;
    }
}
