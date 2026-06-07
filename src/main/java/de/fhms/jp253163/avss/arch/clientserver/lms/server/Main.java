package de.fhms.jp253163.avss.arch.clientserver.lms.server;

import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.Controller;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.IController;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.data.IJsonSQLService;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.data.JsonSQLService;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Loan;

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

            IController controller = new Controller(borrowerRepository, loanRepository);

            controller.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}