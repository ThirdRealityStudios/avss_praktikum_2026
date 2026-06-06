package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerCreate createBorrowerDTO);
}