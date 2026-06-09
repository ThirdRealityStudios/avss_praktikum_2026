package de.fhms.jp253163.avss.arch.clientserver.lms.server.model;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.data.IJsonSQLService;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Loan;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;

public class LoanDatabaseRepository implements ILoanRepository
{
    private IJsonSQLService<Loan> jsonSQLService;
    private ISchufaRepository schufaRepository;
    private IBorrowerRepository borrowerRepository;

    /**
     * Initializes the loan repository.
     *
     * @param jsonSQLService
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public LoanDatabaseRepository(IJsonSQLService<Loan> jsonSQLService, ISchufaRepository schufaRepository, IBorrowerRepository borrowerRepository) throws IllegalArgumentException
    {
        if(jsonSQLService == null || schufaRepository == null || borrowerRepository == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.jsonSQLService = jsonSQLService;
        this.schufaRepository = schufaRepository;
        this.borrowerRepository = borrowerRepository;
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

        currentLoadId = jsonSQLService.getNewestNumberFromJsons(Loan.class);

        if(currentLoadId == -2) // an error occurred while determining
        {
            return null;
        }

        newLoanId = currentLoadId + 1;

        try
        {
            // Making conditions when a customer is allowed to get a loan.
            if(amount <= 1000 && schufaScore >= 0.90d || amount > 1000 && schufaScore >= 0.95d)
            {
                loan = new Loan(newLoanId, amount, interestRate, term, borrowerID);
            }
            else
            {
                // Loan denied (missing creditworthiness)
                return null;
            }

            // Also save the created Loan as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            jsonSQLService.save(newLoanId, loan);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();

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
        int[] allIDs = null;
        ILoan loan = null;

        allIDs = jsonSQLService.listNumericJsonDatasets(Loan.class);

        for(int currentID : allIDs)
        {
            if(currentID == id) // Found that JSON file for the given Loan.
            {
                loan = jsonSQLService.read(id, Loan.class);

                return loan;
            }
        }

        return null;
    }

    @Override
    public ILoan[] readAllLoans()
    {
        ArrayList<Loan> loans = new ArrayList<>();

        int[] allIDs = null;
        Loan loan = null;

        allIDs = jsonSQLService.listNumericJsonDatasets(Loan.class);

        for(int currentID : allIDs)
        {
            loan = jsonSQLService.read(currentID, Loan.class);

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
        int[] allLoanIDs = null;

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
            newBorrower = borrowerRepository.readBorrower(loan.getBorrower());

            if(newBorrower == null)
                return false; // New borrower does not exist in the system, so update denied.

            // From this point, all borrowers do exist in the system or are valid.

            // Take away the loan from the old current borrower
            if(!oldBorrower.setLoans( ArrayUtils.removeAllOccurrences(oldBorrower.getLoans(), loan.getId()) ))
            {
                return false; // Could not remove the loan from the old borrower; in order to give the payment responsibility to a new borrower.
            }

            if(!borrowerRepository.updateBorrower(oldBorrower))
            {
                return false; // Could not update the old borrower, persistently seen.
            }

            // Then give the taken away loan to the actual new borrower (who should pay instead)
            if(!newBorrower.setLoans( ArrayUtils.add(newBorrower.getLoans(), loan.getId()) ))
            {
                return false; // Could not add the loan to the new borrower (which should pay the loan instead of the old borrower).
            }

            // Save again
            if(!borrowerRepository.updateBorrower(newBorrower))
            {
                return false; // Could not physically or persistently write the changes to the hard-disk.
            }
        }

        allLoanIDs = jsonSQLService.listNumericJsonDatasets(Loan.class);

        for(int currentID : allLoanIDs)
        {
            if(currentID == loan.getId())
            {
                // Loan file found, so update it.

                return jsonSQLService.save(currentID, loan);
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
        {
            System.out.println("Model: No loan exists to delete with ID: " + id);
            System.out.println("Model: Hence, exiting... (returning 'unsuccessful')");
            return false; // Loan not found in the system. Is this an error?
        }

        System.out.println("Model: FOUND loan to delete with ID: " + id);

        if(loan.getRemainingBalance() < 0.01) // It is legitimate to delete the loan (paid off)
        {
            System.out.println("Model: Loan paid off.");

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

            return jsonSQLService.delete(id, Loan.class);
        }
        else
        {
            System.out.println("Model: Loan has still something to pay off.");
            return false; // Still something left pay off from the borrower (at least 1 cent).
        }
    }

    @Override
    public boolean isValidBorrowerIDForCreation(int borrowerID) {
        return borrowerID >= 0;
    }

    @Override
    public boolean isValidAmountForCreation(double amount) {
        return amount >= 0.01;
    }

    @Override
    public boolean isValidInterestRateForCreation(double interestRate) {
        return interestRate >= 0.0001;
    }

    @Override
    public boolean isValidTermForCreation(int term) {
        return term > 0;
    }

    @Override
    public boolean isValidBorrowerIDForUpdate(int borrowerID) {
        return borrowerID == -1 || borrowerID >= 0;
    }
}
