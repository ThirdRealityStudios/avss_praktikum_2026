package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.BorrowerDeletePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerControlsPanel extends JPanel
{
    private BorrowerCreateUpdatePanel createUpdatePanel;
    private BorrowerDeletePanel deletePanel;

    public BorrowerControlsPanel(BorrowerCreateUpdatePanel createUpdatePanel, BorrowerDeletePanel deletePanel)
    {
        Objects.requireNonNull(createUpdatePanel);
        Objects.requireNonNull(deletePanel);

        this.createUpdatePanel = createUpdatePanel;
        this.deletePanel = deletePanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, createUpdatePanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, deletePanel, 0, 1, GridBagConstraints.CENTER, layoutResult.getConstraints());
    }

    public BorrowerCreateUpdatePanel getCreateUpdatePanel() {
        return createUpdatePanel;
    }

    public BorrowerDeletePanel getDeletePanel() {
        return deletePanel;
    }
}
