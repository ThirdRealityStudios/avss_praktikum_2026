package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    public void invoke(IRequestDTOLoanCreate createLoanDTO);
}
