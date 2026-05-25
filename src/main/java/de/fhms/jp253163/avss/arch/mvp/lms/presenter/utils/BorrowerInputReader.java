package de.fhms.jp253163.avss.arch.mvp.lms.presenter.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowerInputReader
{
    private InputReader inputReader;

    public BorrowerInputReader(InputReader inputReader) throws IllegalArgumentException
    {
        if(inputReader == null)
            throw new IllegalArgumentException("argument can't be null");

        this.inputReader = inputReader;
    }

    public String readFirstname()
    {
        return inputReader.readString(3, 20, "Firstname (3 - 20 symbols): ");
    }

    public String readLastname()
    {
        return inputReader.readString(3, 20, "Lastname (3 - 20 symbols): ");
    }

    /**
     * Reads a birthday via prompt,
     * in the order year, month and day.
     *
     * @return Date object. Null if an internal error occurred.
     */
    public LocalDate readBirthday()
    {
        try
        {
            return inputReader.readDate("Year of birth (at least 1910): ", "Month of birth (1 - 12): ", "Day of birth (1 - 31): ");
        }
        catch(DateTimeException e)
        {
            return null;
        }
    }

    public String readStreet()
    {
        return inputReader.readString(1, 30, "Street (max. 30 symbols): ");
    }

    public String readNo()
    {
        return inputReader.readString(1, 4, "No (max. 4 symbols): ");
    }

    public String readPlace()
    {
        return inputReader.readString(1, 30, "Place (max. 30 symbols): ");
    }

    public int readZip()
    {
        return inputReader.readInteger(0, 99999, "Zip code (max. 5 numbers): ");
    }

    public int[] readLoans()
    {
        ArrayList<Integer> loans = new ArrayList<>();
        int[] loansArray;

        int loanID, loanAmount, index = 0;

        loanAmount = inputReader.readInteger(0, Integer.MAX_VALUE, "How many loans do you wish? (0 to abort): ");

        if(loanAmount == 0)
            return null;

        while(true)
        {
            loanID = inputReader.readInteger(0, Integer.MAX_VALUE, (index + 1) + ". loan ID (at least zero): ");

            if(!loans.contains(loanID))
            {
                loans.add(loanID);

                loanAmount--;
                index++;

                if(loanAmount == 0)
                    break;
            }
        }

        loansArray = new int[loans.size()];

        for(int i = 0; i < loans.size(); i++)
        {
            loansArray[i] = loans.get(i);
        }

        return loansArray;
    }
}
