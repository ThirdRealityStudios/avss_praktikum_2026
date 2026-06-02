package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.LoanCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.LoanRefreshActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.LoanUpdateActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanButtonPanel extends JPanel
{
    private IView view;
    private LoanTextfieldPanel textfieldPanel;

    private LoanCreateActionListener createActionListener;

    private LoanUpdateActionListener updateActionListener, bookInstallmentActionListener;

    private LoanRefreshActionListener refreshActionListener;

    private JButton createButton, updateButton, refreshButton, bookInstallmentButton;

    /**
     * @param view
     * @param createLoanViewHandler Or null allowed too (do nothing at this event)
     * @param updateLoanViewHandler Or null allowed too (do nothing at this event)
     * @throws NullPointerException
     */
    public LoanButtonPanel(IView view, ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, LoanTextfieldPanel textfieldPanel, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(tableLoan);

        this.view = view;
        this.textfieldPanel = textfieldPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        setCreateButton(createLoanViewHandler, readLoanViewHandler);
        setUpdateButton(readLoanViewHandler, updateLoanViewHandler, tableLoan);
        setRefreshButton(readLoanViewHandler);
        setBookInstallmentButton(readLoanViewHandler, updateLoanViewHandler, tableLoan);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, refreshButton, 2, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, bookInstallmentButton, 3, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public void setCreateLoanViewHandler(ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        setCreateButton(createLoanViewHandler, readLoanViewHandler);
    }

    public void setUpdateLoanViewHandler(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(tableLoan);

        setUpdateButton(readLoanViewHandler, updateLoanViewHandler, tableLoan);
    }

    public void setRefreshLoanViewHandler(IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        setRefreshButton(readLoanViewHandler);
    }

    public void setBookInstallmentLoanViewHandler(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(tableLoan);

        setBookInstallmentButton(readLoanViewHandler, updateLoanViewHandler, tableLoan);
    }

    private void setRefreshButton(IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        if(refreshButton == null)
            refreshButton = new JButton("Refresh");

        if(refreshActionListener != null) // remove old action listener
            refreshButton.removeActionListener(refreshActionListener);

        // create (new) one and assign it
        refreshActionListener = new LoanRefreshActionListener(readLoanViewHandler);
        refreshButton.addActionListener(refreshActionListener);
    }

    private void setCreateButton(ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        if(createButton == null)
            createButton = new JButton("Create");

        if(createActionListener != null) // remove old action listener
            createButton.removeActionListener(createActionListener);

        // create (new) one and assign it
        createActionListener = new LoanCreateActionListener(view, createLoanViewHandler, readLoanViewHandler, textfieldPanel);
        createButton.addActionListener(createActionListener);
    }

    private void setUpdateButton(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(tableLoan);

        if(updateButton == null)
            updateButton = new JButton("Update");

        if(updateActionListener != null) // remove old action listener
            updateButton.removeActionListener(updateActionListener);

        // create (new) one and assign it
        updateActionListener = new LoanUpdateActionListener(view, updateLoanViewHandler, readLoanViewHandler, textfieldPanel, tableLoan);
        updateButton.addActionListener(updateActionListener);
    }

    private void setBookInstallmentButton(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(tableLoan);

        if(bookInstallmentButton == null)
            bookInstallmentButton = new JButton("Book Installment");

        if(bookInstallmentActionListener != null) // remove old action listener
            bookInstallmentButton.removeActionListener(updateActionListener);

        // create (new) one and assign it (note here that the LoanUpdateActionListener is enough - it can handle both: update button and book installment button)
        bookInstallmentActionListener = new LoanUpdateActionListener(view, updateLoanViewHandler, readLoanViewHandler, textfieldPanel, tableLoan);
        bookInstallmentButton.addActionListener(updateActionListener);
    }

    public JButton getCreateButton()
    {
        return createButton;
    }

    public JButton getUpdateButton()
    {
        return updateButton;
    }

    public JButton getRefreshButton()
    {
        return refreshButton;
    }

    public JButton getBookInstallmentButton()
    {
        return bookInstallmentButton;
    }
}
