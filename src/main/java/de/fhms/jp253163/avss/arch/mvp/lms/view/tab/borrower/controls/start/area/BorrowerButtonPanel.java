package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action.BorrowerCreateActionListener;
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

    private JButton createButton, updateButton;

    /**
     *
     * @param view
     * @param createBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @param updateBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @throws NullPointerException
     */
    public BorrowerButtonPanel(IView view, ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.textfieldPanel = textfieldPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        setCreateButton(createBorrowerViewHandler, readBorrowerViewHandler);
        setUpdateButton(readBorrowerViewHandler, updateBorrowerViewHandler);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        setCreateButton(createBorrowerViewHandler, readBorrowerViewHandler);
    }

    public void setUpdateBorrowerViewHandler(IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        setUpdateButton(readBorrowerViewHandler, updateBorrowerViewHandler);
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

    private void setUpdateButton(IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        if(updateButton == null)
            updateButton = new JButton("Update");

        if(updateActionListener != null) // remove old action listener
            updateButton.removeActionListener(updateActionListener);

        // create (new) one and assign it
        updateActionListener = new BorrowerUpdateActionListener(view, updateBorrowerViewHandler, readBorrowerViewHandler, textfieldPanel);
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
