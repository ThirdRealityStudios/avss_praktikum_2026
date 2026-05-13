package de.fhms.jp253163.avss.arch.layered.normal.lms;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.*;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.*;
import de.fhms.jp253163.avss.arch.layered.normal.lms.data.IJsonFileService;
import de.fhms.jp253163.avss.arch.layered.normal.lms.data.JsonFileService;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.ConsoleWindow;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.IConsoleWindow;

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
        IJsonFileService<Borrower> borrowerJsonFileService = null;

        ILoanRepository loanRepository = null;
        IJsonFileService<Loan> loanJsonFileService = null;

        ISchufaRepository schufaRepository = null;

        schufaRepository = new SchufaRepository();

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

            return;
        }

        try
        {
            loanJsonFileService = new JsonFileService<Loan>(gsonBuilder);
            loanRepository = new LoanRepository(loanJsonFileService, exeutableDirectory, schufaRepository, borrowerRepository);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return;
        }

        IConsoleWindow consoleWindow = new ConsoleWindow(exeutableDirectory, loanRepository, borrowerRepository, schufaRepository);

        consoleWindow.showCommandInput();
    }
}