package de.fhms.jp253163.avss.arch.mvp.lms.model;

import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.IJsonFileService;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.IJsonSQLService;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.Borrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowerDatabaseRepository implements IBorrowerRepository
{
    private IJsonSQLService<Borrower> jsonSQLService;

    /**
     * Initializes the loan repository.
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
        String jsonFilepath = null;

        int currentBorrowerId = -1, newBorrowerId = -1;

        if(firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || dateOfBirth == null || residentialAddress == null)
        {
            System.out.println("Internal error: stopped BEFORE borrower creation due to argument which is null or invalid");

            return null;
        }

        currentBorrowerId = jsonSQLService.getNewestNumberFromJsons(Borrower.class);

        System.out.println("Detected " + currentBorrowerId + " for current highest JSON file!");

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
                System.out.println("Cannot save borrower as a JSON file!");

                return null;
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Invalid arguments for borrower!");
            System.out.println("The following arguments have been transmitted:");
            System.out.println();
            System.out.println("ID: " + newBorrowerId);
            System.out.println("Firstname: " + firstname);
            System.out.println("Lastname: " + lastname);
            System.out.println("Birthday: " + dateOfBirth);
            System.out.println("SchufaScore: " + schufaScore);
            System.out.println("Street: " + residentialAddress.getStreet());
            System.out.println("No: " + residentialAddress.getNo());
            System.out.println("Place: " + residentialAddress.getPlace());
            System.out.println("Zip: " + residentialAddress.getZip());
            System.out.println();

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
