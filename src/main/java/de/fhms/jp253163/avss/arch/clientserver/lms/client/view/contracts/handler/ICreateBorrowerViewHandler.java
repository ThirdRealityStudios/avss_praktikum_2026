package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerCreate createBorrowerDTO);
}