package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    public void invoke(IRequestDTOLoanCreate createLoanDTO);
}
