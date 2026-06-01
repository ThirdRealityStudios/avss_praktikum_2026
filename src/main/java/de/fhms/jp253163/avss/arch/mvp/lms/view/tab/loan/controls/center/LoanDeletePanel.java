package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.action.LoanDeleteActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoanDeletePanel extends JPanel
{
    private IView view;
    private JButton deleteButton;
    private LoanDeleteActionListener deleteActionListener;
    private TabsPanel tabsPanel;

    public LoanDeletePanel(IView view, TabsPanel tabsPanel)
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tabsPanel);

        this.view = view;
        this.tabsPanel = tabsPanel;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        initDeleteButton(null, null, tabsPanel);

        UIFactory.addComponentFor(this, deleteButton, 0, 0, GridBagConstraints.PAGE_START, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }

    private void initDeleteButton(IReadLoanViewHandler readBorrowerViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, TabsPanel tabsPanel) throws NullPointerException
    {
        if(deleteButton == null)
            deleteButton = new JButton("Delete");

        if(deleteActionListener != null) // remove old action listener
            deleteButton.removeActionListener(deleteActionListener);

        // create (new) one and assign it
        deleteActionListener = new LoanDeleteActionListener(readBorrowerViewHandler, deleteLoanViewHandler, tabsPanel);
        deleteButton.addActionListener(deleteActionListener);
    }

    public void setDeleteLoanViewHandler(IReadLoanViewHandler loanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, TabsPanel tabsPanel)
    {
        initDeleteButton(loanViewHandler, deleteLoanViewHandler, tabsPanel);
    }
}
