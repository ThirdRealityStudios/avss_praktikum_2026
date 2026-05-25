package de.fhms.jp253163.avss.arch.mvp.lms.view.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
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
        String number;
        double numberConverted;

        do
        {
            System.out.print(promptMessage);
            number = scanner.nextLine();

            try
            {
                numberConverted = Double.parseDouble(number);
            }
            catch(NumberFormatException e)
            {
                numberConverted = -1;
            }
        }
        while(numberConverted < min || numberConverted > max);

        return numberConverted;
    }

    public int readInteger(int min, int max, String promptMessage)
    {
        String number;
        int numberConverted;

        do
        {
            System.out.print(promptMessage);
            number = scanner.nextLine();

            try
            {
                numberConverted = Integer.parseInt(number);
            }
            catch(NumberFormatException e)
            {
                numberConverted = -1;
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
                if(scanner.hasNextLine())
                    stringRead = scanner.nextLine();
            }
            catch (Exception e)
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

        return LocalDate.of(year, month, day);
    }
}
