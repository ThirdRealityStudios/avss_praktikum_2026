package de.fhms.jp253163.avss.arch.mvp.lms.view.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class InputReader
{
    private Scanner scanner = null;

    public InputReader(Scanner scanner)
    {
        if(scanner == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.scanner = scanner;
    }

    public double readDouble(double min, double max, String promptMessage)
    {
        String number = null;
        double numberConverted = -1;

        do
        {
            System.out.print(promptMessage);

            try
            {
                if(scanner.hasNextLine())
                    number = scanner.nextLine();

                if(number == null)
                    continue;

                numberConverted = Double.parseDouble(number);
            }
            catch(Exception e)
            {
                return -1;
            }
        }
        while(numberConverted < min || numberConverted > max);

        return numberConverted;
    }

    public int readInteger(int min, int max, String promptMessage)
    {
        String number = null;
        int numberConverted = -1;

        do
        {
            System.out.print(promptMessage);

            try
            {
                if(scanner.hasNextLine())
                    number = scanner.nextLine();

                if(number == null)
                    continue;

                numberConverted = Integer.parseInt(number);
            }
            catch(Exception e)
            {
                return -1;
            }
        }
        while(numberConverted < min || numberConverted > max);

        return numberConverted;
    }

    public String readString(int minLength, int maxLength, String promptMessage)
    {
        String stringRead = null;

        do
        {
            System.out.print(promptMessage);

            try
            {
                if(scanner.hasNextLine()) // hasNextLine() blocks the program flow to await an user-input
                    stringRead = scanner.nextLine(); // then nextLine() will read from the stream the whole line
            }
            catch(Exception e)
            {

            }
        }
        while(stringRead == null || stringRead.length() < minLength || stringRead.length() > maxLength);

        return stringRead;
    }

    /**
     * @param promptMessageYear Year. At least 1910. Max is 2999.
     * @param promptMessageMonth Month. 1 to 12.
     * @param promptMessageDay Day. 1 to 31.
     * @return LocalDate object.
     * @throws DateTimeException
     */
    public LocalDate readDate(String promptMessageYear, String promptMessageMonth, String promptMessageDay) throws DateTimeException
    {
        int year, month, day;

        year = readInteger(1910, 2999, promptMessageYear);

        month = readInteger(1, 12, promptMessageMonth);

        day = readInteger(1, 31, promptMessageDay);

        LocalDate date = null;

        try
        {
            date = LocalDate.of(year, month, day);

            return date;
        }
        catch (DateTimeException e)
        {
            return null;
        }
    }
}
