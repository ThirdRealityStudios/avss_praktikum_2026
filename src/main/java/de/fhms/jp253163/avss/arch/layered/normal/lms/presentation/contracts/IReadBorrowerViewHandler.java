package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

public interface IReadBorrowerViewHandler
{
    /**
     * Determines an array like IDTOBorrowerRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * @param borrowerIDs
     */
    public void invoke(int[] borrowerIDs);
}
