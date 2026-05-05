package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

public class ConsoleWindow implements IConsoleWindow
{
    private ILoanRepository loanRepository;
    private IBorrowerRepository borrowerRepository;

    public ConsoleWindow(ILoanRepository loanRepository, IBorrowerRepository borrowerRepository) throws IllegalArgumentException
    {
        if(loanRepository == null || borrowerRepository == null)
            throw new IllegalArgumentException("at least one argument is null!");

        this.loanRepository = loanRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public void showCommandInput()
    {
        String input = null;

        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.print("> ");
            input = scanner.nextLine();
        }
        while(input != null && !input.equalsIgnoreCase("/exit"));

        scanner.close();

        System.out.println("Goodbye!");
    }

    @Override
    public void showHelp()
    {

    }

    @Override
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage)
    {

    }

    @Override
    public void showLoanData(ILoan[] loans, String optionalMessage)
    {

    }
}
