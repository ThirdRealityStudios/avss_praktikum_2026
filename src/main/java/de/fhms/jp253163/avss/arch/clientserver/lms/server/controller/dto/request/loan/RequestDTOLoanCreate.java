package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanCreate;

public class RequestDTOLoanCreate implements IRequestDTOLoanCreate
{
    private int borrowerID, term;
    private double amount, interestRate;

    public RequestDTOLoanCreate(int borrowerID, double amount, double interestRate, int term)
    {
        this.borrowerID = borrowerID;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
    }

    @Override
    public double getAmount() {
        return amount;
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
    public int getBorrower() {
        return borrowerID;
    }
}
