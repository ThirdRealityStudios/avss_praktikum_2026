package de.fhms.jp253163.avss.arch.mvp.lms.presenter.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IDTOLoanSet;

public class DTOLoanSet implements IDTOLoanSet
{
    private int loanID, term, borrowerID, remainingTerm;
    private double amount, interestRate, remainingBalance;

    public DTOLoanSet(int loanID, double amount, double remainingBalance, double interestRate, int term, int remainingTerm, int borrowerID)
    {
        this.loanID = loanID;
        this.amount = amount;
        this.remainingBalance = remainingBalance;
        this.interestRate = interestRate;
        this.term = term;
        this.remainingTerm = remainingTerm;
        this.borrowerID = borrowerID;
    }

    @Override
    public int getID() {
        return loanID;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getRemainingBalance() {
        return remainingBalance;
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public int getRemainingTerm() {
        return remainingTerm;
    }

    @Override
    public int getBorrower() {
        return borrowerID;
    }

    @Override
    public String toString(){
        return "ID: " + getID() + "\n" +
                "Amount: " + getAmount() + "\n" +
                "Remaining balance: " + getRemainingBalance() + "\n" +
                "Interest rate: " + getInterestRate() + "\n" +
                "Term: " + getTerm() + "\n" +
                "Remaining term: " + getRemainingTerm() + "\n" +
                "Borrower ID attached: " + getBorrower();
    }
}
