package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.center.LoanDeletePanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.LoanCreateUpdatePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanControlsPanel extends JPanel
{
    private LoanCreateUpdatePanel createUpdatePanel;
    private LoanDeletePanel deletePanel;

    public LoanControlsPanel(LoanCreateUpdatePanel createUpdatePanel, LoanDeletePanel deletePanel)
    {
        Objects.requireNonNull(createUpdatePanel);
        Objects.requireNonNull(deletePanel);

        this.createUpdatePanel = createUpdatePanel;
        this.deletePanel = deletePanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, createUpdatePanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, deletePanel, 0, 1, GridBagConstraints.CENTER, layoutResult.getConstraints());
    }

    public LoanCreateUpdatePanel getCreateUpdatePanel() {
        return createUpdatePanel;
    }

    public LoanDeletePanel getDeletePanel() {
        return deletePanel;
    }
}
