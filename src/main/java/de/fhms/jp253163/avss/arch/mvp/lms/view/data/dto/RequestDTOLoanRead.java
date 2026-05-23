package de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanRead;

public class RequestDTOLoanRead implements IRequestDTOLoanRead
{
    private int loanID;

    /**
     * @param loanID Give ID of all loans here.
     */
    public RequestDTOLoanRead(int loanID)
    {
        this.loanID = loanID;
    }

    @Override
    public int getLoanIDs() {
        return loanID;
    }
}
