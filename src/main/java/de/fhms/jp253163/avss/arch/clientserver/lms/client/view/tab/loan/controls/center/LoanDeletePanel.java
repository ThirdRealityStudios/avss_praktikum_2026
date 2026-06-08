package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.center;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.IView;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.center.action.LoanDeleteActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanDeletePanel extends JPanel
{
    private IView view;
    private JButton deleteButton;
    private LoanDeleteActionListener deleteActionListener;

    public LoanDeletePanel(IView view, JTable loanTable)
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(loanTable);

        this.view = view;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        initDeleteButton(null, null, loanTable);

        UIFactory.addComponentFor(this, deleteButton, 0, 0, GridBagConstraints.PAGE_START, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }

    private void initDeleteButton(IReadLoanViewHandler readLoanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, JTable loanTable) throws NullPointerException
    {
        if(deleteButton == null)
            deleteButton = new JButton("Delete");

        if(deleteActionListener != null) // remove old action listener
            deleteButton.removeActionListener(deleteActionListener);

        // create (new) one and assign it
        deleteActionListener = new LoanDeleteActionListener(readLoanViewHandler, deleteLoanViewHandler, this, loanTable);
        deleteButton.addActionListener(deleteActionListener);
    }

    public void setDeleteLoanViewHandler(IReadLoanViewHandler readLoanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, JTable loanTable)
    {
        initDeleteButton(readLoanViewHandler, deleteLoanViewHandler, loanTable);
    }
}
