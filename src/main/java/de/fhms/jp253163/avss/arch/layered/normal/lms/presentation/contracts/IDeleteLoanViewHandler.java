package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IRequestDTOLoanDelete;

public interface IDeleteLoanViewHandler
{
    public void invoke(IRequestDTOLoanDelete deleteLoanDTO);
}
