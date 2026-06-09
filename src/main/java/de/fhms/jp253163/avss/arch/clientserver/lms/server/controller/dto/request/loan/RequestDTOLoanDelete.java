package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanDelete;

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
