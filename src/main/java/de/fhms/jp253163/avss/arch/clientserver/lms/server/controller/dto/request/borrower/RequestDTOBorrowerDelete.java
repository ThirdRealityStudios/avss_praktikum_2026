package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerDelete;

public class RequestDTOBorrowerDelete implements IRequestDTOBorrowerDelete
{
    private int id;

    public RequestDTOBorrowerDelete(int borrowerID)
    {
        this.id = borrowerID;
    }

    @Override
    public int getBorrowerID() {
        return id;
    }
}
