package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanButtonPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanCreateUpdatePanel extends JPanel
{
    private IView view;

    private LoanTextfieldPanel textfieldPanel;
    private LoanButtonPanel buttonPanel;

    public LoanCreateUpdatePanel(IView view) throws NullPointerException
    {
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        textfieldPanel = new LoanTextfieldPanel(view, new LabelledTextfield("Borrower ID:"), new LabelledTextfield("Amount:"), new LabelledTextfield("Interest rate:"), new LabelledTextfield("Term:"));
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());

        buttonPanel = new LoanButtonPanel(view, null, null, null, textfieldPanel);
        UIFactory.addComponentFor(this, buttonPanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    public IView getView()
    {
        return view;
    }

    public LoanTextfieldPanel getTextfieldPanel()
    {
        return textfieldPanel;
    }

    public LoanButtonPanel getButtonPanel()
    {
        return buttonPanel;
    }
}
