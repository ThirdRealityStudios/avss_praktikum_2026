package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.lms.application.data.ResidentialAddress;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class CommandCreateBorrower
{
    private IBorrowerRepository borrowerRepository;

    public CommandCreateBorrower(IBorrowerRepository borrowerRepository) throws IllegalArgumentException
    {
        if(borrowerRepository == null)
            throw new IllegalArgumentException("argument can't be null");

        this.borrowerRepository = borrowerRepository;
    }

    private Date readBirthday(Scanner scanner) throws IllegalArgumentException, ParseException
    {
        int year = 0, month = 0, day = 0;
        String yearText = null, monthText = null, dayText = null;
        String unparsedDateString = null;

        if(scanner == null)
            throw new IllegalArgumentException("argument must not be null!");

        do
        {
            System.out.print("Year of birth (at least 1910): ");
            yearText = scanner.nextLine();

            try
            {
                year = Integer.parseInt(yearText);
            }
            catch(NumberFormatException e)
            {
                year = -1;
            }
        }
        while(year < 1910);

        do
        {
            System.out.print("Month of birth (1 - 12): ");
            monthText = scanner.nextLine();

            try
            {
                month = Integer.parseInt(monthText);
            }
            catch(NumberFormatException e)
            {
                month = -1;
            }
        }
        while(month < 1 || month > 12);

        do
        {
            System.out.print("Day of birth (1 - 31): ");
            dayText = scanner.nextLine();

            try
            {
                day = Integer.parseInt(dayText);
            }
            catch(NumberFormatException e)
            {
                day = -1;
            }
        }
        while(day < 1 || day > 31);

        // Used help from here for Date parsing from a given format: https://www.baeldung.com/java-string-to-date

        unparsedDateString = String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        return DateUtils.parseDate(unparsedDateString, "yyyy-MM-dd");
    }

    private IResidentialAddress readResidentialAddress(Scanner scanner) throws IllegalArgumentException
    {
        String street = null, no = null, place = null;
        int zip = 0;
        String zipText = null;

        do
        {
            System.out.print("Street (max. 30 symbols): ");
            street = scanner.nextLine();
        }
        while(street.isEmpty() || street.length() > 30);

        do
        {
            System.out.print("No (max. 4 symbols): ");
            no = scanner.nextLine();
        }
        while(no.isEmpty() || no.length() > 4);

        do
        {
            System.out.print("Place (max. 30 symbols): ");
            place = scanner.nextLine();
        }
        while(place.isEmpty() || place.length() > 30);

        do
        {
            System.out.print("Zip code (exactly 5 symbols): ");
            zipText = scanner.nextLine();

            try
            {
                zip = Integer.parseInt(zipText);
            }
            catch(NumberFormatException e)
            {
                zip = -1;
            }
        }
        while(zip < 0 || zip > 99999);

        return new ResidentialAddress(street, no, zip, place);
    }

    public void run(Scanner scanner, String[] parameters)
    {
        IBorrower borrower = null;

        String firstname = null, lastname = null;

        Date dateOfBirth = null;

        IResidentialAddress residentialAddress = null;

        double schufaScore = 0;

        ILoan[] loans = null;

        if(parameters != null && parameters.length == 2)
        {
            do
            {
                System.out.print("Firstname (3 - 20 symbols): ");
                firstname = scanner.nextLine();
            }
            while(firstname == null || firstname.length() < 2 || firstname.length() > 20);

            do
            {
                System.out.print("Lastname (3 - 20 symbols): ");
                lastname = scanner.nextLine();
            }
            while(lastname == null || lastname.length() < 2 || lastname.length() > 20);

            try
            {
                dateOfBirth = readBirthday(scanner);
            }
            catch(ParseException e)
            {
                e.printStackTrace();

                return;
            }

            residentialAddress = readResidentialAddress(scanner);

            borrower = borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d);

            if(borrower != null)
                System.out.println("Created new borrower");
            else
                System.out.println("Could not create borrower!");
        }
    }
}
