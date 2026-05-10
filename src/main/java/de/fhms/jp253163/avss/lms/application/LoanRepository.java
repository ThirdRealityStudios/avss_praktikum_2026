package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.Loan;
import de.fhms.jp253163.avss.lms.data.IJsonFileService;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;

public class LoanRepository implements ILoanRepository
{
    private IJsonFileService<Loan> jsonFileService;
    private File loanDirectoryFile;
    private ISchufaRepository schufaRepository;
    private IBorrowerRepository borrowerRepository;

    /**
     * Initializes the loan repository,
     * including the creation of the "loan" folder in the executable path if not existing yet.
     *
     * @param jsonFileService
     * @param executableDirectory
     * @param schufaRepository
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public LoanRepository(IJsonFileService<Loan> jsonFileService, File executableDirectory, ISchufaRepository schufaRepository, IBorrowerRepository borrowerRepository) throws IllegalArgumentException, IOException
    {
        if (jsonFileService == null || executableDirectory == null || schufaRepository == null || borrowerRepository == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.jsonFileService = jsonFileService;
        this.schufaRepository = schufaRepository;
        this.borrowerRepository = borrowerRepository;

        // To locate the directory where all Borrower objects are stored.
        String loanPath = executableDirectory.getAbsolutePath() + FileSystems.getDefault().getSeparator() + "loan";

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
    public ILoan createLoan(int borrowerID, double amount, double interestRate, int term)
    {
        ILoan loan = null;
        IBorrower borrower = null;
        int[] currentLoans = null;
        String jsonFilepath = null;

        int currentLoadId = -1, newLoanId = -1;
        double schufaScore = schufaRepository.readSchufaScore(borrowerID);

        borrower = this.borrowerRepository.readBorrower(borrowerID);

        if(borrower == null)
            return null; // the borrower account does not exist (yet), so loan can't be given (loan denied).

        try
        {
            currentLoadId = jsonFileService.getNewestNumberFromJsons(this.loanDirectoryFile);

            if(currentLoadId == -2) // an error occurred while determining
            {
                System.out.println("COULD NOT LOAD NEWEST JSON NUMBER (1)!");
                return null;
            }

            newLoanId = currentLoadId + 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            System.out.println("COULD NOT LOAD NEWEST JSON NUMBER (2)!");
            return null;
        }

        try
        {
            // Making conditions when a customer is allowed to get a loan.
            if(amount <= 1000 && schufaScore >= 0.90d || amount > 1000 && schufaScore >= 0.95d)
            {
                loan = new Loan(newLoanId, amount, interestRate, term, borrowerID);
            }
            else
            {
                System.out.println("Loan denied (missing creditworthiness)");
                return null;
            }

            jsonFilepath = loanDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + newLoanId + ".json";

            // Also save the created Loan as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            jsonFileService.save(jsonFilepath, loan);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("COULD NOT CREATE OBJECT! CONSTRUCTOR VALUES CORRUPT (2)");
            return null;
        }

        currentLoans = borrower.getLoans();

        if(currentLoans == null)
            return null;

        if(ArrayUtils.indexOf(currentLoans, newLoanId) < 0) // check if the given loan is yet known to the borrower (normally shouldn't but never know)
        {
            currentLoans = ArrayUtils.add(currentLoans, newLoanId);

            borrower.setLoans(currentLoans);

            if(!borrowerRepository.updateBorrower(borrower))
            {
                return null; // Could not add the given loan to the borrower in the system.
            }
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

        ILoan oldLoan = null;
        IBorrower oldBorrower = null;
        IBorrower newBorrower = null;

        oldLoan = readLoan(loan.getId());

        if(oldLoan == null)
            return false; // Loan not known to the system, so update denied.

        oldBorrower = borrowerRepository.readBorrower(oldLoan.getBorrower());

        if(oldBorrower == null)
            return false; // Borrower of the loan can't be found, so update denied.

        if(loan.getBorrower() != oldLoan.getBorrower())
        {
            // See if the new borrower even exists (e.g. if your dad wants to pay your loan instead - but he needs to be known to the system first)
            newBorrower = borrowerRepository.readBorrower(loan.getId());

            if(newBorrower == null)
                return false; // New borrower does not exist in the system, so update denied.

            // From this point, all borrowers do exist in the system or are valid.

            if(!oldBorrower.setLoans( ArrayUtils.removeAllOccurrences(oldBorrower.getLoans(), loan.getId()) ))
            {
                return false; // Could not remove the loan from the old borrower; in order to give the payment responsibility to a new borrower.
            }

            if(!borrowerRepository.updateBorrower(oldBorrower))
            {
                return false; // Could not update the old borrower, persistently seen.
            }

            if(!newBorrower.setLoans( ArrayUtils.add(newBorrower.getLoans(), loan.getId()) ))
            {
                return false; // Could not add the loan to the new borrower (which should pay the loan instead of the old borrower).
            }

            if(!borrowerRepository.updateBorrower(newBorrower))
            {
                return false; // Could not physically or persistently write the changes to the hard-disk.
            }
        }

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
        ILoan loan = null;
        IBorrower borrower = null;

        loan = readLoan(id);

        if(loan == null)
            return false; // Loan not found in the system. Is this an error?

        if(loan.getRemainingBalance() < 0.01) // It is legitimate to delete the loan (paid off)
        {
            borrower = borrowerRepository.readBorrower(loan.getBorrower());

            if(borrower == null)
                return false; // For some reason cannot find the borrower from the loan in the system, so deleting failed..

            if(!borrower.setLoans( ArrayUtils.removeAllOccurrences(borrower.getLoans(), id) ))
            {
                return false; // Could not remove the loan from the borrower for some reason.
            }

            if(!borrowerRepository.updateBorrower(borrower))
            {
                return false; // Could not write the changes on hard-disk for the removed loan from the borrower.
            }

            String filepath = loanDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + id + ".json";

            return jsonFileService.delete(filepath);
        }
        else
        {
            return false; // Still something left pay off from the borrower (at least 1 cent).
        }
    }
}
