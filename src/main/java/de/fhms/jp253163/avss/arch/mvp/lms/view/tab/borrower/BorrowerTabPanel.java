package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.data.BorrowerDataTabPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerTabPanel extends JPanel
{
    private BorrowerDataTabPanel dataTabPanel;
    private BorrowerControlsPanel controlsTabPanel;

    public BorrowerTabPanel(BorrowerDataTabPanel dataTabPanel, BorrowerControlsPanel controlsTabPanel)
    {
        Objects.requireNonNull(dataTabPanel);
        Objects.requireNonNull(controlsTabPanel);

        this.dataTabPanel = dataTabPanel;
        this.controlsTabPanel = controlsTabPanel;

        initLayout();
    }

    private void initLayout()
    {
        GridBagLayout layout = new GridBagLayout();

        GridBagConstraints constraints = new GridBagConstraints();
        layout.setConstraints(this, constraints);
        setLayout(layout);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        add(dataTabPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(controlsTabPanel, constraints);
    }

    public BorrowerDataTabPanel getDataTabPanel()
    {
        return dataTabPanel;
    }

    public BorrowerControlsPanel getControlsTabPanel()
    {
        return controlsTabPanel;
    }
}
