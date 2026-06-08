package de.fhms.jp253163.avss.arch.clientserver.lms.client.view;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.TabsPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class SwingView extends JFrame implements IView
{
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;

    private MessagePanel messagePanel;

    private TabsPanel tabsPanel;

    public SwingView()
    {
        setTitle("Loan Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        messagePanel = new MessagePanel(this);
        tabsPanel = new TabsPanel(this);

        SwingPanel panel = new SwingPanel(messagePanel, tabsPanel);

        JScrollPane pane = new JScrollPane(panel);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(pane, BorderLayout.CENTER);

        pack();
    }

    @Override
    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler handler) throws NullPointerException
    {
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setCreateBorrowerViewHandler(handler, readBorrowerViewHandler);
    }

    @Override
    public void setReadBorrowerViewHandler(IReadBorrowerViewHandler handler)
    {
        this.readBorrowerViewHandler = handler;
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setRefreshBorrowerViewHandler(handler);
    }

    @Override
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler)
    {
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setUpdateBorrowerViewHandler(readBorrowerViewHandler, handler, this.tabsPanel.getBorrowerTabPanel().getDataTabPanel());
    }

    @Override
    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler handler)
    {
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getDeletePanel().setDeleteBorrowerViewHandler(readBorrowerViewHandler, handler, tabsPanel);
    }

    @Override
    public void setCreateLoanViewHandler(ICreateLoanViewHandler createLoanViewHandler)
    {
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setCreateLoanViewHandler(createLoanViewHandler, readLoanViewHandler);
    }

    @Override
    public void setReadLoanViewHandler(IReadLoanViewHandler handler)
    {
        this.readLoanViewHandler = handler;
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setRefreshLoanViewHandler(handler);
    }

    @Override
    public void setUpdateLoanViewHandler(IUpdateLoanViewHandler handler)
    {
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setUpdateLoanViewHandler(readLoanViewHandler, handler, this.tabsPanel.getLoanTabPanel().getDataTabPanel().getTable());
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setBookInstallmentLoanViewHandler(readLoanViewHandler, handler, this.tabsPanel.getLoanTabPanel().getDataTabPanel().getTable());
    }

    @Override
    public void setDeleteLoanViewHandler(IDeleteLoanViewHandler handler)
    {
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getDeletePanel().setDeleteLoanViewHandler(readLoanViewHandler, handler, this.tabsPanel.getLoanTabPanel().getDataTabPanel().getTable());
    }

    @Override
    public void start() {
        // Notify view handler to load all datasets on every program start (into the tables)
        if(readBorrowerViewHandler != null)
            readBorrowerViewHandler.invoke(new RequestDTOBorrowerRead(-1));
        else
            showMessage("Cannot invoke borrower read request!");

        if(readLoanViewHandler != null)
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        else
            showMessage("Cannot invoke loan read request!");

        setVisible(true);
        /*
        tabsPanel.getLoanTabPanel().getDataTabPanel().getTable().setVisible(true);
        tabsPanel.getBorrowerTabPanel().getDataTabPanel().getTable().setVisible(true);
         */

        // pack();
    }

    @Override
    public void showMessage(String message) {
        messagePanel.getMessageLabel().setForeground(Color.RED);
        messagePanel.getMessageLabel().setText(message);
    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers) {
        if(borrowers == null)
            return;

        String[] columns = new String[]{ "ID", "Firstname", "Lastname", "Day", "Month", "Year", "Street", "No", "Place", "ZIP" };
        String[][] data = new String[borrowers.size()][columns.length];

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

        if(tabsPanel == null || tabsPanel.getBorrowerTabPanel() == null || tabsPanel.getBorrowerTabPanel().getDataTabPanel() == null)
            return;

        JTable borrowerTable = tabsPanel.getBorrowerTabPanel().getDataTabPanel().getTable();

        if(borrowerTable == null)
            return;

        TableModel model = new DefaultTableModel(data, columns);

        try
        {
            borrowerTable.setModel(model);
        }
        catch (IllegalArgumentException iae)
        {
            return;
        }

        System.out.println("Updated BORROWER table and sizes of JFrame");
    }

    @Override
    public void setLoanData(List<IDTOLoanSet> loans)
    {
        if(loans == null)
            return;

        String[] columnNames = new String[]{ "ID", "Amount", "Interest Rate", "Term", "Borrower Assigned", "Remaining Term", "Remaining Balance" };

        String[][] data = new String[loans.size()][columnNames.length];

        for(int i = 0; i < loans.size(); i++)
        {
            IDTOLoanSet loanSet = loans.get(i);

            String loanID = "" + loanSet.getID();

            String amount = "" + loanSet.getAmount();
            String interestRate = "" + loanSet.getInterestRate();
            String term = "" + loanSet.getTerm();

            String borrowerID = "" + loanSet.getBorrower();

            String remainingTerm = "" + loanSet.getRemainingTerm();
            String remainingBalance = "" + loanSet.getRemainingBalance();

            data[i][0] = loanID;

            data[i][1] = amount;
            data[i][2] = interestRate;
            data[i][3] = term;

            data[i][4] = borrowerID;

            data[i][5] = remainingTerm;
            data[i][6] = remainingBalance;
        }

        if(tabsPanel == null || tabsPanel.getLoanTabPanel() == null || tabsPanel.getLoanTabPanel().getDataTabPanel() == null)
            return;

        JTable loanTable = tabsPanel.getLoanTabPanel().getDataTabPanel().getTable();

        if(loanTable == null)
            return;

        TableModel model = new DefaultTableModel(data, columnNames);

        try
        {
            loanTable.setModel(model);
        }
        catch (IllegalArgumentException iae)
        {
            return;
        }

        System.out.println("Updated LOAN table and sizes of JFrame");
    }
}