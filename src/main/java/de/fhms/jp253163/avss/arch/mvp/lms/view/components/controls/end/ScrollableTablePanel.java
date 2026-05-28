package de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ScrollableTablePanel extends JScrollPane
{
    private JTable table;

    public ScrollableTablePanel(JTable table) throws NullPointerException
    {
        super(table);

        Objects.requireNonNull(table);

        this.table = table;

        // ScrollPaneLayout layout = new ScrollPaneLayout();
        // layout.

        // LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        // UIFactory.addComponentFor(this, table, 0, 0, GridBagConstraints.CENTER, layoutResult.getConstraints());
        // setLayout(layout);
    }

    public JTable getTable()
    {
        return table;
    }
}
