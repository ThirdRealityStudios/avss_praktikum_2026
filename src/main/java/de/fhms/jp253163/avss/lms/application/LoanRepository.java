package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.Loan;
import de.fhms.jp253163.avss.lms.data.JsonFileService;

import java.io.File;
import java.io.IOException;

public class LoanRepository implements ILoanRepository
{
    private JsonFileService<Loan> jsonFileService;
    private File loanDirectoryFile;

    /**
     * Initializes the loan repository,
     * including the creation of the "loan" folder in the executable path if not existing yet.
     *
     * @param jsonFileService
     * @param executableDirectory
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public LoanRepository(JsonFileService<Loan> jsonFileService, File executableDirectory) throws IllegalArgumentException, IOException
    {
        if (jsonFileService == null || executableDirectory == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.jsonFileService = jsonFileService;

        // To locate the directory where all Borrower objects are stored.
        String loanPath = executableDirectory.getAbsolutePath() + File.pathSeparator + "loan";

        loanDirectoryFile = new File(loanPath);

        if(!loanDirectoryFile.exists())
        {
            if(!loanDirectoryFile.mkdir())
            {
                throw new IOException("could not create the directory to store all JSON files");
            }
        }
    }

    @Override
    public ILoan createLoan(IBorrower borrower, double amount, double interestRate, int term)
    {
        return null;
    }

    @Override
    public ILoan readLoanById(int id)
    {
        return null;
    }

    @Override
    public ILoan[] readAllLoans()
    {
        return new ILoan[0];
    }

    @Override
    public boolean updateLoan(ILoan loan)
    {
        return false;
    }

    @Override
    public boolean deleteLoan(int id)
    {
        return false;
    }
}
