package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.command.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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

        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        userInput = scanner.nextLine();

        scanner.close();

        return userInput;
    }

    private boolean hasNullElements(String[] arr)
    {
        for(String s : arr)
            if(s == null)
                return true;

        return false;
    }

    private Object parse(String userInput)
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
        catch(IndexOutOfBoundsException e)
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

        return null;
    }

    private Object interpret(Object command)
    {
        if(command instanceof CommandCreateBorrower)
        {
            CommandCreateBorrower parsedCommand = (CommandCreateBorrower) command;

            IRequestDTOBorrowerCreate createBorrowerDTO = new
        }
        else if(command instanceof CommandReadBorrower)
        {
            CommandReadBorrower parsedCommand = (CommandReadBorrower) command;


        }
        else if(command instanceof CommandUpdateBorrower)
        {
            CommandUpdateBorrower parsedCommand = (CommandUpdateBorrower) command;


        }
        else if(command instanceof CommandDeleteBorrower)
        {
            CommandDeleteBorrower parsedCommand = (CommandDeleteBorrower) command;


        }
        else if(command instanceof CommandCreateLoan)
        {
            CommandCreateLoan parsedCommand = (CommandCreateLoan) command;


        }
        else if(command instanceof CommandReadLoan)
        {
            CommandReadLoan parsedCommand = (CommandReadLoan) command;


        }
        else if(command instanceof CommandUpdateLoan)
        {
            CommandUpdateLoan parsedCommand = (CommandUpdateLoan) command;


        }
        else if(command instanceof CommandDeleteLoan)
        {
            CommandDeleteLoan parsedCommand = (CommandDeleteLoan) command;


        }

        return null; // Event handler triggered
    }

    @Override
    public void start()
    {
        while(true)
        {
            String userInput = readUserInput();
            Object command = parse(userInput);
            Object handler = interpret(command);

            // TODO Invoke
        }
    }

    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void setBorrowerData(List<IRequestDTOBorrowerCreate> borrowers)
    {

    }

    @Override
    public void setLoanData(List<IRequestDTOLoanCreate> loans)
    {

    }
}
