package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOLoanRead;

public interface IReadLoanViewHandler
{
    /**
     * Determines an array like IDTOLoanRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * @param readLoanDTO
     */
    public void invoke(IRequestDTOLoanRead readLoanDTO);
}
