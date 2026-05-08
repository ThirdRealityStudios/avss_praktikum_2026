package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class CommandCreateBorrower
{
    private IBorrowerRepository borrowerRepository;
    private BorrowerInputReader borrowerInputReader;
    private Scanner scanner;

    public CommandCreateBorrower(IBorrowerRepository borrowerRepository, BorrowerInputReader borrowerInputReader, Scanner scanner) throws IllegalArgumentException
    {
        if(borrowerRepository == null || borrowerInputReader == null || scanner == null)
        {
            throw new IllegalArgumentException("at least one argument is null!");
        }

        this.borrowerRepository = borrowerRepository;
        this.borrowerInputReader = borrowerInputReader;
        this.scanner = scanner;
    }

    public void run(String[] parameters)
    {
        IBorrower borrower = null;

        String firstname = null, lastname = null;

        Date dateOfBirth = null;

        IResidentialAddress residentialAddress = null;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            firstname = borrowerInputReader.readFirstname();
            lastname = borrowerInputReader.readLastname();

            try
            {
                dateOfBirth = borrowerInputReader.readBirthday();
            }
            catch(ParseException e)
            {
                e.printStackTrace();

                return;
            }

            residentialAddress = borrowerInputReader.readResidentialAddress();
            borrower = borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d);

            if(borrower != null)
                System.out.println("Created new borrower");
            else
                System.out.println("Could not create borrower!");
        }
    }
}
