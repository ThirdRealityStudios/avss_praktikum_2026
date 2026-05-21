package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IDTOBorrowerUpdate;

public interface IUpdateBorrowerViewHandler
{
    public void invoke(IDTOBorrowerUpdate updateBorrowerDTO);
}
