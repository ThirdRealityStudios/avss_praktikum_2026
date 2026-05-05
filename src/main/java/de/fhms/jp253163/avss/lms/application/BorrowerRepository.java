package de.fhms.jp253163.avss.lms.application;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.lms.application.data.Borrower;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.lms.data.*; // in this line you can see how the built Maven artifact should be detected correctly for the data layer.
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BorrowerRepository implements IBorrowerRepository
{
    private GsonBuilder gsonBuilder;
    private JsonFileService<Borrower> jsonFileService;
    private File borrowerDirectoryFile;

    public BorrowerRepository(GsonBuilder gsonBuilder, String executableDirectory) throws IllegalArgumentException, IOException
    {
        if(gsonBuilder == null)
            throw new IllegalArgumentException("argument must not be null!");

        this.gsonBuilder = gsonBuilder;
        this.jsonFileService = new JsonFileService<>(gsonBuilder);

        File executableDirectoryFile = new File(executableDirectory);

        if(!executableDirectoryFile.exists() || !executableDirectoryFile.isDirectory())
            throw new IllegalArgumentException("given executable path does not exist or is not a directory");

        // To locate the directory where all Borrower objects are stored.
        String borrowerPath = executableDirectory+File.pathSeparator+"borrower";

        borrowerDirectoryFile = new File(borrowerPath);

        /**
         * Create the directory to store all borrower JSON files if not done yet..
         */
        if(!borrowerDirectoryFile.exists())
        {
            if(!borrowerDirectoryFile.mkdir())
            {
                throw new IOException("could not create the directory to store all borrowers");
            }
        }
    }

    /**
     * @param file File to test the extension for.
     * @param testableExtension File extension to test, ignoring case, without dot! (e.g. "JSON", "jpg", "jPeG" etc.)
     * @return true if the given file has the given file extension, ignoring case. Otherwise, false.
     */
    private boolean hasExtensionIgnoreCase(File file, String testableExtension)
    {
        String extension = StringUtils.substringAfterLast(file.getName(), ".");

        return extension.equalsIgnoreCase(testableExtension);
    }

    /**
     * Returns only the number contained in the filename of that given JSON file.
     * If the given file does not represent a numeric filename or is invalid (e.g. null),
     * then null will be returned.
     *
     * @param numericallyNamedFile
     * @return Number contained in the JSON filename. Otherwise, null (file is not ending on .json, is not numerically named etc.)
     */
    private String getJSONFilenameNumber(File numericallyNamedFile)
    {
        String filenameNaked = null;

        if(numericallyNamedFile.isDirectory() || !hasExtensionIgnoreCase(numericallyNamedFile, "json"))
        {
            return null; // not an expected file ending on .json
        }

        filenameNaked = StringUtils.substringBeforeLast(numericallyNamedFile.getName(), ".");

        if(StringUtils.isNumeric(filenameNaked))
        {
            // Return the numeric filename.
            return filenameNaked;
        }

        return null;
    }

    /**
     * Returns all JSON files from that given directory.
     * If there are no JSON files,
     * then an empty File array is returned.
     *
     * @param directory
     * @return
     * @throws IOException E.g. the given directory is invalid.
     */
    private File[] listNumericJsonFiles(File directory) throws IOException
    {
        IOException e = new IOException("cannot work in that file system environment. Are all files valid? Do they exist?");

        if(directory == null || !directory.isDirectory() || !directory.exists())
            throw e;

        File[] directoryFiles = directory.listFiles();
        String iteratedFilenameNaked = null;

        ArrayList<File> jsonFilesFound = new ArrayList<>();

        if(directoryFiles == null)
            throw e;

        for(File file : directoryFiles)
        {
            iteratedFilenameNaked = getJSONFilenameNumber(file);

            if(iteratedFilenameNaked == null)
            {
                continue; // skip (is directory, is no JSON file or is not numerically named)
            }

            jsonFilesFound.add(file);
        }

        return jsonFilesFound.toArray(new File[0]);
    }

    /**
     * Returns the highest ID found for a Borrower object saved as a JSON file.
     *
     * @param numericJsonsDirectory The directory of all the JSON files being numerically named (e.g. "1.json", "12.json").
     * @return Highest Borrower ID known to the system (judging by the JSON file name). If there is no Borrower object saved yet, then -1 is returned (you can freely choose ID).
     * @throws IOException
     */
    private int getHighestId(File numericJsonsDirectory) throws IOException
    {
        File[] numericJsonFiles = listNumericJsonFiles(numericJsonsDirectory);
        String filenameNumber = null;

        if(numericJsonFiles.length == 0)
            return -1; // No JSON files do exist yet for any IBorrower object.

        int highestIdFound = Integer.MIN_VALUE, newlyFoundId = Integer.MIN_VALUE;

        for(File file : numericJsonFiles)
        {
            filenameNumber = getJSONFilenameNumber(file);

            if(filenameNumber == null)
            {
                throw new IOException("expected numerically named JSON file but got invalid instead");
            }

            // Remember the JSON filename.
            newlyFoundId = Integer.getInteger(filenameNumber);

            if(newlyFoundId > highestIdFound)
            {
                highestIdFound = newlyFoundId;
            }
        }

        return highestIdFound;
    }

    @Override
    public IBorrower createBorrower(String firstname, String lastname, Date dateOfBirth, IResidentialAddress residentialAddress, double schufaScore)
    {
        IBorrower borrower = null;
        String jsonFilepath = null;

        // Date dateOfBirth = Date.from(Instant.parse("1910-01-01"));

        int newBorrowerId = -1;

        try
        {
            newBorrowerId = getHighestId(this.borrowerDirectoryFile) + 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }

        try
        {
            borrower = new Borrower(newBorrowerId, firstname, lastname, dateOfBirth, residentialAddress, schufaScore, new ILoan[]{});

            jsonFilepath = borrowerDirectoryFile.getAbsolutePath() + File.pathSeparator + newBorrowerId + ".json";

            // Also save the created Borrower as a JSON file (https://www.geeksforgeeks.org/java/file-getabsolutepath-method-in-java-with-examples/)
            jsonFileService.save(jsonFilepath, borrower);
        }
        catch(IllegalArgumentException e)
        {
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
            numericJsonFiles = listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            return null;
        }

        for(File jsonFile : numericJsonFiles)
        {
            filenameNumber = getJSONFilenameNumber(jsonFile);

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
            numericJsonFiles = listNumericJsonFiles(borrowerDirectoryFile);
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
            borrowerFiles = listNumericJsonFiles(borrowerDirectoryFile);
        }
        catch(IOException e)
        {
            // e.printStackTrace();

            return false;
        }

        for(File jsonFile : borrowerFiles)
        {
            jsonFilename = getJSONFilenameNumber(jsonFile);

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
        String filepath = borrowerDirectoryFile.getAbsolutePath() + File.pathSeparator + id + ".json";

        return jsonFileService.delete(filepath);
    }
}
