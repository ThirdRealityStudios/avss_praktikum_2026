package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

public interface IReadLoanViewHandler
{
    /**
     * Determines an array like IDTOLoanRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * @param loanIDs
     */
    public void invoke(int[] loanIDs);
}
