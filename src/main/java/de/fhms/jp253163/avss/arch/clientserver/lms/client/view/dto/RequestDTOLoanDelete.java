package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanDelete;

public class RequestDTOLoanDelete implements IRequestDTOLoanDelete
{
    private int loanID = -1;

    public RequestDTOLoanDelete(int loanID)
    {
        this.loanID = loanID;
    }

    @Override
    public int getLoanID()
    {
        return loanID;
    }
}
