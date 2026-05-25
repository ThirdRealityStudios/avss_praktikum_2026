package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOBorrowerUpdate;

public interface IUpdateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerUpdate updateBorrowerDTO);
}