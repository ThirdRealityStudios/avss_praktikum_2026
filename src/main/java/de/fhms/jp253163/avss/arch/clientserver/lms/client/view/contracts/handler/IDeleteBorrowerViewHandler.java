package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerDelete;

public interface IDeleteBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerDelete deleteBorrowerDTO);
}
