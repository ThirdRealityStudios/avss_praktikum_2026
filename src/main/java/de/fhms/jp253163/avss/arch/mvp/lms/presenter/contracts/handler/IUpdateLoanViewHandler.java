package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOLoanUpdate;

public interface IUpdateLoanViewHandler
{
    public void invoke(IRequestDTOLoanUpdate loanUpdateDTO);
}
