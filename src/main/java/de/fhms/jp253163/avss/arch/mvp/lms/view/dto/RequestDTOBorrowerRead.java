package de.fhms.jp253163.avss.arch.mvp.lms.view.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerRead;

public class RequestDTOBorrowerRead implements IRequestDTOBorrowerRead
{
    private int borrowerID;

    /**
     * @param borrowerID Give ID of borrower here (at least zero). Or give -1 (to read all borrowers).
     */
    public RequestDTOBorrowerRead(int borrowerID)
    {
        this.borrowerID = borrowerID;
    }

    @Override
    public int getBorrowerID()
    {
        return borrowerID;
    }
}
