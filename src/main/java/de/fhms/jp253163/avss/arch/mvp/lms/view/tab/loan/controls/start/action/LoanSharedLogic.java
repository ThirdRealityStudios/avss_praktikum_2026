package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

public class LoanSharedLogic
{
    public static RequestDTOLoanCreate genCreateLoanDTO(LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        String borrowerID = textfieldPanel.getBorrowerID().getJTextField().getText(),
                amount = textfieldPanel.getAmout().getJTextField().getText(),
                interestRate = textfieldPanel.getInterestRate().getJTextField().getText(),
                term = textfieldPanel.getTerm().getJTextField().getText();

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

    public static RequestDTOLoanUpdate genUpdateLoanDTO(LoanTextfieldPanel textfieldPanel, boolean bookInstallment, TabsPanel tabsPanel) throws NullPointerException
    {
        String loanID = "-1", // TODO Get loan id from selected data table row / loan dataset
                borrowerID = textfieldPanel.getBorrowerID().getJTextField().getText();;

        int loanIDParsed = -1;
        try
        {
            loanIDParsed = Integer.parseInt(loanID);
        }
        catch (NumberFormatException nfe)
        {
            loanIDParsed = -1;
        }

        int borrowerIDParsed = -1;
        try
        {
            borrowerIDParsed = Integer.parseInt(borrowerID);
        }
        catch (NumberFormatException nfe)
        {
            borrowerIDParsed = -1;
        }

        return new RequestDTOLoanUpdate(loanIDParsed, borrowerIDParsed, bookInstallment);
    }
}
