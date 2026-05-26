package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerSharedLogic;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.action.BorrowerDeleteActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerDeletePanel extends JPanel
{
    private IView view;
    private JButton deleteButton;
    private BorrowerDeleteActionListener deleteActionListener;

    public BorrowerDeletePanel(IView view)
    {
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        initDeleteButton(null);

        UIFactory.addComponentFor(this, deleteButton, 0, 0, GridBagConstraints.PAGE_START, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }

    private void initDeleteButton(IDeleteBorrowerViewHandler deleteBorrowerViewHandler) throws NullPointerException
    {
        if(deleteButton == null)
            deleteButton = new JButton("Delete");

        if(deleteActionListener != null) // remove old action listener
            deleteButton.removeActionListener(deleteActionListener);

        // create (new) one and assign it
        deleteActionListener = new BorrowerDeleteActionListener(deleteBorrowerViewHandler, this);
        deleteButton.addActionListener(deleteActionListener);
    }

    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler deleteBorrowerViewHandler)
    {
        initDeleteButton(deleteBorrowerViewHandler);
    }
}
