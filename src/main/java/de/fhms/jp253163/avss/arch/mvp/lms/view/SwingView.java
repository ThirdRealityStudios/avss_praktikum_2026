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
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
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

        this.borrowerTabPanel = borrowerTabPanel;
        this.loanTabPanel = loanTabPanel;

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Borrowers", borrowerTabPanel);
        tabbedPane.addTab("Loans", loanTabPanel);
    }

    @Override
    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler handler)
    {
        this.createBorrowerViewHandler = handler;
    }

    @Override
    public void setReadBorrowerViewHandler(IReadBorrowerViewHandler handler)
    {
        this.readBorrowerViewHandler = handler;
    }

    @Override
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler)
    {
        this.updateBorrowerViewHandler = handler;
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
