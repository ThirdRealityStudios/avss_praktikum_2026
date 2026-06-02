package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanButtonPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.LoanConditionsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.LoanUpdateIDPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanCreateUpdatePanel extends JPanel
{
    private IView view;

    private LoanTextfieldPanel textfieldPanel;
    private LoanButtonPanel buttonPanel;

    public LoanCreateUpdatePanel(IView view, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tableLoan);

        this.view = view;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        textfieldPanel = createTextfieldPanel();
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());

        buttonPanel = new LoanButtonPanel(view, null, null, null, textfieldPanel, tableLoan);
        UIFactory.addComponentFor(this, buttonPanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    private LoanTextfieldPanel createTextfieldPanel() throws NullPointerException
    {
        LoanUpdateIDPanel updateIDPanel = new LoanUpdateIDPanel(new LabelledTextfield("Borrower ID:"));
        LoanConditionsPanel conditionsPanel = new LoanConditionsPanel(new LabelledTextfield("Amount (not updatable):"), new LabelledTextfield("Interest rate (not updatable):"), new LabelledTextfield("Term (not updatable):"));

        return new LoanTextfieldPanel(updateIDPanel, conditionsPanel);
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
