package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.ISchufaRepository;
import de.fhms.jp253163.avss.lms.application.data.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class ConsoleWindow implements IConsoleWindow
{
    private File executionDirectory;
    private ILoanRepository loanRepository;
    private IBorrowerRepository borrowerRepository;
    private ISchufaRepository schufaRepository;

    public ConsoleWindow(File executionDirectory, ILoanRepository loanRepository, IBorrowerRepository borrowerRepository, ISchufaRepository schufaRepository) throws IllegalArgumentException
    {
        if(executionDirectory == null || !executionDirectory.isDirectory() || loanRepository == null || borrowerRepository == null || schufaRepository == null)
            throw new IllegalArgumentException("at least one argument is null or invalid!");

        this.executionDirectory = executionDirectory;
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

    private Date readBirthday(Scanner scanner) throws IllegalArgumentException, ParseException
    {
        int year = 0, month = 0, day = 0;
        String yearText = null, monthText = null, dayText = null;
        String unparsedDateString = null;

        if(scanner == null)
            throw new IllegalArgumentException("argument must not be null!");

        do
        {
            System.out.print("Year of birth (at least 1910): ");
            yearText = scanner.nextLine();

            try
            {
                year = Integer.parseInt(yearText);
            }
            catch(NumberFormatException e)
            {
                year = -1;
            }
        }
        while(year < 1910);

        do
        {
            System.out.print("Month of birth (1 - 12): ");
            monthText = scanner.nextLine();

            try
            {
                month = Integer.parseInt(monthText);
            }
            catch(NumberFormatException e)
            {
                month = -1;
            }
        }
        while(month < 1 || month > 12);

        do
        {
            System.out.print("Day of birth (1 - 31): ");
            dayText = scanner.nextLine();

            try
            {
                day = Integer.parseInt(dayText);
            }
            catch(NumberFormatException e)
            {
                day = -1;
            }
        }
        while(day < 1 || day > 31);

        // Used help from here for Date parsing from a given format: https://www.baeldung.com/java-string-to-date

        unparsedDateString = String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        return DateUtils.parseDate(unparsedDateString, "yyyy-MM-dd");
    }

    private IResidentialAddress readResidentialAddress(Scanner scanner) throws IllegalArgumentException
    {
        String street = null, no = null, place = null;
        int zip = 0;
        String zipText = null;

        do
        {
            System.out.print("Street (max. 30 symbols): ");
            street = scanner.nextLine();
        }
        while(street.isEmpty() || street.length() > 30);

        do
        {
            System.out.print("No (max. 4 symbols): ");
            no = scanner.nextLine();
        }
        while(no.isEmpty() || no.length() > 4);

        do
        {
            System.out.print("Place (max. 30 symbols): ");
            place = scanner.nextLine();
        }
        while(place.isEmpty() || place.length() > 30);

        do
        {
            System.out.print("Zip code (exactly 5 symbols): ");
            zipText = scanner.nextLine();

            try
            {
                zip = Integer.parseInt(zipText);
            }
            catch(NumberFormatException e)
            {
                zip = -1;
            }
        }
        while(zip < 0 || zip > 99999);

        return new ResidentialAddress(street, no, zip, place);
    }

    private void commandCreateBorrower(Scanner scanner, String[] parameters)
    {
         IBorrower borrower = null;

         String firstname = null, lastname = null;

         Date dateOfBirth = null;

         IResidentialAddress residentialAddress = null;

         double schufaScore = 0;

         ILoan[] loans = null;

         if(parameters != null && parameters.length == 2)
         {
             do
             {
                 System.out.print("Firstname (3 - 20 symbols): ");
                 firstname = scanner.nextLine();
             }
             while(firstname == null || firstname.length() < 2 || firstname.length() > 20);

             do
             {
                 System.out.print("Lastname (3 - 20 symbols): ");
                 lastname = scanner.nextLine();
             }
             while(lastname == null || lastname.length() < 2 || lastname.length() > 20);

             try
             {
                 dateOfBirth = readBirthday(scanner);
             }
             catch(ParseException e)
             {
                 e.printStackTrace();

                 return;
             }

             residentialAddress = readResidentialAddress(scanner);

             borrower = borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d);

             if(borrower != null)
                System.out.println("Created new borrower");
             else
                 System.out.println("Could not create borrower!");
         }
    }

    private void commandBorrower(Scanner scanner, String[] parameters)
    {
        String action = null;
        String detail = null;
        String number = null;
        int nbmberConverted = -1;
        String optionalMessage = null;
        IBorrower[] borrowers = null;

        if(scanner != null && parameters != null && parameters.length > 1)
        {
            action = parameters[1];

            if(action != null)
            {
                if(action.equalsIgnoreCase("create"))
                {
                    commandCreateBorrower(scanner, parameters);

                    return;
                }
                else if(action.equalsIgnoreCase("read"))
                {
                    if(parameters.length > 2)
                    {
                        detail = parameters[2];

                        if(detail.equalsIgnoreCase("all"))
                        {
                            borrowers = borrowerRepository.readAllBorrowers();

                            if(borrowers == null || borrowers.length == 0)
                            {
                                optionalMessage = "No borrowers yet created!";
                            }

                            showBorrowerData(borrowers, optionalMessage);
                        }
                        else if(detail.equalsIgnoreCase("id") && parameters.length > 3)
                        {
                            try
                            {
                                number = parameters[3];

                                nbmberConverted = Integer.parseInt(number);

                                borrowers = new IBorrower[]{ borrowerRepository.readBorrower(nbmberConverted) };
                            }
                            catch(NumberFormatException e)
                            {
                                optionalMessage = "That was no valid number!";
                            }

                            showBorrowerData(borrowers, optionalMessage);
                        }
                        else
                        {
                            showHelp();
                        }
                    }
                    else
                    {
                        showHelp();
                    }

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

    private void commandInfo()
    {
        System.out.println("Info");
        System.out.println("-----");
        System.out.println();
        System.out.println("You are running this application from: " + executionDirectory.getAbsolutePath());
        System.out.println();
    }

    private int process(Scanner scanner, String command)
    {
        String[] parameters = StringUtils.split(command, " ");

        if(executionDirectory == null || scanner == null)
            return 0;

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
            else if(parameters[0].equalsIgnoreCase("/info"))
            {
                commandInfo();

                return 0;
            }
            else if(parameters[0].equalsIgnoreCase("/borrower"))
            {
                commandBorrower(scanner, parameters);

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

        showHelp();

        do
        {
            System.out.print("> ");
            command = scanner.nextLine();

            statusCode = process(scanner, command);
        }
        while(command != null && statusCode != -1);

        scanner.close();
    }

    @Override
    public void showHelp()
    {
        System.out.println("Help");
        System.out.println("-----");
        System.out.println();
        System.out.println("1. /borrower [create, read, update, delete]");
        System.out.println("2. /loan [create, read, update, delete]");
        System.out.println("3. /info    Runtime details");
        System.out.println("4. /exit");
        System.out.println();
    }

    @Override
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage)
    {
        String output = "";

        if(optionalMessage != null && !optionalMessage.isEmpty())
        {
            output += optionalMessage;
        }

        if(borrowers != null)
        {
            for(IBorrower borrower : borrowers)
            {
                output += borrower + "\n";
            }
        }

        System.out.println(output);
    }

    @Override
    public void showLoanData(ILoan[] loans, String optionalMessage)
    {

    }
}
