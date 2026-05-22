package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanUpdate;

public interface IUpdateLoanViewHandler
{
    public void invoke(IRequestDTOLoanUpdate loanUpdateDTO);
}
