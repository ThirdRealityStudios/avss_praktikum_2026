package de.fhms.jp253163.avss.arch.mvp.lms.presenter.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOLoanUpdate;

public class RequestDTOLoanUpdate implements IRequestDTOLoanUpdate
{
    private int loanID = -1, borrowerID = -1;

    public RequestDTOLoanUpdate(int loanID, int borrowerID)
    {
        this.loanID = loanID;
        this.borrowerID = borrowerID;
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
}