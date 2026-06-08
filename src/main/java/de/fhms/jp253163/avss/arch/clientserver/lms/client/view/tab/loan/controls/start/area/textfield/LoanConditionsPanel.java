package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area.textfield;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.component.LabelledTextfield;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanConditionsPanel extends JPanel
{
    private LabelledTextfield amount, interestRate, term;

    public LoanConditionsPanel(LabelledTextfield amount, LabelledTextfield interestRate, LabelledTextfield term) throws NullPointerException
    {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(interestRate);
        Objects.requireNonNull(term);

        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, amount, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, interestRate, 0, 1, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, term, 0, 2, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public LabelledTextfield getAmount()
    {
        return amount;
    }

    public LabelledTextfield getInterestRate()
    {
        return interestRate;
    }

    public LabelledTextfield getTerm()
    {
        return term;
    }
}
