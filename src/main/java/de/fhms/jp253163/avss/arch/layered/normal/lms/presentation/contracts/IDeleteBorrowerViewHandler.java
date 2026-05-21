package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IRequestDTOBorrowerDelete;

public interface IDeleteBorrowerViewHandler
{
    public void invoke(IRequestDTOBorrowerDelete deleteBorrowerDTO);
}
