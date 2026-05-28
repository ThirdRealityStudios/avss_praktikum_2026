package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.LoanControlsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private LoanControlsPanel controlsTabPanel;

    public LoanTabPanel(ScrollableTablePanel scrollableTablePanel, LoanControlsPanel controlsTabPanel)
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

    public LoanControlsPanel getControlsPanel()
    {
        return controlsTabPanel;
    }
}
