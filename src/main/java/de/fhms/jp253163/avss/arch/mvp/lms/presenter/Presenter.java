package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;

import java.io.IOException;
import java.io.OutputStream;

public class Presenter implements IPresenter
{
    private IView view;
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;

    public Presenter(HttpServer server, IBorrowerRepository borrowerRepository, ILoanRepository loanRepository) throws NullPointerException
    {
        if(borrowerRepository == null || loanRepository == null)
            throw new NullPointerException();

        HttpHandler handler;

        new HttpHandler(){

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "OK";
                exchange.sendResponseHeaders(200, response.length());

                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();

            }
        };

        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;

        if(!setView(view))
            throw new NullPointerException();
    }

    @Override
    public boolean setView(IView view)
    {
        if(view == null)
            return false;

        this.view = view;

        IReadBorrowerViewHandler readBorrowerViewHandler = new ReadBorrowerViewHandler(borrowerRepository, this);
        getView().setReadBorrowerViewHandler(readBorrowerViewHandler);

        IReadLoanViewHandler readLoanViewHandler = new ReadLoanViewHandler(loanRepository, this);
        getView().setReadLoanViewHandler(readLoanViewHandler);

        ICreateBorrowerViewHandler createBorrowerViewHandler = new CreateBorrowerViewHandler(borrowerRepository, this);
        getView().setCreateBorrowerViewHandler(createBorrowerViewHandler);

        ICreateLoanViewHandler createLoanViewHandler = new CreateLoanViewHandler(loanRepository, this);
        getView().setCreateLoanViewHandler(createLoanViewHandler);

        IUpdateBorrowerViewHandler updateBorrowerViewHandler = new UpdateBorrowerViewHandler(borrowerRepository, this);
        getView().setUpdateBorrowerViewHandler(updateBorrowerViewHandler);

        IUpdateLoanViewHandler updateLoanViewHandler = new UpdateLoanViewHandler(loanRepository, this);
        getView().setUpdateLoanViewHandler(updateLoanViewHandler);

        IDeleteBorrowerViewHandler deleteBorrowerViewHandler = new DeleteBorrowerViewHandler(borrowerRepository, this);
        getView().setDeleteBorrowerViewHandler(deleteBorrowerViewHandler);

        IDeleteLoanViewHandler deleteLoanViewHandler = new DeleteLoanViewHandler(loanRepository, this);
        getView().setDeleteLoanViewHandler(deleteLoanViewHandler);

        return true;
    }

    @Override
    public IView getView() {
        return view;
    }
}
