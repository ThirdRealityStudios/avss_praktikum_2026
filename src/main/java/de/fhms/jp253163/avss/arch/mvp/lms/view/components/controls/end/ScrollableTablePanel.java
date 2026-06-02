package de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end;

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

        table.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(new Dimension(getPreferredSize().width, table.getRowHeight()));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setPreferredSize(new Dimension(getPreferredSize().width, table.getRowHeight()));
            }

            @Override
            public void componentShown(ComponentEvent e) {
                setPreferredSize(new Dimension(getPreferredSize().width, table.getRowHeight()));
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    public JTable getTable()
    {
        return table;
    }
}
