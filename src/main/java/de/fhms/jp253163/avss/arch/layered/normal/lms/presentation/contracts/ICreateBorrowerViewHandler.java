package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    public void invoke(IDTOBorrowerCreate createBorrowerDTO);
}