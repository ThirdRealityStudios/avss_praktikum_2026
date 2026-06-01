package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
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

    public LoanCreateUpdatePanel(IView view, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tabsPanel);

        this.view = view;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        textfieldPanel = new LoanTextfieldPanel(view, new LabelledTextfield("Borrower ID:"), new LabelledTextfield("Amount (not updatable):"), new LabelledTextfield("Interest rate (not updatable):"), new LabelledTextfield("Term (not updatable):"));
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());

        IReadLoanViewHandler readLoanViewHandler = new IReadLoanViewHandler() {
            @Override
            public void invoke(IRequestDTOLoanRead readLoanDTO) {

            }
        };

        ICreateLoanViewHandler createLoanViewHandler = new ICreateLoanViewHandler() {
            @Override
            public void invoke(IRequestDTOLoanCreate createLoanDTO) {

            }
        };

        IUpdateLoanViewHandler updateLoanViewHandler = new IUpdateLoanViewHandler() {
            @Override
            public void invoke(IRequestDTOLoanUpdate loanUpdateDTO) {

            }
        };

        buttonPanel = new LoanButtonPanel(view, readLoanViewHandler, createLoanViewHandler, updateLoanViewHandler, textfieldPanel, tabsPanel);
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
