package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanUpdate;

public interface IUpdateLoanViewHandler
{
    public void invoke(IRequestDTOLoanUpdate loanUpdateDTO);
}
