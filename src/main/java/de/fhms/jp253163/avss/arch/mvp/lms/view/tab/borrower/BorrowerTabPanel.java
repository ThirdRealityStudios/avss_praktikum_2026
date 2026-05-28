package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private BorrowerControlsPanel controlsPanel;

    public BorrowerTabPanel(ScrollableTablePanel scrollableTablePanel, BorrowerControlsPanel controlsPanel)
    {
        Objects.requireNonNull(scrollableTablePanel);
        Objects.requireNonNull(controlsPanel);

        this.scrollableTablePanel = scrollableTablePanel;
        this.controlsPanel = controlsPanel;

        initLayout();
    }

    private void initLayout()
    {
        LayoutResultDTO dto = UIFactory.createGridBagLayoutFor(this);

        Insets insets = new Insets(30, 30, 0, 30);

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 0;
        dto.getConstraints().anchor = GridBagConstraints.PAGE_START;
        dto.getConstraints().insets = insets;
        add(scrollableTablePanel, dto.getConstraints());

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 1;
        dto.getConstraints().anchor = GridBagConstraints.PAGE_END;
        dto.getConstraints().insets = new Insets(30, 30, 30, 30);
        add(controlsPanel, dto.getConstraints());
    }

    public ScrollableTablePanel getDataTabPanel()
    {
        return scrollableTablePanel;
    }

    public BorrowerControlsPanel getControlsPanel()
    {
        return controlsPanel;
    }
}
