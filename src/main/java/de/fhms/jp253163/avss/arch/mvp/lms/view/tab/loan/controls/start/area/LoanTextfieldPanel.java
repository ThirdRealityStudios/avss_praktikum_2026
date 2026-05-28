package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanTextfieldPanel extends JPanel
{
    private IView view;
    private LabelledTextfield loanID, borrowerID, amount, interestRate, term;

    public LoanTextfieldPanel(IView view, LabelledTextfield loanID, LabelledTextfield borrowerID, LabelledTextfield amount, LabelledTextfield interestRate, LabelledTextfield term) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(loanID);
        Objects.requireNonNull(borrowerID);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(interestRate);
        Objects.requireNonNull(term);

        this.view = view;
        this.loanID = loanID;
        this.borrowerID = borrowerID;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, loanID, 0, 0, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, borrowerID, 0, 1, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, amount, 0, 2, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, interestRate, 0, 3, GridBagConstraints.LINE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, interestRate, 0, 4, GridBagConstraints.LINE_START, layoutResult.getConstraints());
    }

    public IView getView()
    {
        return view;
    }

    public LabelledTextfield getLoanID() {
        return loanID;
    }

    public LabelledTextfield getBorrowerID() {
        return borrowerID;
    }

    public LabelledTextfield getAmout() {
        return amount;
    }

    public LabelledTextfield getInterestRate() {
        return interestRate;
    }

    public LabelledTextfield getTerm() {
        return term;
    }
}
