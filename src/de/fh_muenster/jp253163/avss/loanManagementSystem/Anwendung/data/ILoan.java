package de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data;

public interface ILoan
{
    public int getId();
    public boolean setId(int id);
    public double getAmount();
    public boolean setAmount(double amount);
    public double getInterestRate();
    public boolean setInterestRate(double interestRate);
    public int getTerm();
    public boolean setTerm(int term);
    public boolean payInstallment();
    public int getRemainingTerm();
    public boolean setRemainingTerm(int remainingTerm);
    public double getRemainingBalance();
    public boolean setRemainingBalance(double remainingBalance);
    public IBorrower getBorrower();
    public boolean setBorrower(IBorrower borrower);
}
