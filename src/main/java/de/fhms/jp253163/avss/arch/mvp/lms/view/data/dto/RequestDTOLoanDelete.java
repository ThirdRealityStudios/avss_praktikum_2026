package de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;

public class RequestDTOLoanDelete implements IRequestDTOLoanDelete
{
    private int borrowerID = -1;

    public RequestDTOLoanDelete(int borrowerID)
    {
        this.borrowerID = borrowerID;
    }

    @Override
    public int getLoanID()
    {
        return borrowerID;
    }
}
