package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOLoanDelete;

public interface IDeleteLoanViewHandler
{
    public void invoke(IRequestDTOLoanDelete deleteLoanDTO);
}
