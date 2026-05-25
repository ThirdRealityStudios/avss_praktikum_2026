package de.fhms.jp253163.avss.arch.mvp.lms.presenter.data.command;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.command.ICommand;

public class CommandCreateBorrower implements ICommand
{
    private String command;
    private String[] parameters;

    /**
     * @param parameters First element is always expected to be "borrower", followed by optional parameters.
     * @throws IllegalArgumentException
     */
    public CommandCreateBorrower(String[] parameters) throws IllegalArgumentException
    {
        if(parameters == null || parameters.length == 0 || !parameters[0].equalsIgnoreCase("borrower"))
        {
            throw new IllegalArgumentException("parameters are null, invalid or empty");
        }

        this.command = "/create";
        this.parameters = parameters;
    }

    /*
    public void run(String[] parameters)
    {
        String firstname = null, lastname = null;

        Date dateOfBirth = null;

        if(parameters != null && parameters.length == 2)
        {
            firstname = borrowerInputReader.readFirstname();
            lastname = borrowerInputReader.readLastname();

            dateOfBirth = borrowerInputReader.readBirthday();

            if(dateOfBirth == null)
                return;

            residentialAddress = borrowerInputReader.readResidentialAddress();
            borrower = borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d);

            if(borrower != null)
                System.out.println("Created new borrower");
            else
                System.out.println("Could not create borrower!");
        }
    }
     */

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
