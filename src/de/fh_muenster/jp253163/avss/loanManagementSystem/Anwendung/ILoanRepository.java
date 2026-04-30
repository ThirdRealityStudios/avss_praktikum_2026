package de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung;

import de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data.IBorrower;
import de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data.ILoan;

public interface ILoanRepository
{
    public ILoan createLoan(IBorrower borrower, double amount, double interestRate, int term);
    public ILoan readLoanById(int id);
    public ILoan[] readAllLoans();
    public boolean updateLoan(ILoan loan);
    public boolean deleteLoan(int id);
}
