package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerCreate createBorrowerDTO);
}