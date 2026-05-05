package de.fhms.jp253163.avss.lms.frontend;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.lms.application.BorrowerRepository;
import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.LoanRepository;
import de.fhms.jp253163.avss.lms.application.data.Borrower;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.Loan;
import de.fhms.jp253163.avss.lms.data.IJsonFileService;
import de.fhms.jp253163.avss.lms.data.JsonFileService;

import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        String executableDirectoryString = null;
        File exeutableDirectory = null;

        GsonBuilder gsonBuilder = null;

        IBorrowerRepository borrowerRepository = null;
        JsonFileService<Borrower> borrowerJsonFileService = null;

        ILoanRepository loanRepository = null;
        IJsonFileService<Loan> loanJsonFileService = null;

        executableDirectoryString = System.getProperty("user.dir");

        if(executableDirectoryString == null)
        {
            System.out.println("Cannot read user.dir property for working directory. Exiting.");

            return;
        }

        exeutableDirectory = new File(executableDirectoryString);
        gsonBuilder = new GsonBuilder();

        try
        {
            borrowerJsonFileService = new JsonFileService<Borrower>(gsonBuilder);

            borrowerRepository = new BorrowerRepository(borrowerJsonFileService, exeutableDirectory);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        IConsoleWindow consoleWindow = new ConsoleWindow(loanRepository, borrowerRepository);

        consoleWindow.showCommandInput();
    }
}