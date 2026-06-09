package de.fhms.jp253163.avss.arch.clientserver.lms.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer.BorrowerConsumer;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer.LoanConsumer;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.Presenter;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.IView;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.SwingView;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.*;

import javax.swing.*;
import java.io.File;
import java.net.http.HttpClient;

public class Main
{
    public static void main(String[] args)
    {
        IView view = new SwingView();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        HttpClient client = HttpClient.newHttpClient();

        String authority = "localhost";

        try
        {
            String executableDirectoryString = System.getProperty("user.dir");

            if(executableDirectoryString == null)
            {
                return;
            }

            IBorrowerRepository borrowerConsumer = new BorrowerConsumer(gson, client, authority);
            ILoanRepository loanConsumer = new LoanConsumer(gson, client, authority);

            IPresenter presenter = new Presenter(view, borrowerConsumer, loanConsumer);
            presenter.setView(view);

            view.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}