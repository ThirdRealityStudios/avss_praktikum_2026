package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IDTOBorrowerRemove;

public interface IRemoveBorrowerViewHandler
{
    public void invoke(IDTOBorrowerRemove removeBorrowerDTO);
}
