package de.fhms.jp253163.avss.arch.mvp.lms;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.IJsonFileService;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.JsonFileService;
import de.fhms.jp253163.avss.arch.mvp.lms.model.*;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.Borrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.Loan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.Presenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.SwingView;

import javax.swing.*;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        IView view = new SwingView(); // TODO Instantiate GUI implementation here instead

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        try
        {
            IJsonFileService<Borrower> jfsBorrower = new JsonFileService<>(builder);
            String executableDirectoryString = System.getProperty("user.dir");

            if(executableDirectoryString == null)
            {
                return;
            }

            File exeutableDirectory = new File(executableDirectoryString);

            IBorrowerRepository borrowerRepository = new BorrowerRepository(jfsBorrower, exeutableDirectory);

            IJsonFileService<Loan> jfsLoan = new JsonFileService<>(builder);
            ISchufaRepository schufaRepo = new SchufaRepository();
            ILoanRepository loanRepository = new LoanRepository(jfsLoan, exeutableDirectory, schufaRepo, borrowerRepository);

            IPresenter presenter = new Presenter(view, borrowerRepository, loanRepository);

            view.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}