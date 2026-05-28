package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.LoanCreateUpdatePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanControlsPanel extends JPanel
{
    private LoanCreateUpdatePanel createUpdatePanel;
    private LoanDeletePanel deletePanel;
    private MessagePanel messagePanel;

    public LoanControlsPanel(LoanCreateUpdatePanel createUpdatePanel, LoanDeletePanel deletePanel, MessagePanel messagePanel)
    {
        Objects.requireNonNull(createUpdatePanel);
        Objects.requireNonNull(deletePanel);
        Objects.requireNonNull(messagePanel);

        this.createUpdatePanel = createUpdatePanel;
        this.deletePanel = deletePanel;
        this.messagePanel = messagePanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, createUpdatePanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, deletePanel, 0, 1, GridBagConstraints.CENTER, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, messagePanel, 0, 2, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    public LoanCreateUpdatePanel getCreateUpdatePanel()
    {
        return createUpdatePanel;
    }

    public LoanDeletePanel getDeletePanel()
    {
        return deletePanel;
    }

    public MessagePanel getMessagePanel()
    {
        return messagePanel;
    }
}
