package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanUpdate;

public class RequestDTOLoanUpdate implements IRequestDTOLoanUpdate
{
    private int loanID = -1, borrowerID = -1;
    private boolean bookInstallment = false;

    public RequestDTOLoanUpdate(int loanID, int borrowerID, boolean bookInstallment)
    {
        this.loanID = loanID;
        this.borrowerID = borrowerID;
        this.bookInstallment = bookInstallment;
    }

    @Override
    public int getLoanID()
    {
        return loanID;
    }

    @Override
    public int getBorrowerID()
    {
        return borrowerID;
    }

    @Override
    public boolean isBookInstallmentWanted() {
        return bookInstallment;
    }
}