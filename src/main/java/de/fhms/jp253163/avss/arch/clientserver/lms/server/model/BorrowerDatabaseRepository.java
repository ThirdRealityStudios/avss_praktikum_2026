package de.fhms.jp253163.avss.arch.clientserver.lms.server.model;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IResidentialAddress;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowerDatabaseRepository implements IBorrowerRepository
{
    private IJsonSQLService<Borrower> jsonSQLService;

    /**
     * Initializes the borrower repository.
     *
     * @param jsonSQLService
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public BorrowerDatabaseRepository(IJsonSQLService<Borrower> jsonSQLService) throws IllegalArgumentException
    {
        if(jsonSQLService == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.jsonSQLService = jsonSQLService;
    }

    @Override
    public IBorrower createBorrower(String firstname, String lastname, LocalDate dateOfBirth, IResidentialAddress residentialAddress, double schufaScore)
    {
        IBorrower borrower = null;

        int currentBorrowerId = -1, newBorrowerId = -1;

        if(firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || dateOfBirth == null || residentialAddress == null)
        {
            return null;
        }

        currentBorrowerId = jsonSQLService.getNewestNumberFromJsons(Borrower.class);

        if(currentBorrowerId == -2) // an error occurred while determining.
        {
            return null;
        }

        newBorrowerId = currentBorrowerId + 1;

        try
        {
            borrower = new Borrower(newBorrowerId, firstname, lastname, dateOfBirth, residentialAddress, schufaScore, new int[]{});

            // Also save the created Borrower as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            if(!jsonSQLService.save(newBorrowerId, borrower))
            {
                return null;
            }
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();

            return null;
        }

        return borrower;
    }

    @Override
    public IBorrower readBorrower(int id)
    {
        int[] allIDs = null;
        int foundBorrowerID = -1;
        IBorrower borrower = null;

        allIDs = jsonSQLService.listNumericJsonDatasets(Borrower.class);

        for(int currentID : allIDs)
        {
            foundBorrowerID = currentID;

            if(foundBorrowerID == id) // Found that JSON file for the given Borrower.
            {
                borrower = jsonSQLService.read(id, Borrower.class);

                return borrower;
            }
        }

        return null;
    }

    @Override
    public IBorrower[] readAllBorrowers()
    {
        ArrayList<Borrower> borrowers = new ArrayList<>();

        int[] numericJsonFiles = null;
        Borrower borrower = null;

        numericJsonFiles = jsonSQLService.listNumericJsonDatasets(Borrower.class);

        for(int jsonFile : numericJsonFiles)
        {
            borrower = jsonSQLService.read(jsonFile, Borrower.class);

            if(borrower == null)
            {
                continue;
            }

            borrowers.add(borrower);
        }

        return borrowers.toArray(new Borrower[0]);
    }

    @Override
    public boolean updateBorrower(IBorrower borrower)
    {
        int[] allIDs = jsonSQLService.listNumericJsonDatasets(Borrower.class);

        for(int currentID : allIDs)
        {
            if(currentID == borrower.getId())
            {
                // Borrower file found, so update it.

                return jsonSQLService.save(currentID, borrower);
            }
        }

        return false;
    }

    @Override
    public boolean deleteBorrower(int id)
    {
        IBorrower borrower = readBorrower(id);

        if(borrower == null)
            return false;

        if(borrower.getLoans().length == 0) // If no loan left to pay off, then delete the borrower (account) from the system.
        {
            return jsonSQLService.delete(id, Borrower.class);
        }
        else
        {
            return false; // The corresponding borrower still has something to pay off.
        }
    }

    public boolean isValidFirstnameForCreation(String firstname)
    {
        return firstname != null && !firstname.isEmpty();
    }

    public boolean isValidLastnameForCreation(String lastname)
    {
        return lastname != null && !lastname.isEmpty();
    }

    public boolean isValidDayOfBirthForCreation(int day)
    {
        return day > 0 && day < 32;
    }

    public boolean isValidMonthOfBirthForCreation(int month)
    {
        return month > 0 && month < 13;
    }

    public boolean isValidYearOfBirthForCreation(int year)
    {
        return year > 1900 && year < Integer.MAX_VALUE;
    }

    public boolean isValidStreetForCreation(String street)
    {
        return street != null && !street.isEmpty();
    }

    public boolean isValidNoForCreation(String no)
    {
        return no != null && !no.isEmpty();
    }

    public boolean isValidPlaceForCreation(String place)
    {
        return place != null && !place.isEmpty();
    }

    public boolean isValidZipForCreation(int zip)
    {
        return zip >= 0 && zip <= 99999;
    }

    public boolean isValidSchufaScoreForCreation(double schufaScore)
    {
        return schufaScore >= 0 && schufaScore <= 1;
    }

    public boolean isValidFirstnameForUpdate(String firstname)
    {
        return firstname == null || !firstname.isEmpty();
    }

    public boolean isValidLastnameForUpdate(String lastname)
    {
        return lastname == null || !lastname.isEmpty();
    }

    public boolean isValidDayOfBirthForUpdate(int day)
    {
        return day == -1 || day > 0 && day < 32;
    }

    public boolean isValidMonthOfBirthForUpdate(int month)
    {
        return month == -1 || month > 0 && month < 13;
    }

    public boolean isValidYearOfBirthForUpdate(int year)
    {
        return year == -1 || year > 1900 && year < Integer.MAX_VALUE;
    }

    public boolean isValidStreetForUpdate(String street)
    {
        return street == null || !street.isEmpty();
    }

    public boolean isValidNoForUpdate(String no)
    {
        return no == null || !no.isEmpty();
    }

    public boolean isValidPlaceForUpdate(String place)
    {
        return place == null || !place.isEmpty();
    }

    public boolean isValidZipForUpdate(int zip)
    {
        return zip == -1 || zip >= 0 && zip <= 99999;
    }

    public boolean isValidSchufaScoreForUpdate(double schufaScore)
    {
        return schufaScore == -1 || schufaScore >= 0 && schufaScore <= 1;
    }
}
