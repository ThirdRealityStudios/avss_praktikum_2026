package de.fhms.jp253163.avss.arch.mvp.lms;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.IJsonFileService;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.IJsonSQLService;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.JsonFileService;
import de.fhms.jp253163.avss.arch.mvp.lms.infrastructure.JsonSQLService;
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
        IView view = new SwingView();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        try
        {
            IJsonSQLService<Borrower> jssBorrower = new JsonSQLService<>(builder);
            String executableDirectoryString = System.getProperty("user.dir");

            if(executableDirectoryString == null)
            {
                return;
            }

            IBorrowerRepository borrowerRepository = new BorrowerDatabaseRepository(jssBorrower);

            IJsonSQLService<Loan> jssLoan = new JsonSQLService<>(builder);
            ISchufaRepository schufaRepo = new SchufaRepository();
            ILoanRepository loanRepository = new LoanDatabaseRepository(jssLoan, schufaRepo, borrowerRepository);

            IPresenter presenter = new Presenter(view, borrowerRepository, loanRepository);
            presenter.setView(view);

            view.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}