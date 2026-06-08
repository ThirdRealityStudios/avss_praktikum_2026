package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    public void invoke(IRequestDTOLoanCreate createLoanDTO);
}
