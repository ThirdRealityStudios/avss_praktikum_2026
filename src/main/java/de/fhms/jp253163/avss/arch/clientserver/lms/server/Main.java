package de.fhms.jp253163.avss.arch.clientserver.lms.server;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;

public class Main
{
    public static void main(String[] args)
    {
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