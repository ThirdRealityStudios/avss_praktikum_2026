package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto.IRequestDTOBorrowerRead;

public interface IReadBorrowerViewHandler
{
    /**
     * Determines internally an array like IDTOBorrowerRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * @param readBorrowerDTO
     */
    public void invoke(IRequestDTOBorrowerRead readBorrowerDTO);
}
