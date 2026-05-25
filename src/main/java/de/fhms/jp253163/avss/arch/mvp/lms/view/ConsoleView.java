package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.command.ICommand;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.command.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.utils.BorrowerInputReader;
import de.fhms.jp253163.avss.arch.mvp.lms.view.utils.InputReader;
import de.fhms.jp253163.avss.arch.mvp.lms.view.utils.LoanInputReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleView implements IView
{
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;

    private ICreateLoanViewHandler createLoanViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;
    private IUpdateLoanViewHandler updateLoanViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;

    private InputReader inputReader;
    private BorrowerInputReader borrowerInputReader;
    private LoanInputReader loanInputReader;
    private Scanner scanner;

    public ConsoleView(Scanner scanner, InputReader inputReader, BorrowerInputReader borrowerInputReader, LoanInputReader loanInputReader) throws NullPointerException
    {
        if(scanner == null || inputReader == null || borrowerInputReader == null || loanInputReader == null)
            throw new NullPointerException("at least one argument in constructor is null");

        this.scanner = scanner;
        this.inputReader = inputReader;
        this.borrowerInputReader = borrowerInputReader;
        this.loanInputReader = loanInputReader;
    }

    @Override
    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler handler)
    {
        this.createBorrowerViewHandler = handler;
    }

    @Override
    public void setReadBorrowerViewHandler(IReadBorrowerViewHandler handler)
    {
        this.readBorrowerViewHandler = handler;
    }

    @Override
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler)
    {
        this.updateBorrowerViewHandler = handler;
    }

    @Override
    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler handler)
    {
        this.deleteBorrowerViewHandler = handler;
    }

    @Override
    public void setCreateLoanViewHandler(ICreateLoanViewHandler handler)
    {
        this.createLoanViewHandler = handler;
    }

    @Override
    public void setReadLoanViewHandler(IReadLoanViewHandler handler)
    {
        this.readLoanViewHandler = handler;
    }

    @Override
    public void setUpdateLoanViewHandler(IUpdateLoanViewHandler handler)
    {
        this.updateLoanViewHandler = handler;
    }

    @Override
    public void setDeleteLoanViewHandler(IDeleteLoanViewHandler handler)
    {
        this.deleteLoanViewHandler = handler;
    }

    private String readUserInput()
    {
        String userInput = null;

        System.out.print("> ");

        try
        {
            userInput = scanner.nextLine();
        }
        catch(Exception e)
        {
            return null;
        }

        return userInput;
    }

    private boolean hasNullElements(String[] arr)
    {
        for(String s : arr)
            if(s == null)
                return true;

        return false;
    }

    private ICommand parse(String userInput)
    {
        String[] userEntries = StringUtils.split(userInput, " ");

        if(userEntries == null || userEntries.length == 0)
            return null;

        if(hasNullElements(userEntries))
            return null;

        String command = userEntries[0];

        if(command == null || command.isEmpty())
            return null;

        String[] parameters = null;

        try
        {
            parameters = ArrayUtils.remove(userEntries, 0);

            if(hasNullElements(parameters))
            {
                return null;
            }
        }
        catch(Exception e)
        {
            return null;
        }

        if(parameters.length >= 1)
        {
            if(command.equalsIgnoreCase("/create"))
            {
                if(parameters[0].equalsIgnoreCase("borrower"))
                {
                    return new CommandCreateBorrower(parameters);
                }
                else if(parameters[0].equalsIgnoreCase("loan"))
                {
                    return new CommandCreateLoan(parameters);
                }
            }
            else if(command.equalsIgnoreCase("/read"))
            {
                if(parameters[0].equalsIgnoreCase("borrower"))
                {
                    return new CommandReadBorrower(parameters);
                }
                else if(parameters[0].equalsIgnoreCase("loan"))
                {
                    return new CommandReadLoan(parameters);
                }
            }
            else if(command.equalsIgnoreCase("/update"))
            {
                if(parameters[0].equalsIgnoreCase("borrower"))
                {
                    return new CommandUpdateBorrower(parameters);
                }
                else if(parameters[0].equalsIgnoreCase("loan"))
                {
                    return new CommandUpdateLoan(parameters);
                }
            }
            else if(command.equalsIgnoreCase("/delete"))
            {
                if(parameters[0].equalsIgnoreCase("borrower"))
                {
                    return new CommandDeleteBorrower(parameters);
                }
                else if(parameters[0].equalsIgnoreCase("loan"))
                {
                    return new CommandDeleteLoan(parameters);
                }
            }
        }
        else
        {
            if(command.equalsIgnoreCase("/exit"))
            {
                return new CommandExit();
            }
        }

        return null;
    }

    private void executeCreateBorrower()
    {
        String firstname = borrowerInputReader.readFirstname();
        String lastname = borrowerInputReader.readLastname();

        String street = borrowerInputReader.readStreet();
        String no = borrowerInputReader.readNo();
        String place = borrowerInputReader.readPlace();
        int zip = borrowerInputReader.readZip();

        LocalDate birthday = borrowerInputReader.readBirthday();

        double schufaScore = 0.9d; // simplified assumed

        int[] loans = new int[0]; // in the beginning, there are no loans

        try
        {
            IRequestDTOBorrowerCreate createBorrowerDTO = new RequestDTOBorrowerCreate(firstname, lastname, street, no, place, zip, birthday);

            if(createBorrowerViewHandler != null)
                createBorrowerViewHandler.invoke(createBorrowerDTO); // actual logic run here, defined in the presenter usually anyway.
        }
        catch(NullPointerException e)
        {
            showMessage("Could not create borrower! (code: 1651)");
        }
    }

    private void executeReadBorrower(ICommand readBorrower)
    {
        if(readBorrower.getParameters().length == 2)
        {
            String readTarget = readBorrower.getParameters()[1];

            IRequestDTOBorrowerRead readBorrowerDTO = null;

            if(readTarget.equalsIgnoreCase("all"))
            {
                readBorrowerDTO = new RequestDTOBorrowerRead(-1);

                if(readBorrowerViewHandler != null)
                    readBorrowerViewHandler.invoke(readBorrowerDTO);
            }
            else if(StringUtils.isNumeric(readTarget))
            {
                int id = -1;

                try
                {
                    id = Integer.parseInt(readTarget);
                }
                catch(NumberFormatException e)
                {

                }

                if(id >= 0)
                {
                    readBorrowerDTO = new RequestDTOBorrowerRead(id);

                    if(readBorrowerViewHandler != null)
                        readBorrowerViewHandler.invoke(readBorrowerDTO);
                }
            }
        }
    }

    private void executeUpdateBorrower(ICommand updateBorrower)
    {
        if(updateBorrower.getParameters().length == 3)
        {
            String borrowerID = updateBorrower.getParameters()[1];
            String property = updateBorrower.getParameters()[2];

            if(StringUtils.isNumeric(borrowerID))
            {
                int borrowerIDParsed = -1, zip = -1;
                String firstname = null, lastname = null, street = null, no = null, place = null;
                LocalDate birthday = null;
                int[] loans = null;

                try
                {
                    borrowerIDParsed = Integer.parseInt(borrowerID);

                    if(property.equalsIgnoreCase("firstname"))
                    {
                        firstname = borrowerInputReader.readFirstname();
                    }
                    else if(property.equalsIgnoreCase("lastname"))
                    {
                        lastname = borrowerInputReader.readLastname();
                    }
                    else if(property.equalsIgnoreCase("street"))
                    {
                        street = borrowerInputReader.readStreet();
                    }
                    else if(property.equalsIgnoreCase("no"))
                    {
                        no = borrowerInputReader.readNo();
                    }
                    else if(property.equalsIgnoreCase("place"))
                    {
                        place = borrowerInputReader.readPlace();
                    }
                    else if(property.equalsIgnoreCase("zip"))
                    {
                        zip = borrowerInputReader.readZip();
                    }
                    else if(property.equalsIgnoreCase("birthday"))
                    {
                        birthday = borrowerInputReader.readBirthday();
                    }
                    else if(property.equalsIgnoreCase("loans"))
                    {
                        loans = borrowerInputReader.readLoans();
                    }

                    try
                    {
                        IRequestDTOBorrowerUpdate updateBorrowerDTO = new RequestDTOBorrowerUpdate(borrowerIDParsed, firstname, lastname, birthday, street, no, place, zip, loans);

                        if(updateBorrowerViewHandler != null)
                            updateBorrowerViewHandler.invoke(updateBorrowerDTO); // actual logic run here, defined in the presenter usually anyway.
                    }
                    catch(NullPointerException e)
                    {

                    }
                }
                catch(NumberFormatException e)
                {

                }
            }
        }
    }

    private void executeDeleteBorrower(ICommand deleteBorrower)
    {
        if(deleteBorrower.getParameters().length == 2)
        {
            String deleteTarget = deleteBorrower.getParameters()[1];

            IRequestDTOBorrowerDelete deleteBorrowerDTO = null;

            if(StringUtils.isNumeric(deleteTarget))
            {
                int id = -1;

                try
                {
                    id = Integer.parseInt(deleteTarget);
                }
                catch(NumberFormatException e)
                {
                    return;
                }

                deleteBorrowerDTO = new RequestDTOBorrowerDelete(id);

                if(deleteBorrowerViewHandler != null)
                    deleteBorrowerViewHandler.invoke(deleteBorrowerDTO);
            }
        }
    }

    private void executeCreateLoan()
    {
        int borrowerID = loanInputReader.readBorrowerID("Borrower ID (at least 0)");
        double amount = loanInputReader.readAmount();
        double interestRate = loanInputReader.readInterestRate();
        int term = loanInputReader.readTerm();

        try
        {
            IRequestDTOLoanCreate createLoanDTO = new RequestDTOLoanCreate(borrowerID, amount, interestRate, term);

            if(createLoanViewHandler != null)
                createLoanViewHandler.invoke(createLoanDTO); // actual logic run here, defined in the presenter usually anyway.
        }
        catch(NullPointerException e)
        {

        }
    }

    private void executeReadLoan(ICommand readLoan)
    {
        if(readLoan.getParameters().length == 2)
        {
            String readTarget = readLoan.getParameters()[1];

            IRequestDTOLoanRead readLoanDTO = null;

            if(readTarget.equalsIgnoreCase("all"))
            {
                readLoanDTO = new RequestDTOLoanRead(-1);

                if(readLoanViewHandler != null)
                    readLoanViewHandler.invoke(readLoanDTO);
            }
            else if(StringUtils.isNumeric(readTarget))
            {
                int id = -1;

                try
                {
                    id = Integer.parseInt(readTarget);
                }
                catch(NumberFormatException e)
                {
                    return;
                }

                readLoanDTO = new RequestDTOLoanRead(id);

                if(readLoanViewHandler != null)
                    readLoanViewHandler.invoke(readLoanDTO);
            }
        }
    }

    private void executeUpdateLoan(ICommand updateLoan)
    {
        if(updateLoan.getParameters().length == 3)
        {
            String loanID = updateLoan.getParameters()[1];
            String property = updateLoan.getParameters()[2];

            if(StringUtils.isNumeric(loanID))
            {
                int loanIDParsed = -1, borrowerID = -1;

                try
                {
                    loanIDParsed = Integer.parseInt(loanID);

                    if(property.equalsIgnoreCase("borrower"))
                    {
                        borrowerID = loanInputReader.readBorrowerID("Borrower ID (at least 0)");
                    }

                    try
                    {
                        IRequestDTOLoanUpdate updateLoanDTO = new RequestDTOLoanUpdate(loanIDParsed, borrowerID);

                        if(updateLoanViewHandler != null)
                            updateLoanViewHandler.invoke(updateLoanDTO); // actual logic run here, defined in the presenter usually anyway.
                    }
                    catch(NullPointerException e)
                    {

                    }
                }
                catch(NumberFormatException e)
                {

                }
            }
        }
    }

    private void executeDeleteLoan(ICommand deleteLoan)
    {
        if(deleteLoan.getParameters().length == 2)
        {
            String deleteTarget = deleteLoan.getParameters()[1];

            IRequestDTOLoanDelete deleteLoanDTO = null;

            if(StringUtils.isNumeric(deleteTarget))
            {
                int id = -1;

                try
                {
                    id = Integer.parseInt(deleteTarget);
                }
                catch(NumberFormatException e)
                {
                    return;
                }

                deleteLoanDTO = new RequestDTOLoanDelete(id);

                if(deleteLoanViewHandler != null)
                    deleteLoanViewHandler.invoke(deleteLoanDTO);
            }
        }
    }

    /**
     * Executes a command.
     *
     * @param command
     * @return Usually returns false (do not exist). Except you enter /exit (then returns true to signalize)
     */
    private boolean execute(ICommand command)
    {
        if(command instanceof CommandCreateBorrower)
        {
            executeCreateBorrower();
        }
        else if(command instanceof CommandReadBorrower)
        {
            executeReadBorrower(command);
        }
        else if(command instanceof CommandUpdateBorrower)
        {
            executeUpdateBorrower(command);
        }
        else if(command instanceof CommandDeleteBorrower)
        {
            executeDeleteBorrower(command);
        }
        else if(command instanceof CommandCreateLoan)
        {
            executeCreateLoan();
        }
        else if(command instanceof CommandReadLoan)
        {
            executeReadLoan(command);
        }
        else if(command instanceof CommandUpdateLoan)
        {
            executeUpdateLoan(command);
        }
        else if(command instanceof CommandDeleteLoan)
        {
            executeDeleteLoan(command);
        }
        else if(command instanceof CommandExit)
        {
            return true;
        }

        return false;
    }

    public void showHelp()
    {
        String helpMessage =
        "Help\n" +
        "-----\n" +
        "\n" +
        "1. /create\n" +
        "\t\tborrower\n" +
        "\t\tloan\n" +
        "2. /read [borrower | loan] [ID | all]\n" +
        "3. /update\n" +
        "\t\tborrower [ID] [firstname | lastname | address | birthday | schufa | loans]\n" +
        "\t\tloan [ID] [borrower [ID] | book installment]\n" +
        "4. /delete [borrower | loan] [ID]\n" +
        "5. /info\t\tRuntime details\n" +
        "6. /exit\n";

        showMessage(helpMessage);
    }

    @Override
    public void start()
    {
        boolean exit = false;
        String userInput = null;

        while(!exit)
        {
            userInput = readUserInput();

            if(userInput == null)
                continue;

            ICommand command = parse(userInput);

            if(command == null)
            {
                showHelp();

                continue;
            }

            /** Command execution will also listen to the corresponding events.
             *  E.g. "borrower create event" was detected.
             *  Then it will trigger the (optionally) attached handler too
             *  (logic code usually defined in presenter, as it should for MVP user interactions)
             */
            exit = execute(command);

            userInput = null;
        }

        showMessage("Goodbye!");

        scanner.close();
    }

    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
        System.out.println();
    }

    @Override
    public void setBorrowerData(List<IDTOBorrowerSet> borrowers)
    {
        // in this console application it is unnecessary to save model component data as abstracted DTO in order to change the UI components
        // (because this is a console application)
        // So outputting beautified is enough in this context (It is expected every borrower DTO has overwritten the toString() function - beautified).

        for(IDTOBorrowerSet borrowerSetDTO : borrowers)
        {
            showMessage(borrowerSetDTO.toString());
            showMessage("\n");
        }
    }

    @Override
    public void setLoanData(List<IDTOLoanSet> loans)
    {
        for(IDTOLoanSet loanSetDTO : loans)
        {
            showMessage(loanSetDTO.toString());
            showMessage("\n");
        }
    }
}
