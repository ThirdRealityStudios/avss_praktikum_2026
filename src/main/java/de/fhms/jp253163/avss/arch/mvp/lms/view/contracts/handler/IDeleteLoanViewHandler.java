package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;

public interface IDeleteLoanViewHandler
{
    public void invoke(IRequestDTOLoanDelete deleteLoanDTO);
}
