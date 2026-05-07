package de.fhms.jp253163.avss.lms.application.data;

import java.util.IllegalFormatException;

/**
 * For easier implementation it is assumed,
 * you cannot pay the installments earlier in larger scales in order to pay your loan faster.
 */
public class Loan implements ILoan
{
    private int id = -1;
    private double amount = -1, remainingBalance = -1;
    private double interestRate = -1;
    private int term = -1, remainingTerm = -1;
    private IBorrower borrower;

    public Loan(int id, double amount, double interestRate, int term, IBorrower borrower) throws IllegalArgumentException
    {
        IllegalArgumentException defaultException = new IllegalArgumentException("Given argument is invalid, null or has invalid format.");

        if(!setId(id) ||
            !setAmount(amount) ||
            !setInterestRate(interestRate) ||
            !setTerm(term) ||
            !setBorrower(borrower))
        {
            throw defaultException;
        }

        // Determine the total amount to pay from the borrower.
        if(!setRemainingBalance(amount + getTotalInterest()))
        {
            throw defaultException;
        }
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public boolean setId(int id)
    {
        if(id >= 0 && this.id == -1) // you can only change or set the ID once.
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
        if(amount > 0 && this.amount == -1) // you can only set the amount once (when you take the loan)
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
        if(interestRate > -1 && interestRate < 1 && this.interestRate == -1)
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
        if(term >= 1 && this.term == -1)
        {
            this.term = term;

            return setRemainingTerm(term);
        }

        return false;
    }

    private double getTermInYears()
    {
        return getTerm() / 12d;
    }

    private double getTotalInterest()
    {
        return getAmount() * (Math.pow(1 + getInterestRate(), getTermInYears()) - 1);
    }

    private double getMonthlyInterest()
    {
        return getTotalInterest() / getTermInYears();
    }

    private double getMonthlyInstallment()
    {
        return getAmount() / getTerm() + getMonthlyInterest();
    }

    @Override
    public boolean payInstallment()
    {
        boolean remainingBalanceUpdated = false;
        boolean remainingTermUpdated = false;

         if(getRemainingTerm() > 0)
         {
             remainingBalanceUpdated = setRemainingBalance(getRemainingBalance() - getMonthlyInstallment());

             if(!remainingBalanceUpdated)
             {
                 return false;
             }

             remainingTermUpdated = setRemainingTerm(getRemainingTerm() - 1);

             if(!remainingTermUpdated)
             {
                 return false;
             }

             return true;
         }
         else
         {
             return false; // The loan is yet done or paid completely.
         }
    }

    @Override
    public int getRemainingTerm()
    {
        return remainingTerm;
    }

    @Override
    public boolean setRemainingTerm(int remainingTerm)
    {
        if(remainingTerm >= 0 && this.remainingTerm == -1 || remainingTerm < this.remainingTerm)
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
        if(remainingBalance >= 0 && this.remainingBalance == -1 || remainingBalance < this.remainingBalance)
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

        this.borrower = borrower;

        return true;
    }

    @Override
    public String toString() throws IllegalFormatException
    {
        return "Loan with ID: " + getId() + "\nAmount taken: " + getAmount() + "\nTime agreed to pay: " + getTerm() + "\nInterest rate: " + String.format("2.2f", getInterestRate() * 100) + "%" + "\nRemaining balance to pay: " + String.format("%.2f", getRemainingBalance()) + "\nTerm remaining: " + getRemainingTerm() + "\nBorrower is: \n" + getBorrower();
    }
}
