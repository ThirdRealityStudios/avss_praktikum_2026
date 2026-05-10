package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.ISchufaRepository;
import de.fhms.jp253163.avss.lms.application.data.*;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.util.Date;
import java.util.Scanner;

public class ConsoleWindow implements IConsoleWindow
{
    private File executionDirectory;

    private ILoanRepository loanRepository;
    private IBorrowerRepository borrowerRepository;
    private ISchufaRepository schufaRepository;

    private CommandCreateBorrower commandCreateBorrower;
    private CommandCreateLoan commandCreateLoan;

    private BorrowerInputReader borrowerInputReader;
    private LoanInputReader loanInputReader;

    private Scanner scanner;
    private InputReader inputReader;

    public ConsoleWindow(File executionDirectory, ILoanRepository loanRepository, IBorrowerRepository borrowerRepository, ISchufaRepository schufaRepository) throws IllegalArgumentException
    {
        if(executionDirectory == null || !executionDirectory.isDirectory() || loanRepository == null || borrowerRepository == null || schufaRepository == null)
            throw new IllegalArgumentException("at least one argument is null or invalid!");

        this.scanner = new Scanner(System.in);
        this.inputReader = new InputReader(scanner);

        this.borrowerInputReader = new BorrowerInputReader(borrowerRepository, inputReader);
        this.loanInputReader = new LoanInputReader(inputReader);
        this.commandCreateBorrower = new CommandCreateBorrower(borrowerRepository, borrowerInputReader, scanner);
        this.commandCreateLoan = new CommandCreateLoan(loanRepository, loanInputReader);
        this.executionDirectory = executionDirectory;
        this.loanRepository = loanRepository;
        this.borrowerRepository = borrowerRepository;
        this.schufaRepository = schufaRepository;
    }

    private void commandExit()
    {
        System.out.println("Goodbye!");
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
        int deleteId = -1;

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

                            return;
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
                                        birthday = borrowerInputReader.readBirthday();

                                        if(birthday == null || !borrowers[0].setDateOfBirth(birthday))
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
                else if(action.equalsIgnoreCase("delete") && parameters.length == 3 && StringUtils.isNumeric(parameters[2])) // e.g. /borrower delete [borrower ID]
                {
                    try
                    {
                        deleteId = Integer.parseInt(parameters[2]);
                    }
                    catch(NumberFormatException e)
                    {
                        return;
                    }

                    if(borrowerRepository.deleteBorrower(deleteId))
                    {
                        optionalMessage = "Deleted borrower successfully (had nothing left to pay off)";
                    }
                    else
                    {
                        optionalMessage = "Deleting borrower failed (has at least one loan active in the system still)";
                    }

                    showBorrowerData(new IBorrower[0], optionalMessage);

                    return;
                }
            }
        }

        showHelp();
    }

    private void commandLoan(Scanner scanner, String[] parameters)
    {
        String action = null;
        String detail = null;
        String updateId = null;
        String updateTarget = null;
        String targetId = null;
        int numberConverted = -1;
        String optionalMessage = null;
        ILoan[] loans = null;
        IBorrower newBorrower = null;
        Date birthday = null;
        boolean errorOccurred = false;
        int deleteId = -1;

        if(scanner != null && parameters != null && parameters.length >= 2)
        {
            action = parameters[1];

            if(action != null)
            {
                if(action.equalsIgnoreCase("create"))
                {
                    commandCreateLoan.run(parameters);

                    return;
                }
                else if(action.equalsIgnoreCase("read") && parameters.length >= 3)
                {
                    detail = parameters[2];

                    if(detail.equalsIgnoreCase("all"))
                    {
                        loans = loanRepository.readAllLoans();

                        if(loans == null || loans.length == 0)
                        {
                            optionalMessage = "No loans yet created!";
                        }

                        showLoanData(loans, optionalMessage);
                    }
                    else if(StringUtils.isNumeric(detail))
                    {
                        try
                        {
                            numberConverted = Integer.parseInt(detail);

                            loans = new ILoan[]{ loanRepository.readLoan(numberConverted) };

                            if(loans[0] == null)
                                optionalMessage = "Loan not found!";
                        }
                        catch(NumberFormatException e)
                        {
                            optionalMessage = "That was no valid number!";
                        }

                        showLoanData(loans, optionalMessage);
                    }
                    else
                    {
                        showHelp();
                    }
                }
                else if(parameters.length == 5 && action.equalsIgnoreCase("update"))
                {
                    /**
                     *      /loan update [loan ID] borrower [new borrower ID]
                     *
                     *      /loan update [loan ID] book installment
                     */

                    String loanIDParam = parameters[2],
                            loanAction = parameters[3],
                            loanBorrowerID = parameters[4],
                            loanBookAction = parameters[4];

                    int loanIDParamConverted = -1;

                    ILoan loan = null;

                    int loanBorrowerIDConverted = -1;

                    if(StringUtils.isNumeric(loanIDParam))
                    {
                        try
                        {
                            loanIDParamConverted = Integer.parseInt(loanIDParam);

                            loan = loanRepository.readLoan(loanIDParamConverted);

                            if(loan == null)
                                throw new NumberFormatException(); // little "workaround", although NumberFormatException does not quite fit.
                        }
                        catch(NumberFormatException e)
                        {
                            optionalMessage = "Not a valid number for loan ID or loan could not be loaded!";

                            showLoanData(new ILoan[0], optionalMessage);

                            return;
                        }

                        if(loanAction.equalsIgnoreCase("borrower"))
                        {
                            if(StringUtils.isNumeric(loanBorrowerID))
                            {
                                try
                                {
                                    loanBorrowerIDConverted = Integer.parseInt(loanBorrowerID);

                                    loan.setBorrower(loanBorrowerIDConverted);

                                    if(loanRepository.updateLoan(loan))
                                    {
                                        optionalMessage = "Updated loan successfully (changed borrower)!";

                                        showLoanData(new ILoan[]{ loan }, optionalMessage);

                                        return;
                                    }
                                }
                                catch(NumberFormatException e)
                                {
                                    // Should show "Invalid value" or smth.
                                    optionalMessage = "That is not a valid number for a borrower ID!";

                                    showLoanData(new ILoan[0], optionalMessage);

                                    return;
                                }
                            }
                        }
                        else if(loanAction.equalsIgnoreCase("book"))
                        {
                            if(loanBookAction.equalsIgnoreCase("installment"))
                            {
                                if(loan.payInstallment() && loanRepository.updateLoan(loan))
                                {
                                    optionalMessage = "Updated loan successfully (booked installment)";
                                }
                                else
                                {
                                    optionalMessage = "Could not book the installment!";
                                    loan = null;
                                }

                                showLoanData(new ILoan[]{ loan }, optionalMessage);

                                return;
                            }
                        }
                    }
                }
                else if(action.equalsIgnoreCase("delete") && parameters.length == 3 && StringUtils.isNumeric(parameters[2])) // e.g. /loan delete [loan ID]
                {
                    try
                    {
                        deleteId = Integer.parseInt(parameters[2]);
                    }
                    catch(NumberFormatException e)
                    {
                        return;
                    }

                    if(loanRepository.deleteLoan(deleteId))
                    {
                        optionalMessage = "Deleted loan successfully (paid off)";
                    }
                    else
                    {
                        optionalMessage = "Deleting loan failed (most likely not paid off yet)";
                    }

                    loans = new ILoan[0];

                    showLoanData(loans, optionalMessage);

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
            else if(parameters[0].equalsIgnoreCase("/loan"))
            {
                commandLoan(scanner, parameters);

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
                    output += borrower + "\n\n";
            }
        }

        System.out.println(output);
    }

    @Override
    public void showLoanData(ILoan[] loans, String optionalMessage)
    {
        String output = "";

        if(optionalMessage != null && !optionalMessage.isEmpty())
        {
            output += optionalMessage;
        }

        if(loans != null)
        {
            for(ILoan loan : loans)
            {
                if(loan != null)
                    output += loan + "\n\n";
            }
        }

        System.out.println(output);
    }
}