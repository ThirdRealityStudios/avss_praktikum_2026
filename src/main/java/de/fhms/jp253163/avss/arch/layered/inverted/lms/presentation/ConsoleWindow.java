package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.command.CommandCreateBorrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.command.CommandCreateLoan;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.TerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.reader.BorrowerInputReader;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.reader.InputReader;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.reader.LoanInputReader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class ConsoleWindow implements IConsoleWindow
{
    private File executionDirectory;

    private CommandCreateBorrower commandCreateBorrower;
    private CommandCreateLoan commandCreateLoan;

    private BorrowerInputReader borrowerInputReader;
    private LoanInputReader loanInputReader;

    private Scanner scanner;
    private InputReader inputReader;

    private ITerminalRequestDTO termReqDTO = null;

    public ConsoleWindow(File executionDirectory) throws IllegalArgumentException
    {
        if(executionDirectory == null || !executionDirectory.isDirectory())
            throw new IllegalArgumentException("at least one argument is null or invalid!");

        this.scanner = new Scanner(System.in);
        this.inputReader = new InputReader(scanner);

        this.borrowerInputReader = new BorrowerInputReader(inputReader);
        this.loanInputReader = new LoanInputReader(inputReader);
        this.commandCreateBorrower = new CommandCreateBorrower(borrowerInputReader);
        this.commandCreateLoan = new CommandCreateLoan(loanInputReader);
        this.executionDirectory = executionDirectory;
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
        Date birthday = null;
        boolean errorOccurred = false;
        int deleteId = -1;
        long[] loanIDs = null;
        String[] residentialAddress = null;

        termReqDTO = null;

        if(scanner != null && parameters != null && parameters.length > 1)
        {
            action = parameters[1];

            if(action != null)
            {
                if(action.equalsIgnoreCase("create"))
                {
                    termReqDTO = commandCreateBorrower.run(parameters);

                    return;
                }
                else if(action.equalsIgnoreCase("read"))
                {
                    if(parameters.length > 2)
                    {
                        detail = parameters[2];

                        if(detail.equalsIgnoreCase("all"))
                        {
                            /*
                            borrowers = borrowerRepository.readAllBorrowers();

                            if(borrowers == null || borrowers.length == 0)
                            {
                                optionalMessage = "No borrowers yet created!";
                            }

                            showObjectData(borrowers, optionalMessage);
                             */

                            termReqDTO = new TerminalRequestDTO(null, null, new String[]{"borrower"}, 2);

                            return;
                        }
                        else if(StringUtils.isNumeric(detail))
                        {
                            try
                            {
                                numberConverted = Integer.parseInt(detail);

                                /*
                                borrowers = new IBorrower[]{ borrowerRepository.readBorrower(numberConverted) };

                                if(borrowers[0] == null)
                                    optionalMessage = "Borrower not found!";
                                 */

                                try
                                {
                                    termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower"}, 1);

                                    return;
                                }
                                catch (IllegalArgumentException e)
                                {

                                }
                            }
                            catch(NumberFormatException e)
                            {
                                optionalMessage = "That was no valid number!";
                            }

                            // showObjectData(borrowers, optionalMessage);

                            return;
                        }
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

                                if(updateTarget.equalsIgnoreCase("firstname"))
                                {
                                    try
                                    {
                                        termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower", "firstname", borrowerInputReader.readFirstname()}, 3);

                                        return;
                                    }
                                    catch(IllegalArgumentException e)
                                    {
                                        errorOccurred = true;
                                    }
                                }
                                else if(updateTarget.equalsIgnoreCase("lastname"))
                                {
                                    try
                                    {
                                        termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower", "lastname", borrowerInputReader.readLastname()}, 3);

                                        return;
                                    }
                                    catch(IllegalArgumentException e)
                                    {
                                        errorOccurred = true;
                                    }
                                }
                                else if(updateTarget.equalsIgnoreCase("birthday"))
                                {
                                    birthday = borrowerInputReader.readBirthday();

                                    if(birthday == null)
                                    {
                                        errorOccurred = true;
                                    }
                                    else
                                    {
                                        try
                                        {
                                            termReqDTO = new TerminalRequestDTO(new long[]{numberConverted, birthday.getTime()}, null, new String[]{"borrower", "birthday"}, 3);

                                            return;
                                        }
                                        catch(IllegalArgumentException e)
                                        {
                                            errorOccurred = true;
                                        }
                                    }
                                }
                                else if(updateTarget.equalsIgnoreCase("address"))
                                {
                                    try
                                    {
                                        residentialAddress = borrowerInputReader.readResidentialAddress();

                                        try
                                        {
                                            termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower", "address", residentialAddress[0], residentialAddress[1], residentialAddress[2], residentialAddress[3]}, 3);

                                            return;
                                        }
                                        catch(IllegalArgumentException e)
                                        {
                                            errorOccurred = true;
                                        }
                                    }
                                    catch(IllegalArgumentException e)
                                    {
                                        errorOccurred = true;
                                    }
                                }
                                else if(updateTarget.equalsIgnoreCase("schufa"))
                                {
                                    try
                                    {
                                        termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower", "schufa"}, 3);

                                        return;
                                    }
                                    catch(IllegalArgumentException e)
                                    {
                                        errorOccurred = true;
                                    }
                                }
                                else if(updateTarget.equalsIgnoreCase("loans"))
                                {
                                    loanIDs = borrowerInputReader.readLoans();

                                    if(loanIDs == null)
                                    {
                                        errorOccurred = true;
                                    }
                                    else
                                    {
                                        try
                                        {
                                            long[] loansAndId = Arrays.copyOf(loanIDs, (loanIDs.length+1));
                                            loansAndId[loansAndId.length-1] = numberConverted; // borrower ID as last element exceptionally here. Normally in the beginning.

                                            termReqDTO = new TerminalRequestDTO(loansAndId, null, new String[]{"borrower", "loans"}, 3);

                                            return;
                                        }
                                        catch(IllegalArgumentException e)
                                        {
                                            errorOccurred = true;
                                        }
                                    }
                                }

                                if(errorOccurred)
                                {
                                    optionalMessage = "Update failed!";

                                    termReqDTO = null;
                                }
                                else
                                {
                                    return;
                                }
                                /*
                                else
                                {
                                    try
                                    {
                                        termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"borrower", updateTarget}, 3);

                                        return;
                                    }
                                    catch(IllegalArgumentException e)
                                    {
                                        termReqDTO = null;
                                    }
                                }

                                 */
                            }
                            catch(NumberFormatException e)
                            {
                                optionalMessage = "That was no valid number!";
                            }

                            showObjectData(new Object[0], optionalMessage);

                            return;
                        }
                    }
                }
                else if(action.equalsIgnoreCase("delete") && parameters.length == 3 && StringUtils.isNumeric(parameters[2])) // e.g. /borrower delete [borrower ID]
                {
                    try
                    {
                        deleteId = Integer.parseInt(parameters[2]);

                        termReqDTO = new TerminalRequestDTO(new long[]{deleteId}, null, new String[]{"borrower"}, 4);

                        return;
                    }
                    catch(NumberFormatException e)
                    {
                        termReqDTO = null;

                        optionalMessage = "Deleting borrower failed (cannot read ID)";

                        showObjectData(new Object[0], optionalMessage);

                        return;
                    }

                    /*
                    if(borrowerRepository.deleteBorrower(deleteId))
                    {
                        optionalMessage = "Deleted borrower successfully (had nothing left to pay off)";
                    }
                    else
                    {
                        optionalMessage = "Deleting borrower failed (has at least one loan active in the system still)";
                    }

                    showObjectData(new IBorrower[0], optionalMessage);

                    return;
                     */
                }
            }
        }
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
                    termReqDTO = commandCreateLoan.run(parameters);

                    return;
                }
                else if(action.equalsIgnoreCase("read") && parameters.length >= 3)
                {
                    detail = parameters[2];

                    if(detail.equalsIgnoreCase("all"))
                    {
                        /*
                        loans = loanRepository.readAllLoans();

                        if(loans == null || loans.length == 0)
                        {
                            optionalMessage = "No loans yet created!";
                        }

                        showObjectData(loans, optionalMessage);
                         */

                        termReqDTO = new TerminalRequestDTO(null, null, new String[]{"loan"}, 2);

                        return;
                    }
                    else if(StringUtils.isNumeric(detail))
                    {
                        try
                        {
                            numberConverted = Integer.parseInt(detail);

                            /*
                            loans = new ILoan[]{ loanRepository.readLoan(numberConverted) };

                            if(loans[0] == null)
                                optionalMessage = "Loan not found!";
                             */

                            try
                            {
                                termReqDTO = new TerminalRequestDTO(new long[]{numberConverted}, null, new String[]{"loan"}, 1);

                                return;
                            }
                            catch (IllegalArgumentException e)
                            {

                            }
                        }
                        catch(NumberFormatException e)
                        {
                            optionalMessage = "That was no valid number!";
                        }

                        // showObjectData(loans, optionalMessage);

                        return;
                    }

                    showHelp();
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

                    int loanBorrowerIDConverted = -1;

                    if(StringUtils.isNumeric(loanIDParam))
                    {
                        try
                        {
                            loanIDParamConverted = Integer.parseInt(loanIDParam);

                            /*
                            loan = loanRepository.readLoan(loanIDParamConverted);

                            if(loan == null)
                                throw new NumberFormatException(); // little "workaround", although NumberFormatException does not quite fit.
                             */
                        }
                        catch(NumberFormatException e)
                        {
                            optionalMessage = "Not a valid number for loan ID or loan could not be loaded!";

                            showObjectData(new Object[0], optionalMessage);

                            return;
                        }

                        if(loanAction.equalsIgnoreCase("borrower"))
                        {
                            if(StringUtils.isNumeric(loanBorrowerID))
                            {
                                try
                                {
                                    loanBorrowerIDConverted = Integer.parseInt(loanBorrowerID);

                                    termReqDTO = new TerminalRequestDTO(new long[]{loanIDParamConverted, loanBorrowerIDConverted}, null, new String[]{"loan", "borrowerid"}, 3);

                                    return;

                                    /*
                                    loan.setBorrower(loanBorrowerIDConverted);

                                    if(loanRepository.updateLoan(loan))
                                    {
                                        optionalMessage = "Updated loan successfully (changed borrower)!";

                                        showObjectData(new ILoan[]{ loan }, optionalMessage);

                                        return;
                                    }
                                     */
                                }
                                catch(NumberFormatException e)
                                {
                                    // Should show "Invalid value" or smth.
                                    optionalMessage = "That is not a valid number for a borrower ID!";

                                    showObjectData(new Object[0], optionalMessage);

                                    return;
                                }
                            }
                        }
                        else if(loanAction.equalsIgnoreCase("book"))
                        {
                            if(loanBookAction.equalsIgnoreCase("installment"))
                            {
                                /*
                                if(loan.payInstallment() && loanRepository.updateLoan(loan))
                                {
                                    optionalMessage = "Updated loan successfully (booked installment)";
                                }
                                else
                                {
                                    optionalMessage = "Could not book the installment!";
                                    loan = null;
                                }
                                 */

                                try
                                {
                                    termReqDTO = new TerminalRequestDTO(new long[]{loanIDParamConverted}, null, new String[]{"loan", "book", "installment"}, 3);

                                    return;
                                }
                                catch(IllegalArgumentException e)
                                {
                                    showObjectData(new Object[0], optionalMessage);
                                }

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

                        try
                        {
                            termReqDTO = new TerminalRequestDTO(new long[]{deleteId}, null, new String[]{"loan"}, 4);

                            return;
                        }
                        catch(IllegalArgumentException e)
                        {
                            optionalMessage = "Deleting loan failed (internal processing error)";

                            showObjectData(new Object[0], optionalMessage);
                        }
                    }
                    catch(NumberFormatException e)
                    {
                        optionalMessage = "Deleting loan failed (not a number!)";

                        showObjectData(new Object[0], optionalMessage);
                    }

                    /*
                    if(loanRepository.deleteLoan(deleteId))
                    {
                        optionalMessage = "Deleted loan successfully (paid off)";
                    }
                    else
                    {
                        optionalMessage = "Deleting loan failed (most likely not paid off yet)";
                    }

                    loans = new ILoan[0];

                    showObjectData(loans, optionalMessage);
                     */

                    return;
                }
            }
        }
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
                termReqDTO = null;

                return 0;
            }
        }
    }

    @Override
    public void showCommandInput()
    {
        String command = null;

        Scanner scanner = new Scanner(System.in);

        int statusCode = 0;

        ITerminalRequestDTO terminalRequestDTO = null;

        int crudAction = -1;

        showHelp();

        do
        {
            System.out.print("> ");
            command = scanner.nextLine();

            // TODO terminalRequestDTO from process() as instance variable
            statusCode = process(scanner, command);
        }
        while(command != null && statusCode != -1);

        scanner.close();

        if(termReqDTO == null || termReqDTO.getCRUDAction() == -1)
            showHelp();
    }

    @Override
    public void showHelp()
    {
        System.out.println("Help");
        System.out.println("-----");
        System.out.println();
        System.out.println("1. /borrower");
        System.out.println("\t\tcreate");
        System.out.println("\t\tread [ID | all]");
        System.out.println("\t\tupdate [ID] [firstname | lastname | address | birthday | schufa | loans]");
        System.out.println("\t\tdelete [ID]");
        System.out.println("2. /loan");
        System.out.println("\t\tcreate");
        System.out.println("\t\tread [ID | all]");
        System.out.println("\t\tupdate [ID] [borrower [ID] | book installment]");
        System.out.println("\t\tdelete [ID]");
        System.out.println("3. /info    Runtime details");
        System.out.println("4. /exit");
        System.out.println();
    }

    @Override
    public void showObjectData(Object[] objects, String optionalMessage)
    {
        String output = "";

        if(optionalMessage != null && !optionalMessage.isEmpty())
        {
            output += optionalMessage;
        }

        if(objects != null)
        {
            for(Object object : objects)
            {
                if(object != null)
                    output += object + "\n";
            }
        }

        System.out.print(output);
    }

    public ITerminalRequestDTO getTerminalRequestDTO()
    {
        return termReqDTO;
    }
}