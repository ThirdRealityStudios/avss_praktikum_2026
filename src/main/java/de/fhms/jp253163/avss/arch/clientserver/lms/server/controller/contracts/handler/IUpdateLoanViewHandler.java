package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanUpdate;

public interface IUpdateLoanViewHandler
{
    public int invoke(IRequestDTOLoanUpdate loanUpdateDTO);
}
