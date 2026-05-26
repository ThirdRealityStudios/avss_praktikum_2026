package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.BorrowerUpdateDeletePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerControlsPanel extends JPanel
{
    private BorrowerCreateUpdatePanel createPanel;
    private BorrowerUpdateDeletePanel updateDeletePanel;

    public BorrowerControlsPanel(BorrowerCreateUpdatePanel createUpdatePanel, BorrowerUpdateDeletePanel deletePanel)
    {
        Objects.requireNonNull(createUpdatePanel);
        Objects.requireNonNull(deletePanel);

        this.createPanel = createUpdatePanel;
        this.updateDeletePanel = deletePanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, createUpdatePanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, deletePanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }
}
