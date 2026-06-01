package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.LoanTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanDeleteActionListener implements ActionListener
{
    private IReadLoanViewHandler readLoanViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;
    private LoanDeletePanel deletePanel;
    private TabsPanel tabsPanel;

    /**
     * @param readLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deleteLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @throws NullPointerException
     */
    public LoanDeleteActionListener(IReadLoanViewHandler readLoanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(tabsPanel);

        this.readLoanViewHandler = readLoanViewHandler;
        this.deleteLoanViewHandler = deleteLoanViewHandler;
        this.tabsPanel = tabsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing LOAN DELETION button CLICKED!");

        LoanTabPanel tabPanel = null;
        ScrollableTablePanel tablePanel = null;
        JTable table = null;
        int[] selectedRowsIDs = null;
        Object selectedRowIDObj = null;
        String selectedRowIDText = null;
        int selectedRowIDParsed = -1;

        TableModel model = null;
        Object loanIDObj = null;
        String loanIDText = null;
        int loanIDParsed = -1;

        System.out.println("");

        tabPanel = tabsPanel.getLoanTabPanel();
        if(tabPanel != null) {
            tablePanel = tabPanel.getDataTabPanel();
            System.out.println("tabPanel = null");
        }
        if(tablePanel != null) {
            table = tablePanel.getTable();
            System.out.println("tablePanel = null");
        }
        if(table != null) {
            selectedRowsIDs = table.getSelectedRows();
            System.out.println("table = null");
        }
        if(selectedRowsIDs != null && selectedRowsIDs.length > 0) {
            selectedRowIDObj = selectedRowsIDs[0];
            System.out.println("selectedRowsIDs = null OR empty");
        }
        if(selectedRowIDObj != null) {
            selectedRowIDText = selectedRowIDObj.toString();
            System.out.println("selectedRowIDObj = null");
        }
        if(selectedRowIDText != null) try{
            selectedRowIDParsed = Integer.parseInt(selectedRowIDText);
            System.out.println("selectedRowIDText = null");
        } catch(NumberFormatException exc){}

        if(table != null) {
            model = table.getModel();
            System.out.println("table = null");
        }
        if(model != null && selectedRowIDParsed >= 0) {
            loanIDObj = model.getValueAt(selectedRowIDParsed, 0);
            System.out.println("model = null OR selectedRowIDParsed negative");
        }
        if(loanIDObj != null) {
            loanIDText = loanIDObj.toString();
            System.out.println("loanIDObj = null");
        }
        if(loanIDText != null) try{
            loanIDParsed = Integer.parseInt(loanIDText);
            System.out.println("loanIDText = null");
        } catch(NumberFormatException exc){  }

        System.out.println("loanIDText = " + loanIDParsed);

        IRequestDTOLoanDelete requestDTOLoanDelete = new RequestDTOLoanDelete(loanIDParsed);

        System.out.println("Created valid request for LOAN DELETION button CLICKED!");

        if(readLoanViewHandler != null && deleteLoanViewHandler != null)
        {
            System.out.println("Invoking view handler for LOAN DELETION!");
            deleteLoanViewHandler.invoke(requestDTOLoanDelete);

            System.out.println("Invoking view handler for LOAN READ!");
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        }
    }
}