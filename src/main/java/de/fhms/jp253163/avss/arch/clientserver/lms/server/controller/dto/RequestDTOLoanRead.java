package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanRead;

public class RequestDTOLoanRead implements IRequestDTOLoanRead
{
    private int loanID;

    /**
     * @param loanID Give ID of a loan here. Or -1 if all wanted.
     */
    public RequestDTOLoanRead(int loanID)
    {
        this.loanID = loanID;
    }

    @Override
    public int getLoanID()
    {
        return loanID;
    }
}