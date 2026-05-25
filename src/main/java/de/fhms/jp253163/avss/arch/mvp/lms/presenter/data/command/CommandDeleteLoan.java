package de.fhms.jp253163.avss.arch.mvp.lms.presenter.data.command;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.command.ICommand;

public class CommandDeleteLoan implements ICommand
{
    private String command;
    private String[] parameters;

    /**
     * @param parameters First element is always expected to be "loan", followed by optional parameters.
     * @throws IllegalArgumentException
     */
    public CommandDeleteLoan(String[] parameters) throws IllegalArgumentException
    {
        if(parameters == null || parameters.length == 0 || !parameters[0].equalsIgnoreCase("loan"))
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