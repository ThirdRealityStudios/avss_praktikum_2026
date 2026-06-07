package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Objects;

public class Controller implements IController
{
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;

    private HttpServer server;
    private HttpContext context;

    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private ICreateLoanViewHandler createLoanViewHandler;

    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;

    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private IUpdateLoanViewHandler updateLoanViewHandler;

    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;

    public Controller(IBorrowerRepository borrowerRepository, ILoanRepository loanRepository) throws IOException, BindException
    {
        Objects.requireNonNull(borrowerRepository);
        Objects.requireNonNull(loanRepository);

        InetSocketAddress socketAddress = new InetSocketAddress(80);
        this.server = HttpServer.create(socketAddress, 0);
        this.server.setExecutor(null);

        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;

        createBorrowerViewHandler = new CreateBorrowerRequestHandler(borrowerRepository, this);
        createLoanViewHandler = new CreateLoanViewHandler(loanRepository);

        readBorrowerViewHandler = new ReadBorrowerViewHandler(borrowerRepository);
        readLoanViewHandler = new ReadLoanViewHandler(loanRepository);

        updateBorrowerViewHandler = new UpdateBorrowerViewHandler(borrowerRepository);
        updateLoanViewHandler = new UpdateLoanViewHandler(loanRepository);

        deleteBorrowerViewHandler = new DeleteBorrowerViewHandler(borrowerRepository);
        deleteLoanViewHandler = new DeleteLoanViewHandler(loanRepository);

        this.server.createContext("/", genHttpHandler());
    }

    private HttpHandler genHttpHandler()
    {
        return new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                URI uri = exchange.getRequestURI();
                String path = uri.getPath();

                String response = null;
                int responseCode = -1;

                if(path == null)
                {
                    response = "Not Found";
                    responseCode = 404;
                }
                else if(path.toLowerCase().startsWith(ENTRY_POINT_BORROWER.toLowerCase()))
                {
                    if(path.length() == ENTRY_POINT_BORROWER.length())
                    {
                        response = "Invalid Request";
                        responseCode = 400;
                    }
                    else
                    {
                        if(path.toLowerCase().startsWith(ENTRY_POINT_BORROWER_CREATE.toLowerCase()))
                        {
                            String numberPromptUri = ENTRY_POINT_BORROWER_CREATE + "/";

                            if(path.toLowerCase().startsWith(numberPromptUri.toLowerCase()))
                            {
                                String borrowerNumber = StringUtils.truncate(path, 0, ENTRY_POINT_BORROWER_CREATE.length());


                            }
                        }
                        else if(path.toLowerCase().startsWith(ENTRY_POINT_BORROWER_READ.toLowerCase()))
                        {

                        }
                        else if(path.toLowerCase().startsWith(ENTRY_POINT_BORROWER_UPDATE.toLowerCase()))
                        {

                        }
                        else if(path.toLowerCase().startsWith(ENTRY_POINT_BORROWER_DELETE.toLowerCase()))
                        {
                            response = "Here borrowers will be shown";
                            responseCode = 200;
                        }
                        else
                        {
                            response = "Invalid Request";
                            responseCode = 400;
                        }
                    }
                }
                else if(path.toLowerCase().startsWith(ENTRY_POINT_LOAN.toLowerCase()))
                {
                    if(path.length() == ENTRY_POINT_LOAN.length())
                    {
                        response = "Invalid Request";
                        responseCode = 400;
                    }
                    else
                    {
                        response = "Here loans will be shown";
                        responseCode = 200;
                    }
                }
                else
                {
                    response = "Not Implemented. Sorry.";
                    responseCode = 501;
                }

                // NumericQueryHandler(String entryPoint)
                // bind()

                exchange.sendResponseHeaders(responseCode, response.length());

                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        };
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
    public void start() {

        this.server.start();
    }
}