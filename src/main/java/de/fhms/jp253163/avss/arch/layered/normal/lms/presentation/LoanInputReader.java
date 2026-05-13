package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

public class LoanInputReader
{
    private InputReader inputReader;

    public LoanInputReader(InputReader inputReader) throws IllegalArgumentException
    {
        if(inputReader == null)
            throw new IllegalArgumentException("argument can't be null");

        this.inputReader = inputReader;
    }

    public int readBorrowerID(String promptMessage)
    {
        return inputReader.readInteger(0, Integer.MAX_VALUE, "Borrower ID (at least zero): ");
    }

    public double readAmount()
    {
        return inputReader.readDouble(0.01, Double.MAX_VALUE, "Loan amount: ");
    }

    public double readInterestRate()
    {
        return inputReader.readDouble(0.001, 1, "Interest rate (0.0001 - 1, meaning 0,01% to 100%): ");
    }

    public int readTerm()
    {
        return inputReader.readInteger(1, Integer.MAX_VALUE, "Term of loan (unit: in months): ");
    }
}
