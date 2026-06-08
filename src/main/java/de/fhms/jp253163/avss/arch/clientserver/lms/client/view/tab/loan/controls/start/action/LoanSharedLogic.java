package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Objects;

public class LoanSharedLogic
{
    public static RequestDTOLoanCreate genCreateLoanDTO(LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        String borrowerID = textfieldPanel.getUpdateIDPanel().getBorrowerID().getJTextField().getText(),
                amount = textfieldPanel.getConditionsPanel().getAmount().getJTextField().getText(),
                interestRate = textfieldPanel.getConditionsPanel().getInterestRate().getJTextField().getText(),
                term = textfieldPanel.getConditionsPanel().getTerm().getJTextField().getText();

        int borrowerIDParsed = -1;
        try
        {
            borrowerIDParsed = Integer.parseInt(borrowerID);
        }
        catch (NumberFormatException nfe)
        {
            borrowerIDParsed = -1;
        }

        double amountParsed = -1;
        try
        {
            amountParsed = Double.parseDouble(amount);
        }
        catch (NumberFormatException nfe)
        {
            amountParsed = -1;
        }

        double interestRateParsed = -1;
        try
        {
            interestRateParsed = Double.parseDouble(interestRate);
        }
        catch (NumberFormatException nfe)
        {
            interestRateParsed = -1;
        }

        int termParsed = -1;
        try
        {
            termParsed = Integer.parseInt(term);
        }
        catch (NumberFormatException nfe)
        {
            termParsed = -1;
        }

        return new RequestDTOLoanCreate(borrowerIDParsed, amountParsed, interestRateParsed, termParsed);
    }

    private static int getSelectedRowID(JTable table)
    {
        Objects.requireNonNull(table);

        int[] selectedRowsIDs = table.getSelectedRows();

        int selectedRowID = -1;

        if(selectedRowsIDs != null && selectedRowsIDs.length > 0)
        {
            selectedRowID = selectedRowsIDs[0]; // even though multiple maybe selected, only take the first one chosen.
        }

        return selectedRowID;
    }

    public static RequestDTOLoanUpdate genUpdateLoanDTO(LoanTextfieldPanel textfieldPanel, boolean bookInstallment, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(tableLoan, "table is null");

        RequestDTOLoanCreate helpingDTO = genCreateLoanDTO(textfieldPanel);

        TableModel model = tableLoan.getModel();

        if(model == null) return new RequestDTOLoanUpdate(-1, helpingDTO.getBorrower(), bookInstallment);

        int selectedRowID = -1;
        Object loanIDObj = null;
        String loanIDText = null;
        int loanIDParsed = -1;
        int borrowerID = -1;

        selectedRowID = getSelectedRowID(tableLoan);

        if(selectedRowID < 0) {
            return new RequestDTOLoanUpdate(-1, helpingDTO.getBorrower(), bookInstallment);
        }

        loanIDObj = model.getValueAt(selectedRowID, 0);
        loanIDText = loanIDObj.toString();
        try { loanIDParsed = Integer.parseInt(loanIDText); } catch(NumberFormatException exc) {  }

        borrowerID = helpingDTO.getBorrower();

        return new RequestDTOLoanUpdate(loanIDParsed, borrowerID, bookInstallment);
    }
}