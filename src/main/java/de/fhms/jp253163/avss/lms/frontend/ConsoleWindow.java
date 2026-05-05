package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.data.*;
import org.apache.commons.lang3.StringUtils;

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

    private void commandExit()
    {
        System.out.println("Goodbye!");
    }

    private void commandCreateBorrower(String[] parameters)
    {
         IBorrower borrower = null;

         String firstname = null, lastname = null;

         int year = 0, month = 0, day = 0;

        IResidentialAddress residentialAddress = null;

        String street = null, no = null, place = null;
        int zip = 0;

        if(parameters != null && parameters.length == 2)
        {
            
        }
    }

    private void commandBorrower(String[] parameters)
    {
        String action = null;

        if(parameters != null && parameters.length > 1)
        {
            action = parameters[1];

            if(action != null)
            {
                if(action.equalsIgnoreCase("create"))
                {


                    return;
                }
                else if(action.equalsIgnoreCase("read"))
                {


                    return;
                }
                else if(action.equalsIgnoreCase("update"))
                {


                    return;
                }
                else if(action.equalsIgnoreCase("delete"))
                {


                    return;
                }
            }
        }

        showHelp();
    }

    private int process(String command)
    {
        String[] parameters = StringUtils.split(command, " ");

        if(parameters.length == 0)
        {
            System.out.println("Please enter a command!");

            return 0;
        }
        else
        {
            if(parameters[0].equalsIgnoreCase("/exit"))
            {
                commandExit();

                return -1;
            }
            else if(parameters[0].equalsIgnoreCase("/borrower"))
            {
                commandBorrower(parameters);

                return 0;
            }
            else
            {
                showHelp();

                return 0;
            }
        }

    }

    @Override
    public void showCommandInput()
    {
        String command = null;

        Scanner scanner = new Scanner(System.in);

        IBorrower[] borrowers = null;
        ILoan[] loans = null;

        int statusCode = 0;

        do
        {
            System.out.print("> ");
            command = scanner.nextLine();

            statusCode = process(command);
        }
        while(command != null && statusCode != -1);

        scanner.close();
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
