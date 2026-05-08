package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.ISchufaRepository;
import de.fhms.jp253163.avss.lms.application.data.*;
import org.apache.commons.lang3.StringUtils;

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
    private CommandCreateBorrower commandCreateBorrower;
    private BorrowerInputReader borrowerInputReader;
    private Scanner scanner;

    public ConsoleWindow(File executionDirectory, ILoanRepository loanRepository, IBorrowerRepository borrowerRepository, ISchufaRepository schufaRepository) throws IllegalArgumentException
    {
        if(executionDirectory == null || !executionDirectory.isDirectory() || loanRepository == null || borrowerRepository == null || schufaRepository == null)
            throw new IllegalArgumentException("at least one argument is null or invalid!");

        this.scanner = new Scanner(System.in);
        this.borrowerInputReader = new BorrowerInputReader(borrowerRepository, scanner);
        this.commandCreateBorrower = new CommandCreateBorrower(borrowerRepository, borrowerInputReader, scanner);
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

    private void commandBorrower(Scanner scanner, String[] parameters)
    {
        String action = null;
        String detail = null;
        String updateId = null;
        String updateTarget = null;
        int numberConverted = -1;
        String optionalMessage = null;
        IBorrower[] borrowers = null;
        Date birthday = null;
        boolean errorOccurred = false;

        if(scanner != null && parameters != null && parameters.length > 1)
        {
            action = parameters[1];

            if(action != null)
            {
                if(action.equalsIgnoreCase("create"))
                {
                    commandCreateBorrower.run(parameters);

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
                        else if(StringUtils.isNumeric(detail))
                        {
                            try
                            {
                                numberConverted = Integer.parseInt(detail);

                                borrowers = new IBorrower[]{ borrowerRepository.readBorrower(numberConverted) };

                                if(borrowers[0] == null)
                                    optionalMessage = "Borrower not found!";
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
                    if(parameters.length == 4)
                    {
                        updateId = parameters[2];
                        updateTarget = parameters[3];

                        if(StringUtils.isNumeric(updateId) && !updateTarget.isEmpty())
                        {
                            try
                            {
                                numberConverted = Integer.parseInt(updateId);

                                borrowers = new IBorrower[]{ borrowerRepository.readBorrower(numberConverted) };

                                if(borrowers[0] != null)
                                {
                                    optionalMessage = "Update successful!";

                                    if(updateTarget.equalsIgnoreCase("firstname") && !borrowers[0].setFirstname(borrowerInputReader.readFirstname()))
                                    {
                                        errorOccurred = true;
                                    }
                                    else if(updateTarget.equalsIgnoreCase("lastname") && !borrowers[0].setLastname(borrowerInputReader.readLastname()))
                                    {
                                        errorOccurred = true;
                                    }
                                    else if(updateTarget.equalsIgnoreCase("birthday"))
                                    {
                                        try
                                        {
                                            birthday = borrowerInputReader.readBirthday();

                                            if(birthday == null || !borrowers[0].setDateOfBirth(birthday))
                                            {
                                                errorOccurred = true;
                                            }
                                        }
                                        catch(ParseException e)
                                        {
                                            errorOccurred = true;
                                        }
                                    }
                                    else if(updateTarget.equalsIgnoreCase("address") && !borrowers[0].setResidentialAddress(borrowerInputReader.readResidentialAddress()))
                                    {
                                        errorOccurred = true;
                                    }
                                    else if(updateTarget.equalsIgnoreCase("schufa") && !borrowers[0].setSchufaScore(schufaRepository.readSchufaScore(borrowers[0].getId())))
                                    {
                                        errorOccurred = true;
                                    }
                                    else if(updateTarget.equalsIgnoreCase("loans") && !borrowers[0].setLoans(borrowerInputReader.readLoans()))
                                    {
                                        errorOccurred = true;
                                    }

                                    if(errorOccurred)
                                    {
                                        optionalMessage = "Update failed!";
                                    }
                                    else
                                    {
                                        if(!borrowerRepository.updateBorrower(borrowers[0]))
                                        {
                                            optionalMessage = "Update failed! (Reason: file saving)";
                                        }
                                    }
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                optionalMessage = "That was no valid number!";
                            }

                            showBorrowerData(borrowers, optionalMessage);
                        }
                    }
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
                if(borrower != null)
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
