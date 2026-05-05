package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.Borrower;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.Loan;
import de.fhms.jp253163.avss.lms.data.JsonFileService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        ILoan loan = null;
        String jsonFilepath = null;

        int newLoanId = -1;

        try
        {
            newLoanId = jsonFileService.getNewestNumberFromJsons(this.loanDirectoryFile) + 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }

        try
        {
            loan = new Loan(newLoanId, amount, interestRate, term, borrower);

            jsonFilepath = loanDirectoryFile.getAbsolutePath() + File.pathSeparator + newLoanId + ".json";

            // Also save the created Loan as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            jsonFileService.save(jsonFilepath, loan);
        }
        catch(IllegalArgumentException e)
        {
            return null;
        }

        return loan;
    }

    @Override
    public ILoan readLoan(int id)
    {
        File[] numericJsonFiles = null;
        String filenameNumber = null;
        ILoan loan = null;

        try
        {
            numericJsonFiles = jsonFileService.listNumericJsonFiles(loanDirectoryFile);
        }
        catch(IOException e)
        {
            return null;
        }

        for(File jsonFile : numericJsonFiles)
        {
            filenameNumber = jsonFileService.getNumberFromJsonName(jsonFile);

            if(filenameNumber.equalsIgnoreCase("" + id)) // Found that JSON file for the given Loan.
            {
                loan = jsonFileService.read(jsonFile.getAbsolutePath(), Loan.class);

                return loan;
            }
        }

        return null;
    }

    @Override
    public ILoan[] readAllLoans()
    {
        ArrayList<Loan> loans = new ArrayList<>();

        File[] numericJsonFiles = null;
        Loan loan = null;

        try
        {
            numericJsonFiles = jsonFileService.listNumericJsonFiles(loanDirectoryFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return new ILoan[0];
        }

        for(File jsonFile : numericJsonFiles)
        {
            loan = jsonFileService.read(jsonFile.getAbsolutePath(), Loan.class);

            if(loan == null)
            {
                continue;
            }

            loans.add(loan);
        }

        return loans.toArray(new Loan[0]);
    }

    @Override
    public boolean updateLoan(ILoan loan)
    {
        File[] loanFiles = null;
        String jsonFilename = null;

        try
        {
            loanFiles = jsonFileService.listNumericJsonFiles(loanDirectoryFile);
        }
        catch(IOException e)
        {
            // e.printStackTrace();

            return false;
        }

        for(File jsonFile : loanFiles)
        {
            jsonFilename = jsonFileService.getNumberFromJsonName(jsonFile);

            if(jsonFilename == null)
            {
                continue;
            }

            if(jsonFilename.equalsIgnoreCase(loan.getId() + ""))
            {
                // Loan file found, so update it.

                return jsonFileService.save(jsonFile.getAbsolutePath(), loan);
            }
        }

        return false;
    }

    @Override
    public boolean deleteLoan(int id)
    {
        String filepath = loanDirectoryFile.getAbsolutePath() + File.pathSeparator + id + ".json";

        return jsonFileService.delete(filepath);
    }
}
