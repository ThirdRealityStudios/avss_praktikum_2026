package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerRead;

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
