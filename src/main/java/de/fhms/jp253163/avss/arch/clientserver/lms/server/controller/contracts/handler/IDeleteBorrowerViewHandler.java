package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerDelete;

public interface IDeleteBorrowerViewHandler
{
    public int invoke(IRequestDTOBorrowerDelete deleteBorrowerDTO);
}
