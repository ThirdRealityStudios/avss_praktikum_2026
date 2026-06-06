package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerUpdate;

public interface IUpdateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerUpdate updateBorrowerDTO);
}