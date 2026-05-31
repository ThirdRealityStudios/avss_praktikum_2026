package de.fhms.jp253163.avss.arch.mvp.lms.model;

import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;

public interface ILoanRepository
{
    /**
     * Creates a loan if the borrower meets the corresponding conditions,
     * especially Schufa score based.
     * If the loan could be given (= created in the system) then it will be saved persistently in a file.
     *
     * @param borrowerID
     * @param amount
     * @param interestRate
     * @param term
     * @return The loan (also saved persistently yet then). null if the loan was denied.
     */
    public ILoan createLoan(int borrowerID, double amount, double interestRate, int term);

    /**
     * Reads a loan from the system.
     *
     * @param id ID of the loan (not borrower ID!)
     * @return Loan. Otherwise, null (not found).
     */
    public ILoan readLoan(int id);

    /**
     * Gives you all loans known to the system.
     *
     * @return All loans from the system, persistently saved.
     */
    public ILoan[] readAllLoans();

    /**
     * Updates a loan with the given loan object.
     * The ID from the loan object is used to recognize it and to save it correctly.
     *
     * @param loan
     * @return
     */
    public boolean updateLoan(ILoan loan);

    /**
     * Deletes a loan from the system;
     * but only if the conditions are met (e.g. loan paid yet by the borrower completely).
     *
     * @param id The id of the loan in the system (not borrower id!)
     * @return true if the loan could be deleted. Otherwise, false.
     */
    public boolean deleteLoan(int id);

    /**
     * @param borrowerID
     * @return true if borrowerID is at least 0. Otherwise, false.
     */
    public boolean isValidBorrowerIDForCreation(int borrowerID);

    /**
     * @param amount
     * @return true if amount is at least 0.01. Otherwise, false.
     */
    public boolean isValidAmountForCreation(double amount);

    /**
     * @param interestRate
     * @return true if interestRate ranges between 0.01 and 1. Otherwise, false.
     */
    public boolean isValidInterestRateForCreation(double interestRate);

    /**
     * @param term
     * @return true if term is at least 1. Otherwise, false.
     */
    public boolean isValidTermForCreation(int term);

    /**
     * The (attached) borrower ID is the only updatable / editable value of an existing loan
     * (at least directly).
     *
     * @param borrowerID
     * @return true if borrowerID is -1 (= do not update) or at least 0. Otherwise, false.
     */
    public boolean isValidBorrowerIDForUpdate(int borrowerID);
}
