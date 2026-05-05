package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.ISchufaRepository;
import de.fhms.jp253163.avss.lms.application.data.*;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class ConsoleWindow implements IConsoleWindow
{
    private ILoanRepository loanRepository;
    private IBorrowerRepository borrowerRepository;
    private ISchufaRepository schufaRepository;

    public ConsoleWindow(ILoanRepository loanRepository, IBorrowerRepository borrowerRepository, ISchufaRepository schufaRepository) throws IllegalArgumentException
    {
        if(loanRepository == null || borrowerRepository == null || schufaRepository == null)
            throw new IllegalArgumentException("at least one argument is null!");

        this.loanRepository = loanRepository;
        this.borrowerRepository = borrowerRepository;
        this.schufaRepository = schufaRepository;
    }

    private void commandExit()
    {
        System.out.println("Goodbye!");
    }

    private int genNewBorrowerId()
    {
        IBorrower[] borrowers =  borrowerRepository.readAllBorrowers();

        int maxFoundId = 0;

        for(IBorrower borrower : borrowers)
        {
            if(borrower.getId() > maxFoundId)
            {
                maxFoundId = borrower.getId();
            }
        }

        return maxFoundId+1;
    }

    private void commandCreateBorrower(String[] parameters)
    {
         IBorrower borrower = null;

         String firstname = null, lastname = null;

         int year = 0, month = 0, day = 0;

        IResidentialAddress residentialAddress = null;

        String street = null, no = null, place = null;
        int zip = 0;

        double schufaScore = 0;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            residentialAddress = (IResidentialAddress) new ResidentialAddress(street, no, zip, place);

            loans = new Loan[3];

            borrower = new Borrower(genNewBorrowerId(), firstname, lastname, Date.from(Instant.parse(year+"-"+month+"-"+day)), residentialAddress, schufaScore, loans);

            borrower.setSchufaScore(schufaRepository.readSchufaScore(borrower));
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
