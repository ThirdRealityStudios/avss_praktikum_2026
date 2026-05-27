package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.BorrowerSharedLogic;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action.BorrowerUpdateActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerButtonPanel extends JPanel
{
    private IView view;

    private BorrowerCreateActionListener createActionListener;

    private BorrowerUpdateActionListener updateActionListener;

    private BorrowerSharedLogic sharedLogic;

    private JButton createButton, updateButton;

    /**
     *
     * @param view
     * @param createBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @param updateBorrowerViewHandler Or null allowed too (do nothing at this event)
     * @param sharedLogic
     * @throws NullPointerException
     */
    public BorrowerButtonPanel(IView view, ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerSharedLogic sharedLogic) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(sharedLogic);

        this.view = view;
        this.sharedLogic = sharedLogic;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        initCreateButton(createBorrowerViewHandler);
        initUpdateButton(updateBorrowerViewHandler);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler createBorrowerViewHandler) throws NullPointerException
    {
        initCreateButton(createBorrowerViewHandler);
    }

    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        initUpdateButton(updateBorrowerViewHandler);
    }

    private void initCreateButton(ICreateBorrowerViewHandler createBorrowerViewHandler) throws NullPointerException
    {
        if(createButton == null)
            createButton = new JButton("Create");

        if(createActionListener != null) // remove old action listener
            createButton.removeActionListener(createActionListener);

        // create (new) one and assign it
        createActionListener = new BorrowerCreateActionListener(view, createBorrowerViewHandler, sharedLogic);
        createButton.addActionListener(createActionListener);
    }

    private void initUpdateButton(IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        if(updateButton == null)
            updateButton = new JButton("Update");

        if(updateActionListener != null) // remove old action listener
            updateButton.removeActionListener(updateActionListener);

        // create (new) one and assign it
        updateActionListener = new BorrowerUpdateActionListener(view, updateBorrowerViewHandler, sharedLogic);
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
