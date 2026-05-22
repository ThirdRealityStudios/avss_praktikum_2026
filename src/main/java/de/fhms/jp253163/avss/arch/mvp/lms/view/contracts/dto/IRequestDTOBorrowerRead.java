package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto;

/**
 * Is returned when a borrower was processed by presenter and given to the corresponding view.
 * Contains all necessary borrower data,
 * ready to show.
 */
public interface IRequestDTOBorrowerRead extends IDTOBorrower
{
    public int[] getBorrowerIDs();
}
