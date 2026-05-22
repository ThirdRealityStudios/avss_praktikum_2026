package de.fhms.jp253163.avss.arch.mvp.lms.view.data.command;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.command.ICommand;

public class CommandCreateLoan implements ICommand
{
    private String command;
    private String[] parameters;

    /**
     * @param parameters First element is always expected to be "loan", followed by optional parameters.
     * @throws IllegalArgumentException
     */
    public CommandCreateLoan(String[] parameters) throws IllegalArgumentException
    {
        if(parameters == null || parameters.length == 0 || !parameters[0].equalsIgnoreCase("borrower"))
        {
            throw new IllegalArgumentException("parameters are null, invalid or empty");
        }

        this.command = "/create";
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

    /*
    public void run(String[] parameters)
    {
        ILoan loan = null;

        int borrowerId = -1, term = -1;
        double amount = -1, interestRate = -1;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            borrowerId = loanInputReader.readBorrowerID("Borrower ID (at least zero): ");

            amount = loanInputReader.readAmount();

            interestRate = loanInputReader.readInterestRate();

            term = loanInputReader.readTerm();

            loan = loanRepository.createLoan(borrowerId, amount, interestRate, term);

            if(loan != null)
                System.out.println("Created new loan");
            else
                System.out.println("Could not create loan!");
        }
    }
     */
}
