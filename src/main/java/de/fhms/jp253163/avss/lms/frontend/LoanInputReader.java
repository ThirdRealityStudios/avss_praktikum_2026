package de.fhms.jp253163.avss.lms.frontend;

import java.util.Scanner;

public class LoanInputReader
{
    private InputReader inputReader;

    public LoanInputReader(InputReader inputReader) throws IllegalArgumentException
    {
        if(inputReader == null)
            throw new IllegalArgumentException("argument can't be null");

        this.inputReader = inputReader;
    }

    public double readAmount()
    {
        return inputReader.readDouble(0.01, Double.MAX_VALUE, "Loan amount: ");
    }

    public double readBorrowerID(String promptMessage)
    {
        return inputReader.readInteger(0, Integer.MAX_VALUE, "Borrower ID (at least zero): ");
    }
}
