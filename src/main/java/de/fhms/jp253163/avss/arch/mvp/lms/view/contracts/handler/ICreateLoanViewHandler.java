package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    public void invoke(IRequestDTOLoanCreate createLoanDTO);
}
