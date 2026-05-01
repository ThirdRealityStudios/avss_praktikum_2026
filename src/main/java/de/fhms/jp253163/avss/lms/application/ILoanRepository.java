package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;

public interface ILoanRepository
{
    public ILoan createLoan(IBorrower borrower, double amount, double interestRate, int term);
    public ILoan readLoanById(int id);
    public ILoan[] readAllLoans();
    public boolean updateLoan(ILoan loan);
    public boolean deleteLoan(int id);
}
