package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action.BorrowerRefreshActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action.BorrowerUpdateActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerButtonPanel extends JPanel
{
    private IView view;
    private BorrowerTextfieldPanel textfieldPanel;

    private BorrowerCreateActionListener createActionListener;

    private BorrowerUpdateActionListener updateActionListener;

    private BorrowerRefreshActionListener refreshActionListener;

    private JButton createButton, updateButton, refreshButton;

    /**
     * @param view
     * @param createBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @param updateBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @throws NullPointerException
     */
    public BorrowerButtonPanel(IView view, ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel, ScrollableTablePanel scrollableTablePanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(scrollableTablePanel);

        this.view = view;
        this.textfieldPanel = textfieldPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        setCreateButton(createBorrowerViewHandler, readBorrowerViewHandler);
        setUpdateButton(readBorrowerViewHandler, updateBorrowerViewHandler, scrollableTablePanel);
        setRefreshButton(readBorrowerViewHandler);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, refreshButton, 2, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        setCreateButton(createBorrowerViewHandler, readBorrowerViewHandler);
    }

    public void setUpdateBorrowerViewHandler(IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, ScrollableTablePanel scrollableTablePanel) throws NullPointerException
    {
        Objects.requireNonNull(scrollableTablePanel);

        setUpdateButton(readBorrowerViewHandler, updateBorrowerViewHandler, scrollableTablePanel);
    }

    public void setRefreshBorrowerViewHandler(IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        setRefreshButton(readBorrowerViewHandler);
    }

    private void setRefreshButton(IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        if(refreshButton == null)
            refreshButton = new JButton("Refresh");

        if(refreshActionListener != null) // remove old action listener
            refreshButton.removeActionListener(refreshActionListener);

        // create (new) one and assign it
        refreshActionListener = new BorrowerRefreshActionListener(readBorrowerViewHandler);
        refreshButton.addActionListener(refreshActionListener);
    }

    private void setCreateButton(ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        if(createButton == null)
            createButton = new JButton("Create");

        if(createActionListener != null) // remove old action listener
            createButton.removeActionListener(createActionListener);

        // create (new) one and assign it
        createActionListener = new BorrowerCreateActionListener(view, createBorrowerViewHandler, readBorrowerViewHandler, textfieldPanel);
        createButton.addActionListener(createActionListener);
    }

    private void setUpdateButton(IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, ScrollableTablePanel scrollableTablePanel) throws NullPointerException
    {
        Objects.requireNonNull(scrollableTablePanel);

        if(updateButton == null)
            updateButton = new JButton("Update");

        if(updateActionListener != null) // remove old action listener
            updateButton.removeActionListener(updateActionListener);

        // create (new) one and assign it
        updateActionListener = new BorrowerUpdateActionListener(view, updateBorrowerViewHandler, readBorrowerViewHandler, textfieldPanel, scrollableTablePanel);
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

    public JButton getRefreshButton()
    {
        return refreshButton;
    }
}
