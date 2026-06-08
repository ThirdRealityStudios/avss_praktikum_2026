package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.center.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.center.LoanDeletePanel;

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
    private JTable tableLoans;

    /**
     * @param readLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deleteLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @param tableLoans
     * @throws NullPointerException
     */
    public LoanDeleteActionListener(IReadLoanViewHandler readLoanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, LoanDeletePanel deletePanel, JTable tableLoans) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);
        Objects.requireNonNull(tableLoans);

        this.readLoanViewHandler = readLoanViewHandler;
        this.deleteLoanViewHandler = deleteLoanViewHandler;
        this.deletePanel = deletePanel;
        this.tableLoans = tableLoans;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        TableModel model = null;
        int[] selectedRowsIDs = new int[0];
        int selectedRowID = -1;

        Object loanIDObj = null;
        String loanIDText = null;
        int loanIDParsed = -1;

        if(tableLoans != null) {
            model = tableLoans.getModel();
            selectedRowsIDs = tableLoans.getSelectedRows();
        }

        if(selectedRowsIDs.length > 0) selectedRowID = selectedRowsIDs[0];

        if(model != null && selectedRowID > -1) loanIDObj = model.getValueAt(selectedRowID, 0);
        if(loanIDObj != null) loanIDText = loanIDObj.toString();
        if(loanIDText != null) try{loanIDParsed = Integer.parseInt(loanIDText);} catch(NumberFormatException exc){}

        IRequestDTOLoanDelete requestDTOLoanDelete = new RequestDTOLoanDelete(loanIDParsed);

        if(readLoanViewHandler != null && deleteLoanViewHandler != null)
        {
            deleteLoanViewHandler.invoke(requestDTOLoanDelete);
        }
    }
}
