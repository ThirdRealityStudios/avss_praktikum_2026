package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area.textfield.LoanConditionsPanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area.textfield.LoanUpdateIDPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanTextfieldPanel extends JPanel
{
    private LoanUpdateIDPanel updateIDPanel;
    private LoanConditionsPanel conditionsPanel;

    public LoanTextfieldPanel(LoanUpdateIDPanel updateIDPanel, LoanConditionsPanel conditionsPanel) throws NullPointerException
    {
        Objects.requireNonNull(updateIDPanel);
        Objects.requireNonNull(conditionsPanel);

        this.updateIDPanel = updateIDPanel;
        this.conditionsPanel = conditionsPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, updateIDPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, conditionsPanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    public LoanUpdateIDPanel getUpdateIDPanel() {
        return updateIDPanel;
    }

    public LoanConditionsPanel getConditionsPanel() {
        return conditionsPanel;
    }
}
