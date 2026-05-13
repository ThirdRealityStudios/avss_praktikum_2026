package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.ILoan;

public class CommandCreateLoan
{
    private ILoanRepository loanRepository;
    private LoanInputReader loanInputReader;

    public CommandCreateLoan(ILoanRepository loanRepository, LoanInputReader loanInputReader) throws IllegalArgumentException
    {
        if(loanRepository == null || loanInputReader == null)
        {
            throw new IllegalArgumentException("at least one argument is null!");
        }

        this.loanRepository = loanRepository;
        this.loanInputReader = loanInputReader;
    }

    public void run(String[] parameters)
    {
        ILoan loan = null;

        int borrowerId = -1, term = -1;
        double amount = -1, interestRate = -1;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            borrowerId = loanInputReader.readBorrowerID("Borrower ID (at least zero): ");

            amount = loanInputReader.readAmount();

            interestRate = loanInputReader.readInterestRate();

            term = loanInputReader.readTerm();

            loan = loanRepository.createLoan(borrowerId, amount, interestRate, term);

            if(loan != null)
                System.out.println("Created new loan");
            else
                System.out.println("Could not create loan!");
        }
    }
}
