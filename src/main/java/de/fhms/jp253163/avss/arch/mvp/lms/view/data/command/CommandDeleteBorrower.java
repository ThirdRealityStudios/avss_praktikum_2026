package de.fhms.jp253163.avss.arch.mvp.lms.view.data.command;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.command.ICommand;

public class CommandDeleteBorrower implements ICommand
{
    private String command;
    private String[] parameters;

    /**
     * @param parameters First element is always expected to be "borrower", followed by optional parameters.
     * @throws IllegalArgumentException
     */
    public CommandDeleteBorrower(String[] parameters) throws IllegalArgumentException
    {
        if(parameters == null || parameters.length == 0 || !parameters[0].equalsIgnoreCase("borrower"))
        {
            throw new IllegalArgumentException("parameters are null, invalid or empty");
        }

        this.command = "/delete";
        this.parameters = parameters;
    }

    @Override
    public String getCommand()
    {
        return command;
    }

    @Override
    public String[] getParameters()
    {
        return parameters;
    }
}