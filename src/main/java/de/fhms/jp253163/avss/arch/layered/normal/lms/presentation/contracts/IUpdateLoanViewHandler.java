package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IRequestDTOLoanUpdate;

public interface IUpdateLoanViewHandler
{
    public void invoke(IRequestDTOLoanUpdate loanUpdateDTO);
}
