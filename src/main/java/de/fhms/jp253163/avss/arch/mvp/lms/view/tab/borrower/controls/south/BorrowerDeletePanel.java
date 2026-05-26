package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.action.BorrowerDeleteActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerDeletePanel extends JPanel
{
    private IView view;

    public BorrowerDeletePanel(IDeleteBorrowerViewHandler deleteBorrowerViewHandler, IView view)
    {
        Objects.requireNonNull(deleteBorrowerViewHandler);
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        JButton deleteButton = new JButton("Delete");
        BorrowerDeleteActionListener deleteActionListener = new BorrowerDeleteActionListener(deleteBorrowerViewHandler, this);
        deleteButton.addActionListener(deleteActionListener);

        UIFactory.addComponentFor(this, deleteButton, 0, 0, GridBagConstraints.PAGE_START, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }
}
