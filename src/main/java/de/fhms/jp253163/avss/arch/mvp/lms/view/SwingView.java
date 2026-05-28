package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.MessagePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoan;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;

public class SwingView extends JFrame implements IView
{
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;

    private MessagePanel messagePanel;

    private TabsPanel tabsPanel;

    public SwingView()
    {
        messagePanel = new MessagePanel(this);
        tabsPanel = new TabsPanel(this);

        SwingPanel panel = new SwingPanel(messagePanel, tabsPanel);

        add(panel);
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
    }

    @Override
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler)
    {
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setUpdateBorrowerViewHandler(readBorrowerViewHandler, handler);
    }

    @Override
    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler handler)
    {
        this.tabsPanel.getBorrowerTabPanel().getControlsPanel().getDeletePanel().setDeleteBorrowerViewHandler(readBorrowerViewHandler, handler);
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
    }

    @Override
    public void setUpdateLoanViewHandler(IUpdateLoanViewHandler updateLoanViewHandler)
    {
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getCreateUpdatePanel().getButtonPanel().setUpdateLoanViewHandler(readLoanViewHandler, updateLoanViewHandler);
    }

    @Override
    public void setDeleteLoanViewHandler(IDeleteLoanViewHandler handler)
    {
        this.tabsPanel.getLoanTabPanel().getControlsPanel().getDeletePanel().setDeleteLoanViewHandler(readLoanViewHandler, handler);
    }

    @Override
    public void start() {
        setTitle("Loan Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);

        // Notify view handler to load all datasets on every program start (into the tables)
        if(readBorrowerViewHandler != null)
            readBorrowerViewHandler.invoke(new RequestDTOBorrowerRead(-1));

        if(readLoanViewHandler != null)
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
    }

    @Override
    public void showMessage(String message) {
        messagePanel.getMessageLabel().setText(message);
    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers) {
        if(borrowers == null)
            return;

        int columnCountDTOBorrowerSet = IDTOBorrowerSet.class.getDeclaredMethods().length,
            columnCountDTOBorrower = IDTOBorrower.class.getDeclaredMethods().length,
            totalColumnCount = columnCountDTOBorrower + columnCountDTOBorrowerSet;

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

        String[][] data = new String[borrowers.size()][totalColumnCount];

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

        int columnCountDTOLoanSet = IDTOLoanSet.class.getDeclaredMethods().length,
                columnCountDTOLoan = IDTOLoan.class.getDeclaredMethods().length,
                totalColumnCount = columnCountDTOLoan + columnCountDTOLoanSet;

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

        String[][] data = new String[loans.size()][totalColumnCount];

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

        if(tabsPanel == null || tabsPanel.getLoanTabPanel() == null || tabsPanel.getLoanTabPanel().getDataTabPanel() == null)
            return;

        JTable loanTable = tabsPanel.getLoanTabPanel().getDataTabPanel().getTable();

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