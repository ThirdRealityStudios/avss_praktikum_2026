package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

public class LoanSharedLogic
{
    public static RequestDTOLoanCreate genCreateLoanDTO(LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        String borrowerID = textfieldPanel.getBorrowerID().getJTextField().getText(),
                amount = textfieldPanel.getAmout().getJTextField().getText(),
                interestRate = textfieldPanel.getInterestRate().getJTextField().getText(),
                term = textfieldPanel.getTerm().getJTextField().getText();

        if(borrowerID == null) borrowerID = "";
        if(amount == null) amount = "";
        if(interestRate == null) interestRate = "";
        if(term == null) term = "";

        if(borrowerID.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in borrower ID!");
            return null;
        }

        if(amount.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in amount!");
            return null;
        }

        if(interestRate.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in interest rate!");
            return null;
        }

        if(term.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in term!");
            return null;
        }

        int borrowerIDParsed = -1;
        try
        {
            borrowerIDParsed = Integer.parseInt(borrowerID);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Borrower ID is not a number!");

            return null;
        }

        double amountParsed = -1;
        try
        {
            amountParsed = Double.parseDouble(amount);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Amount is not a number (e.g. 1325.69)!");

            return null;
        }

        double interestRateParsed = -1;
        try
        {
            interestRateParsed = Double.parseDouble(interestRate);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Interest rate is not a number (e.g. 0.01 for 1 percent)!");

            return null;
        }

        int termParsed = -1;
        try
        {
            termParsed = Integer.parseInt(term);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Term is not a number (e.g. 7 for 7 months)!");

            return null;
        }

        return new RequestDTOLoanCreate(borrowerIDParsed, amountParsed, interestRateParsed, termParsed);
    }

    public static RequestDTOLoanUpdate genUpdateLoanDTO(LoanTextfieldPanel textfieldPanel, boolean bookInstallment) throws NullPointerException
    {
        String loanID = "-1", // TODO Get loan id from selected data table row / loan dataset
                borrowerID = textfieldPanel.getBorrowerID().getJTextField().getText();;

        if(loanID == null) loanID = "";
        if(borrowerID == null) borrowerID = "";

        if(loanID.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in loan ID!");
            return null;
        }

        if(borrowerID.isEmpty())
        {
            textfieldPanel.getView().showMessage("Please fill in borrower ID!");
            return null;
        }

        int loanIDParsed = -1;
        try
        {
            loanIDParsed = Integer.parseInt(loanID);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Loan ID is not a number!");

            return null;
        }

        int borrowerIDParsed = -1;
        try
        {
            borrowerIDParsed = Integer.parseInt(borrowerID);
        }
        catch (NumberFormatException nfe)
        {
            textfieldPanel.getView().showMessage("Borrower ID is not a number!");

            return null;
        }

        return new RequestDTOLoanUpdate(loanIDParsed, borrowerIDParsed, bookInstallment);
    }
}
