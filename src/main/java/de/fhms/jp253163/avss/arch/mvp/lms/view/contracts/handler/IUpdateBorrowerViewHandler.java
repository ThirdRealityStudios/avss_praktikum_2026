package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;

public interface IUpdateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerUpdate updateBorrowerDTO);
}