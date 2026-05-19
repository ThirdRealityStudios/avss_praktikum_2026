package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.command;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.TerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.reader.BorrowerInputReader;

import java.util.Date;

public class CommandCreateBorrower
{
    private BorrowerInputReader borrowerInputReader;

    public CommandCreateBorrower(BorrowerInputReader borrowerInputReader) throws IllegalArgumentException
    {
        if(borrowerInputReader == null)
        {
            throw new IllegalArgumentException("at least one argument is null!");
        }

        this.borrowerInputReader = borrowerInputReader;
    }

    public ITerminalRequestDTO run(String[] parameters)
    {
        ITerminalRequestDTO termReqDTO = null;

        String firstname = null, lastname = null;

        Date dateOfBirth = null;

        String[] residentialAddress = null;

        if(parameters != null && parameters.length == 2)
        {
            firstname = borrowerInputReader.readFirstname();
            lastname = borrowerInputReader.readLastname();

            dateOfBirth = borrowerInputReader.readBirthday();

            if(dateOfBirth == null)
                return null;

            residentialAddress = borrowerInputReader.readResidentialAddress();

            try
            {
                termReqDTO = new TerminalRequestDTO(new long[]{dateOfBirth.getTime()}, new double[]{0.9}, new String[]{"borrower", firstname, lastname, residentialAddress[0], residentialAddress[1], residentialAddress[2], residentialAddress[3]}, 0);

                System.out.println("Creating new borrower..");

                return termReqDTO;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Could not create borrower!");

                return null;
            }
        }

        return null;
    }
}
