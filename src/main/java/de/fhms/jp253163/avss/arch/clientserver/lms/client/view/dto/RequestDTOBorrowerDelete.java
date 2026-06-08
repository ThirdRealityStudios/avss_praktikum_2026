package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerDelete;

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
