package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOBorrowerDelete;

public interface IDeleteBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerDelete deleteBorrowerDTO);
}
