package de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data;

public class Loan implements ILoan
{
    private int id;
    private double amount, remainingBalance;
    private double interestRate;
    private int term, remainingTerm;
    private IBorrower borrower;

    public Loan(int id, double amount, double interestRate, int term, IBorrower borrower)
    {

    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public boolean setId(int id)
    {
        if(id >= 0)
        {
            this.id = id;

            return true;
        }

        return false;
    }

    @Override
    public double getAmount()
    {
        return amount;
    }

    @Override
    public boolean setAmount(double amount)
    {
        if(amount >= 0)
        {
            this.amount = amount;

            return true;
        }

        return false;
    }

    @Override
    public double getInterestRate()
    {
        return interestRate;
    }

    @Override
    public boolean setInterestRate(double interestRate)
    {
        if(interestRate >= 0)
        {
            this.interestRate = interestRate;

            return true;
        }

        return false;
    }

    @Override
    public int getTerm()
    {
        return term;
    }

    @Override
    public boolean setTerm(int term)
    {
        if(term >= 1)
        {
            this.term = term;

            return true;
        }

        return false;
    }

    @Override
    public boolean payInstallment()
    {
        // TODO Implement
        return false;
    }

    @Override
    public int getRemainingTerm()
    {
        return remainingTerm;
    }

    @Override
    public boolean setRemainingTerm(int remainingTerm)
    {
        if(remainingTerm >= 0)
        {
            this.remainingTerm = remainingTerm;

            return true;
        }

        return false;
    }

    @Override
    public double getRemainingBalance()
    {
        return remainingBalance;
    }

    @Override
    public boolean setRemainingBalance(double remainingBalance)
    {
        if(remainingBalance >= 0)
        {
            this.remainingBalance = remainingBalance;

            return true;
        }

        return false;
    }

    @Override
    public IBorrower getBorrower()
    {
        return borrower;
    }

    @Override
    public boolean setBorrower(IBorrower borrower)
    {
        if(borrower == null)
        {
            return false;
        }

        return true;
    }
}
