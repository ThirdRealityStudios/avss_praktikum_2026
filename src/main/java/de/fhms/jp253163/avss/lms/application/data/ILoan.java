package de.fhms.jp253163.avss.lms.application.data;

public interface ILoan
{
    public int getId();
    /**
     * Sets the ID for the loan but only once.
     * After that impossible to change.
     *
     * @param id ID to set.
     * @return True if the ID could be set. Otherwise, false.
     */
    public boolean setId(int id);

    public double getAmount();
    /**
     * Sets the amount of the loan which should be taken.
     * Can only be set once.
     * After that impossible.
     *
     * @param amount Amount of the loan.
     * @return True if the amount could be set. Otherwise, false.
     */
    public boolean setAmount(double amount);

    public double getInterestRate();
    /**
     * Sets the interest of the loan.
     * Can only be set once.
     * After that impossible to change.
     *
     * @param interestRate Interest rate to change. E.g. 0.01 stands for 1%, 0.09 stands for 9%.
     * @return true if the interest rate could be set. Otherwise, false.
     */
    public boolean setInterestRate(double interestRate);

    public int getTerm();
    /**
     * Sets the term of the loan.
     * Can only be set once.
     * After that impossible.
     *
     * @param term Term of the loan in months. E.g. 1 stands for 1 month, an argument of 19 stands for 1 year and 7 months.
     * @return True if the term could be set. Otherwise, false.
     */
    public boolean setTerm(int term);

    /**
     * Pays the installment (or exactly: it does at least recognize it was paid).
     * It will reduce automatically the remaining term and remaining balance still to pay.
     *
     * @return True if the paid installment could be recognized successfully. Otherwise, false.
     */
    public boolean payInstallment();

    public int getRemainingTerm();
    /**
     * Sets the remaining term.
     * Internally used by payInstallment().
     * This function will only accept lower values than the old ones (a term should always decrease over time - can never grow suddenly or be manipulated badly).
     * Exception is: this is the first time a remaining term is set (= when loan is given / created).
     *
     * @param remainingTerm Remaining term left, unit is in months. E.g. 1 is 1 month, 19 are 19 months.
     * @return True if the remaining term is set properly. Otherwise, false.
     */
    public boolean setRemainingTerm(int remainingTerm);

    public double getRemainingBalance();
    /**
     * Sets the remaining balance.
     * Internally used by payInstallment().
     * This function will only accept lower values than the old ones (a remaining balance should always decrease over time - can never grow suddenly or be manipulated badly).
     * Exception is: this is the first time a remaining balance is set (= when a loan is given / created).
     *
     * @param remainingBalance Remaining balance left to pay. E.g. 100.23 is 100 EUR and 23 cents (if the currency applies).
     * @return True if the remaining balance could be set properly. Otherwise, false.
     */
    public boolean setRemainingBalance(double remainingBalance);

    public int getBorrower();
    /**
     * Sets the borrower of your loan,
     * using the corresponding ID.
     * Can be used multiple times.
     * Simply because someone could instead take the responsibility to the corresponding loan (German: bürgen).
     *
     * @param borrower Borrower ID to set.
     * @return True if the borrower could be set. Otherwise, false (e.g. no valid borrower given, like -2 or so).
     */
    public boolean setBorrower(int borrower);
}
