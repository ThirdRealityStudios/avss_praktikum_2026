package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    public void invoke(IRequestDTOLoanCreate createLoanDTO);
}
