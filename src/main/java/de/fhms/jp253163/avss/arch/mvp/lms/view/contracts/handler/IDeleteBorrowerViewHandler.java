package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerDelete;

public interface IDeleteBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerDelete deleteBorrowerDTO);
}
