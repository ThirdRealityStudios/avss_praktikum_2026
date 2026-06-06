package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.logging.Logger;

public class Controller implements IController
{
    private IController controller;
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;

    private HttpServer server;
    private HttpContext context;

    public Controller(IBorrowerRepository borrowerRepository, ILoanRepository loanRepository) throws IOException, BindException
    {
        Objects.requireNonNull(borrowerRepository);
        Objects.requireNonNull(loanRepository);

        InetSocketAddress socketAddress = new InetSocketAddress(80);
        this.server = HttpServer.create(socketAddress, 0);
        this.server.setExecutor(null);

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

        /*
        if(!setView(view))
            throw new NullPointerException();

         */
    }

    @Override
    public boolean postBorrower(String action, String id, String data) {
        return false;
    }

    @Override
    public String[] getBorrower(String id) {
        return new String[0];
    }

    @Override
    public void postLoan(String action, String id, String json) {

    }

    @Override
    public String[] getLoan(String id) {
        return new String[0];
    }

    @Override
    public void setPostHandler(HttpHandler handler) {
        Objects.requireNonNull(handler);

        if(this.context != null)
            this.server.removeContext(context);

        this.context = this.server.createContext("/", handler);
    }

    @Override
    public void start() {
        this.server.start();
    }



    /*
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

     */
}
