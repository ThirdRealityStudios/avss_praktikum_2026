package de.fhms.jp253163.avss.lms.frontend;

import java.util.Scanner;

public class LoanInputReader
{
    private Scanner scanner;

    public LoanInputReader(Scanner scanner) throws IllegalArgumentException
    {
        if(scanner == null)
            throw new IllegalArgumentException("argument can't be null");

        this.scanner = scanner;
    }

    public int readBorrower()
    {
        String borrower;
        int borrowerConverted;

        do
        {
            System.out.print("Borrower ID (at least zero): ");
            borrower = scanner.nextLine();

            try
            {
                borrowerConverted = Integer.parseInt(borrower);
            }
            catch(NumberFormatException e)
            {
                borrowerConverted = -1;
            }
        }
        while(borrowerConverted < 0);

        return borrowerConverted;
    }
}
