package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.command;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.TerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.reader.LoanInputReader;

public class CommandCreateLoan
{
    private LoanInputReader loanInputReader;

    public CommandCreateLoan(LoanInputReader loanInputReader) throws IllegalArgumentException
    {
        if(loanInputReader == null)
        {
            throw new IllegalArgumentException("at least one argument is null!");
        }

        this.loanInputReader = loanInputReader;
    }

    public ITerminalRequestDTO run(String[] parameters)
    {
        int borrowerId = -1, term = -1;
        double amount = -1, interestRate = -1;

        ITerminalRequestDTO termReqDTO = null;

        if(parameters != null && parameters.length == 2)
        {
            borrowerId = loanInputReader.readBorrowerID("Borrower ID (at least zero): ");

            amount = loanInputReader.readAmount();

            interestRate = loanInputReader.readInterestRate();

            term = loanInputReader.readTerm();

            try
            {
                termReqDTO = new TerminalRequestDTO(new long[]{borrowerId, term}, new double[]{amount, interestRate}, new String[]{"loan"}, 0);

                System.out.println("Creating new loan...");

                return termReqDTO;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Could not create loan!");

                return null;
            }
        }

        return null;
    }
}
