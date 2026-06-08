package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanDelete;

public interface IDeleteLoanViewHandler
{
    public void invoke(IRequestDTOLoanDelete deleteLoanDTO);
}
