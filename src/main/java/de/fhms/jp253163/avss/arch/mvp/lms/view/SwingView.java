package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.BorrowerDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.LoanTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.LoanControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.LoanCreateUpdatePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;

public class SwingView extends JFrame implements IView
{
    // TODO These left view handlers are to implement
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;

    private ICreateLoanViewHandler createLoanViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;
    private IUpdateLoanViewHandler updateLoanViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;

    private JTable borrowerTable, loanTable;
    private ScrollableTablePanel borrowerTablePanel, loanTablePanel;

    private BorrowerControlsPanel borrowerControlsPanel;
    private LoanControlsPanel loanControlsPanel;

    private BorrowerCreateUpdatePanel borrowerCreateUpdatePanel;
    private LoanCreateUpdatePanel loanCreateUpdatePanel;

    private BorrowerDeletePanel borrowerDeletePanel;
    private LoanDeletePanel loanDeletePanel;

    private MessagePanel borrowerMessagePanel, loanMessagePanel;

    private BorrowerTabPanel borrowerTabPanel;
    private LoanTabPanel loanTabPanel;

    private JTabbedPane tabbedPane;

    public SwingView()
    {
        initBorrowerTab();
        initLoanTab();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Borrowers", borrowerTabPanel);
        tabbedPane.addTab("Loans", loanTabPanel);
        add(tabbedPane);
    }

    private void initBorrowerTab()
    {
        borrowerCreateUpdatePanel = new BorrowerCreateUpdatePanel(this);
        borrowerDeletePanel = new BorrowerDeletePanel(this);
        borrowerMessagePanel = new MessagePanel(this);

        borrowerControlsPanel = new BorrowerControlsPanel(borrowerCreateUpdatePanel, borrowerDeletePanel, borrowerMessagePanel);
        borrowerControlsPanel.setPreferredSize(new Dimension(800, borrowerControlsPanel.getPreferredSize().height));

        int controlsPanelHeight = borrowerControlsPanel.getPreferredSize().height;
        int remainingHeight = 600 - controlsPanelHeight;

        if(remainingHeight < 300)
            remainingHeight = 300;

        borrowerTable = new JTable();
        borrowerTablePanel = new ScrollableTablePanel(borrowerTable);
        borrowerTablePanel.setPreferredSize(new Dimension(800, remainingHeight));
        borrowerTabPanel = new BorrowerTabPanel(borrowerTablePanel, borrowerControlsPanel);
    }

    private void initLoanTab()
    {
        loanCreateUpdatePanel = new LoanCreateUpdatePanel(this);
        loanDeletePanel = new LoanDeletePanel(this);
        loanMessagePanel = new MessagePanel(this);

        loanControlsPanel = new LoanControlsPanel(loanCreateUpdatePanel, loanDeletePanel, loanMessagePanel);
        loanControlsPanel.setPreferredSize(new Dimension(800, loanControlsPanel.getPreferredSize().height));

        int controlsPanelHeight = loanControlsPanel.getPreferredSize().height;
        int remainingHeight = 600 - controlsPanelHeight;

        if(remainingHeight < 300)
            remainingHeight = 300;

        loanTable = new JTable();
        loanTablePanel = new ScrollableTablePanel(loanTable);
        loanTablePanel.setPreferredSize(new Dimension(800, remainingHeight));
        loanTabPanel = new LoanTabPanel(loanTablePanel, loanControlsPanel);
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
        borrowerMessagePanel.getMessageLabel().setText(message);
    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers) {
        if(borrowers == null)
            return;

        int columns = IDTOBorrowerSet.class.getDeclaredMethods().length;

        Method[] getterMethods = IDTOBorrowerSet.class.getDeclaredMethods();
        String[] columnNames = new String[getterMethods.length];

        for(int i = 0; i < getterMethods.length; i++)
        {
            Method getterMethod = getterMethods[i];

            if(getterMethod == null)
            {
                columnNames[i] = "[Can't load]";

                continue;
            }

            String methodName = getterMethod.getName();

            String propertyName = methodName.replace("get", "");

            columnNames[i] = propertyName;
        }

        String[][] data = new String[columns][borrowers.size()];

        for(int i = 0; i < borrowers.size(); i++)
        {
            IDTOBorrowerSet borrowerSet = borrowers.get(i);

            String borrowerID = "" + borrowerSet.getID();

            String firstname = borrowerSet.getFirstname();
            String lastname = borrowerSet.getLastname();

            String day = "" + borrowerSet.getDayOfBirth();
            String month = "" + borrowerSet.getMonthOfBirth();
            String year = "" + borrowerSet.getYearOfBirth();

            String street = borrowerSet.getStreet();
            String no = borrowerSet.getNo();
            String place = borrowerSet.getPlace();
            String zip = "" + borrowerSet.getZip();

            data[i][0] = borrowerID;

            data[i][1] = firstname;
            data[i][2] = lastname;

            data[i][3] = day;
            data[i][4] = month;
            data[i][5] = year;

            data[i][6] = street;
            data[i][7] = no;
            data[i][8] = place;
            data[i][9] = zip;
        }

        if(borrowerTable.getModel() == null)
        {
            TableModel model = new DefaultTableModel(data, columnNames);

            try
            {
                borrowerTable.setModel(model);
            }
            catch (IllegalArgumentException iae)
            {
                return;
            }
        }
    }

    @Override
    public void setLoanData(List<IDTOLoanSet> loans)
    {
        if(loans == null)
            return;

        int columns = IDTOLoanSet.class.getDeclaredMethods().length;

        Method[] getterMethods = IDTOLoanSet.class.getDeclaredMethods();
        String[] columnNames = new String[getterMethods.length];

        for(int i = 0; i < getterMethods.length; i++)
        {
            Method getterMethod = getterMethods[i];

            if(getterMethod == null)
            {
                columnNames[i] = "[Can't load]";

                continue;
            }

            String methodName = getterMethod.getName();

            String propertyName = methodName.replace("get", "");

            columnNames[i] = propertyName;
        }

        String[][] data = new String[columns][loans.size()];

        for(int i = 0; i < loans.size(); i++)
        {
            IDTOLoanSet loanSet = loans.get(i);

            String loanID = "" + loanSet.getID();

            String remainingTerm = "" + loanSet.getRemainingTerm();
            String remainingBalance = "" + loanSet.getRemainingBalance();

            String amount = "" + loanSet.getAmount();
            String interestRate = "" + loanSet.getInterestRate();
            String term = "" + loanSet.getTerm();
            String borrowerID = "" + loanSet.getBorrower();

            data[i][0] = loanID;

            data[i][1] = remainingTerm;
            data[i][2] = remainingBalance;

            data[i][3] = amount;
            data[i][4] = interestRate;
            data[i][5] = term;
            data[i][6] = borrowerID;
        }

        if(loanTable.getModel() == null)
        {
            TableModel model = new DefaultTableModel(data, columnNames);

            try
            {
                loanTable.setModel(model);
            }
            catch (IllegalArgumentException iae)
            {
                return;
            }
        }
    }
}
