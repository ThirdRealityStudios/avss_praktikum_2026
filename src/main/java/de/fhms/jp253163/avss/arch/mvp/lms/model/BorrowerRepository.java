package de.fhms.jp253163.avss.arch.mvp.lms.model;

import de.fhms.jp253163.avss.arch.mvp.lms.model.data.Borrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.*; // in this line you can see how the built Maven artifact should be detected correctly for the data layer.
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BorrowerRepository implements IBorrowerRepository
{
    private IJsonFileService<Borrower> jsonFileService;
    private File borrowerDirectoryFile;

    /**
     * Initializes the loan repository,
     * including the creation of the "loan" folder in the executable path if not existing yet.
     *
     * @param jsonFileService
     * @param executableDirectory
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public BorrowerRepository(IJsonFileService<Borrower> jsonFileService, File executableDirectory) throws IllegalArgumentException, IOException
    {
        if(jsonFileService == null || executableDirectory == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.jsonFileService = jsonFileService;

        if(!executableDirectory.exists() || !executableDirectory.isDirectory())
            throw new IllegalArgumentException("given executable path does not exist or is not a directory");

        // To locate the directory where all Borrower objects are stored.
        String borrowerPath = executableDirectory.getAbsolutePath() + FileSystems.getDefault().getSeparator() + "borrower";

        borrowerDirectoryFile = new File(borrowerPath);

        /**
         * Create the directory to store all borrower JSON files if not done yet..
         */
        if(!borrowerDirectoryFile.exists())
        {
            if(!borrowerDirectoryFile.mkdir())
            {
                throw new IOException("could not create the directory to store all JSON files");
            }
        }
    }

    @Override
    public IBorrower createBorrower(String firstname, String lastname, LocalDate dateOfBirth, IResidentialAddress residentialAddress, double schufaScore)
    {
        IBorrower borrower = null;
        String jsonFilepath = null;

        int currentBorrowerId = -1, newBorrowerId = -1;

        if(firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || dateOfBirth == null || residentialAddress == null)
        {
            return null;
        }

        try
        {
            currentBorrowerId = jsonFileService.getNewestNumberFromJsons(this.borrowerDirectoryFile);

            if(currentBorrowerId == -2) // an error occurred while determining.
            {
                return null;
            }

            newBorrowerId = currentBorrowerId + 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }

        try
        {
            borrower = new Borrower(newBorrowerId, firstname, lastname, dateOfBirth, residentialAddress, schufaScore, new int[]{});

            jsonFilepath = borrowerDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + newBorrowerId + ".json";

            // Also save the created Borrower as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            if(!jsonFileService.save(jsonFilepath, borrower))
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
        File[] numericJsonFiles = null;
        String filenameNumber = null;
        IBorrower borrower = null;

        try
        {
            numericJsonFiles = jsonFileService.listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }

        for(File jsonFile : numericJsonFiles)
        {
            filenameNumber = jsonFileService.getNumberFromJsonName(jsonFile);

            if(filenameNumber.equalsIgnoreCase("" + id)) // Found that JSON file for the given Borrower.
            {
                borrower = jsonFileService.read(jsonFile.getAbsolutePath(), Borrower.class);

                return borrower;
            }
        }

        return null;
    }

    @Override
    public IBorrower[] readAllBorrowers()
    {
        ArrayList<Borrower> borrowers = new ArrayList<>();

        File[] numericJsonFiles = null;
        Borrower borrower = null;

        try
        {
            numericJsonFiles = jsonFileService.listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return new IBorrower[0];
        }

        for(File jsonFile : numericJsonFiles)
        {
            borrower = jsonFileService.read(jsonFile.getAbsolutePath(), Borrower.class);

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
        File[] borrowerFiles = null;
        String jsonFilename = null;

        try
        {
            borrowerFiles = jsonFileService.listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return false;
        }

        for(File jsonFile : borrowerFiles)
        {
            jsonFilename = jsonFileService.getNumberFromJsonName(jsonFile);

            if(jsonFilename == null)
            {
                continue;
            }

            if(jsonFilename.equalsIgnoreCase(borrower.getId()+""))
            {
                // Borrower file found, so update it.

                return jsonFileService.save(jsonFile.getAbsolutePath(), borrower);
            }
        }

        return false;
    }

    @Override
    public boolean deleteBorrower(int id)
    {
        IBorrower borrower = null;

        borrower = readBorrower(id);

        if(borrower == null)
            return false;

        if(borrower.getLoans().length == 0) // If no loan left to pay off, then delete the borrower (account) from the system.
        {
            String filepath = borrowerDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + id + ".json";

            return jsonFileService.delete(filepath);
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
