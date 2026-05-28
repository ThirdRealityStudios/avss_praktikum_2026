package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.LoanCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.LoanUpdateActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanButtonPanel extends JPanel
{
    private IView view;

    private LoanCreateActionListener createActionListener;

    private LoanUpdateActionListener updateActionListener;

    private LoanTextfieldPanel textfieldPanel;
    private JButton createButton, updateButton;

    /**
     *
     * @param view
     * @param readLoanViewHandler Or null allowed too (do nothing at this event)
     * @param createLoanViewHandler Or null allowed too (do nothing at this event)
     * @param updateLoanViewHandler Or null allowed too (do nothing at this event)
     * @throws NullPointerException
     */
    public LoanButtonPanel(IView view, IReadLoanViewHandler readLoanViewHandler, ICreateLoanViewHandler createLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler, LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.textfieldPanel = textfieldPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        initCreateButton(createLoanViewHandler, readLoanViewHandler);
        initUpdateButton(readLoanViewHandler, updateLoanViewHandler);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public void setCreateLoanViewHandler(ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        initCreateButton(createLoanViewHandler, readLoanViewHandler);
    }

    public void setUpdateLoanViewHandler(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler) throws NullPointerException
    {
        initUpdateButton(readLoanViewHandler, updateLoanViewHandler);
    }

    private void initCreateButton(ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        if(createButton == null)
            createButton = new JButton("Create");

        if(createActionListener != null) // remove old action listener
            createButton.removeActionListener(createActionListener);

        // create (new) one and assign it
        createActionListener = new LoanCreateActionListener(view, createLoanViewHandler, readLoanViewHandler, textfieldPanel);
        createButton.addActionListener(createActionListener);
    }

    private void initUpdateButton(IReadLoanViewHandler readLoanViewHandler, IUpdateLoanViewHandler updateLoanViewHandler) throws NullPointerException
    {
        if(updateButton == null)
            updateButton = new JButton("Update");

        if(updateActionListener != null) // remove old action listener
            updateButton.removeActionListener(updateActionListener);

        // create (new) one and assign it
        updateActionListener = new LoanUpdateActionListener(view, updateLoanViewHandler, readLoanViewHandler, textfieldPanel);
        updateButton.addActionListener(updateActionListener);
    }

    public JButton getCreateButton()
    {
        return createButton;
    }

    public JButton getUpdateButton()
    {
        return updateButton;
    }
}
