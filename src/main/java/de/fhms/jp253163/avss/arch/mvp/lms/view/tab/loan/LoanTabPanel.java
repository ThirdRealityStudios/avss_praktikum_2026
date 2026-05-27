package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.data.LoanDataTabPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanTabPanel extends JPanel
{
    /*
    private LoanDataTabPanel dataTabPanel;
    private LoanControlsPanel controlsTabPanel;

    public LoanTabPanel(LoanDataTabPanel dataTabPanel, LoanControlsPanel controlsTabPanel)
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

        Insets insets = new Insets(0, 30, 30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = insets;
        add(dataTabPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(controlsTabPanel, constraints);
    }

    public LoanDataTabPanel getDataTabPanel()
    {
        return dataTabPanel;
    }

    public LoanControlsPanel getControlsPanel()
    {
        return controlsTabPanel;
    }
     */
}
