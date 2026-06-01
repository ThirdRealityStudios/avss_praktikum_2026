package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.action.BorrowerDeleteActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerDeletePanel extends JPanel
{
    private IView view;
    private JButton deleteButton;
    private BorrowerDeleteActionListener deleteActionListener;

    public BorrowerDeletePanel(IView view, TabsPanel tabsPanel)
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tabsPanel);

        this.view = view;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        initDeleteButton(null, null, tabsPanel);

        UIFactory.addComponentFor(this, deleteButton, 0, 0, GridBagConstraints.PAGE_START, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }

    private void initDeleteButton(IReadBorrowerViewHandler readBorrowerViewHandler, IDeleteBorrowerViewHandler deleteBorrowerViewHandler, TabsPanel tabsPanel) throws NullPointerException
    {
        if(deleteButton == null)
            deleteButton = new JButton("Delete");

        if(deleteActionListener != null) // remove old action listener
            deleteButton.removeActionListener(deleteActionListener);

        // create (new) one and assign it
        deleteActionListener = new BorrowerDeleteActionListener(readBorrowerViewHandler, deleteBorrowerViewHandler, this, tabsPanel);
        deleteButton.addActionListener(deleteActionListener);
    }

    public void setDeleteBorrowerViewHandler(IReadBorrowerViewHandler readBorrowerViewHandler, IDeleteBorrowerViewHandler deleteBorrowerViewHandler, TabsPanel tabsPanel)
    {
        initDeleteButton(readBorrowerViewHandler, deleteBorrowerViewHandler, tabsPanel);
    }
}
