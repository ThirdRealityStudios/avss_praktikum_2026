package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.components.controls.end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Objects;

public class ScrollableTablePanel extends JScrollPane
{
    private JTable table;

    public ScrollableTablePanel(JTable table) throws NullPointerException
    {
        super(table);

        Objects.requireNonNull(table);

        this.table = table;
    }

    public JTable getTable()
    {
        return table;
    }
}
