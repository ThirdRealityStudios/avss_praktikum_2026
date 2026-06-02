package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanUpdateIDPanel extends JPanel
{
    private LabelledTextfield borrowerID;

    public LoanUpdateIDPanel(LabelledTextfield borrowerID) throws NullPointerException
    {
        Objects.requireNonNull(borrowerID);

        this.borrowerID = borrowerID;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, borrowerID, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public LabelledTextfield getBorrowerID()
    {
        return borrowerID;
    }
}
