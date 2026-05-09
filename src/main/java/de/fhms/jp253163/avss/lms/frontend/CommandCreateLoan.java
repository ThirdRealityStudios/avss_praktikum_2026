package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class CommandCreateLoan
{
    private ILoanRepository loanRepository;
    private LoanInputReader loanInputReader;
    private Scanner scanner;

    public CommandCreateLoan(ILoanRepository loanRepository, LoanInputReader loanInputReader, Scanner scanner) throws IllegalArgumentException
    {
        if(loanRepository == null || loanInputReader == null || scanner == null)
        {
            throw new IllegalArgumentException("at least one argument is null!");
        }

        this.loanRepository = loanRepository;
        this.loanInputReader = loanInputReader;
        this.scanner = scanner;
    }

    public void run(String[] parameters)
    {
        IBorrower loan = null;

        int borrowerId = -1, term = -1;
        double amount = -1, interestRate = -1;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            borrowerId = loanInputReader.readBorrower();

            loan = loanRepository.createLoan(borrowerId, amount, interestRate, term);

            if(loan != null)
                System.out.println("Created new loan");
            else
                System.out.println("Could not create loan!");
        }
    }
}
