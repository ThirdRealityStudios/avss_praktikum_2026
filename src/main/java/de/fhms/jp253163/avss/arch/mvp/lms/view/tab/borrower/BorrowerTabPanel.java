package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private BorrowerControlsPanel controlsTabPanel;

    public BorrowerTabPanel(ScrollableTablePanel scrollableTablePanel, BorrowerControlsPanel controlsTabPanel)
    {
        Objects.requireNonNull(scrollableTablePanel);
        Objects.requireNonNull(controlsTabPanel);

        this.scrollableTablePanel = scrollableTablePanel;
        this.controlsTabPanel = controlsTabPanel;

        initLayout();
    }

    private void initLayout()
    {
        GridBagLayout layout = new GridBagLayout();

        GridBagConstraints constraints = new GridBagConstraints();
        layout.setConstraints(this, constraints);
        setLayout(layout);

        Insets insets = new Insets(0, 30, 30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = insets;
        add(scrollableTablePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(controlsTabPanel, constraints);
    }

    public ScrollableTablePanel getDataTabPanel()
    {
        return scrollableTablePanel;
    }

    public BorrowerControlsPanel getControlsPanel()
    {
        return controlsTabPanel;
    }
}
