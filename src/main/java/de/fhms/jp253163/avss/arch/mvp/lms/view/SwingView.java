package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.LoanTabPanel;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class SwingView extends JFrame implements IView
{
    // TODO These left view handlers are to implement
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;

    private ICreateLoanViewHandler createLoanViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;
    private IUpdateLoanViewHandler updateLoanViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;

    private BorrowerTabPanel borrowerTabPanel;
    private LoanTabPanel loanTabPanel;

    public SwingView(BorrowerTabPanel borrowerTabPanel, LoanTabPanel loanTabPanel)
    {
        Objects.requireNonNull(borrowerTabPanel);
        Objects.requireNonNull(loanTabPanel);

        setTitle("Loan Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.borrowerTabPanel = borrowerTabPanel;
        this.loanTabPanel = loanTabPanel;

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Borrowers", borrowerTabPanel);
        tabbedPane.addTab("Loans", loanTabPanel);

        add(tabbedPane);

        tabbedPane.setVisible(true);
    }

    @Override
    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler handler) throws NullPointerException
    {
        this.borrowerTabPanel.getControlsPanel().getCreateUpdatePanel().getButtonPanel().setCreateBorrowerViewHandler(handler);
    }

    @Override
    public void setReadBorrowerViewHandler(IReadBorrowerViewHandler handler)
    {
        this.readBorrowerViewHandler = handler;
    }

    @Override
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler)
    {
        this.borrowerTabPanel.getControlsPanel().getCreateUpdatePanel().getButtonPanel().setUpdateBorrowerViewHandler(handler);
    }

    @Override
    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler handler)
    {
        this.deleteBorrowerViewHandler = handler;
    }

    @Override
    public void setCreateLoanViewHandler(ICreateLoanViewHandler handler)
    {
        this.createLoanViewHandler = handler;
    }

    @Override
    public void setReadLoanViewHandler(IReadLoanViewHandler handler)
    {
        this.readLoanViewHandler = handler;
    }

    @Override
    public void setUpdateLoanViewHandler(IUpdateLoanViewHandler handler)
    {
        this.updateLoanViewHandler = handler;
    }

    @Override
    public void setDeleteLoanViewHandler(IDeleteLoanViewHandler handler)
    {
        this.deleteLoanViewHandler = handler;
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers) {

    }

    @Override
    public void setLoanData(List<IDTOLoanSet> loans) {

    }
}
