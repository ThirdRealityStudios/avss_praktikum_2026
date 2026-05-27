package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.BorrowerDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.data.BorrowerDataTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.LoanTabPanel;

import javax.swing.*;
import java.awt.*;
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

    private JTable table;
    private BorrowerDataTabPanel dataTabPanel;
    private BorrowerControlsPanel controlsPanel;

    private BorrowerCreateUpdatePanel createUpdatePanel;
    private BorrowerDeletePanel deletePanel;

    private BorrowerTabPanel borrowerTabPanel;

    private LoanTabPanel loanTabPanel;

    private JTabbedPane tabbedPane;

    public SwingView()
    {
        table = new JTable();
        dataTabPanel = new BorrowerDataTabPanel(table);

        createUpdatePanel = new BorrowerCreateUpdatePanel(this);
        deletePanel = new BorrowerDeletePanel(this);

        controlsPanel = new BorrowerControlsPanel(createUpdatePanel, deletePanel);
        controlsPanel.setPreferredSize(new Dimension(800, controlsPanel.getPreferredSize().height));

        borrowerTabPanel = new BorrowerTabPanel(dataTabPanel, controlsPanel);
        loanTabPanel = new LoanTabPanel();

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Borrowers", borrowerTabPanel);
        tabbedPane.addTab("Loans", loanTabPanel);

        add(tabbedPane);
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
        setTitle("Loan Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        tabbedPane.setVisible(true);
        setVisible(true);
    }

    @Override
    public void showMessage(String message) {
        setTitle(message);
    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers) {

    }

    @Override
    public void setLoanData(List<IDTOLoanSet> loans) {

    }
}
