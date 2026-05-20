package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.Borrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.*; // in this line you can see how the built Maven artifact should be detected correctly for the data layer.
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.LogicRequestDTO;

import java.nio.file.FileSystems;
import java.util.Date;

public class BorrowerRepository implements IBorrowerRepository
{
    @Override
    public ILogicRequestDTO createBorrower(int newestNumberFromJsons, String firstname, String lastname, Date dateOfBirth, IResidentialAddress residentialAddress, double schufaScore)
    {
        IBorrower borrower = null;
        String jsonFilepath = null;
        ILogicRequestDTO logicReqDTO = null;

        int currentBorrowerId = -1, newBorrowerId = -1;

        if(firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || dateOfBirth == null || residentialAddress == null)
        {
            System.out.println("Internal error: stopped BEFORE borrower creation due to argument which is null or invalid");

            return null;
        }

        currentBorrowerId = newestNumberFromJsons;

        newBorrowerId = currentBorrowerId + 1;

        try
        {
            borrower = new Borrower(newBorrowerId, firstname, lastname, dateOfBirth, residentialAddress, schufaScore, new int[]{});

            // jsonFilepath = borrowerDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + newBorrowerId + ".json";

            // Also save the created Borrower as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            logicReqDTO = new LogicRequestDTO<Borrower>(newBorrowerId + ".json", borrower, 0, Borrower.class);
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

        return logicReqDTO;
    }

    @Override
    public ILogicRequestDTO readBorrower(int[] jsonNumbers, int id)
    {
        /*
        try
        {
            numericJsonFiles = jsonFileService.listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            return null;
        }
         */

        for(int jsonNumber : jsonNumbers)
        {
            // filenameNumber = jsonFileService.getNumberFromJsonName(jsonFile);

            if(jsonNumber == id)
            {
                return new LogicRequestDTO<Borrower>(jsonNumber+".json", null, 1, Borrower.class);
            }

            /*
            if(filenameNumber.equalsIgnoreCase("" + id)) // Found that JSON file for the given Borrower.
            {
                borrower = jsonFileService.read(jsonFile.getAbsolutePath(), Borrower.class);

                return borrower;
            }
             */
        }

        return null;
    }

    @Override
    public ILogicRequestDTO[] readAllBorrowers(int[] jsonNumbers)
    {
        ILogicRequestDTO[] logicRequestDTOS = new ILogicRequestDTO[jsonNumbers.length];

        for(int i = 0; i < jsonNumbers.length; i++)
        {
            logicRequestDTOS[i] = new LogicRequestDTO<Borrower>(jsonNumbers[i]+".json", null, 1, Borrower.class);
        }

        return logicRequestDTOS;

        /*
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
         */
    }

    @Override
    public ILogicRequestDTO updateBorrower(IBorrower borrower)
    {
        return new LogicRequestDTO<Borrower>(borrower.getId()+".json", borrower, 0, Borrower.class);

        /*
        File[] borrowerFiles = null;
        String jsonFilename = null;

        try
        {
            borrowerFiles = jsonFileService.listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            // e.printStackTrace();

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
         */
    }

    @Override
    public ILogicRequestDTO deleteBorrower(int id)
    {
        return new LogicRequestDTO<Borrower>(id+".json", null, 2, Borrower.class);

        /*
        IBorrower borrower = null;

        borrower = readBorrower(id);

        if(borrower.getLoans().length == 0) // If no loan left to pay off, then delete the borrower (account) from the system.
        {
            String filepath = borrowerDirectoryFile.getAbsolutePath() + FileSystems.getDefault().getSeparator() + id + ".json";

            return jsonFileService.delete(filepath);
        }
        else
        {
            return false; // The corresponding borrower still has something to pay off.
        }
         */
    }
}
