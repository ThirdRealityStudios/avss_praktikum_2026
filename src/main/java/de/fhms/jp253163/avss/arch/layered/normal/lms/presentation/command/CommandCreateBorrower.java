package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.command;

import de.fhms.jp253163.avss.arch.layered.normal.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.ILoan;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.reader.BorrowerInputReader;

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

    public IBorrower run(String[] parameters)
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

            dateOfBirth = borrowerInputReader.readBirthday();

            if(dateOfBirth == null)
                return null;

            residentialAddress = borrowerInputReader.readResidentialAddress();
            borrower = borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d);

            if(borrower != null)
            {
                System.out.println("Created new borrower");

                return borrower;
            }
            else
            {
                System.out.println("Could not create borrower!");

                return null;
            }
        }

        return null;
    }
}
