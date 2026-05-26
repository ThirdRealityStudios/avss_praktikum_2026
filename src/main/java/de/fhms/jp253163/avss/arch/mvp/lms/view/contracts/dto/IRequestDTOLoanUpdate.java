package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto;

public interface IRequestDTOLoanUpdate
{
    /**
     * @return The used loan ID for requested update.
     */
    public int getLoanID();

    /**
     * The borrower ID of a loan is the only thing which can be updated
     * (because sometimes somebody takes over your loan)
     *
     * @return The attached borrower ID to change / update.
     */
    public int getBorrowerID();

    /**
     * @return Whether an installment should be booked (true) or not (false)
     */
    public boolean isBookInstallmentWanted();
}
