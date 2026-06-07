package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
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

    private int isTrusted(ArrayList<TrustedDirectory> directories, String directoryName)
    {
        String trustedServiceName = null;
        TrustedDirectory trustedDir = null;

        if(directoryName == null) return -1;

        for(int i = 0; i < directories.size(); i++)
        {
            trustedDir = directories.get(i);

            if(trustedDir == null)
            {
                System.out.println("Trusted directory is null. Skipping.");
                continue;
            }

            trustedServiceName = trustedDir.getValue();

            if(trustedServiceName == null)
            {
                System.out.println("Trusted directory value is null. Skipping.");
                continue;
            }

            System.out.println("Comparing known \""+trustedServiceName+"\" with \""+directoryName+"\"");

            if(trustedServiceName.equalsIgnoreCase(directoryName))
                return i;
        }

        return -1;
    }

    private void response(HttpExchange exchange, int responseCode, String responseData) throws IOException
    {
        Objects.requireNonNull(exchange);
        Objects.requireNonNull(responseData);

        exchange.sendResponseHeaders(responseCode, responseData.length());

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseData.getBytes());
        outputStream.close();
    }

    private HttpHandler genHttpHandler()
    {
        return new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                /**
                 * Note!!!
                 *
                 * Due to the part authority part in URIs and so on,
                 * a maximum of 3 forward slashes is being shrunken from the beginning
                 * to only one forward slash
                 * (when sent - no matter if browser, curl etc.).
                 * E.g. "///loan/crete/dfs" would be "/loan/crete/dfs" instead.
                 * Or "////loan/crete/dfs" would be "//loan/crete/dfs" instead.
                 * Or "//loan/crete/dfs" would be "/loan/crete/dfs" instead.
                 */
                URI uri = exchange.getRequestURI();

                Objects.requireNonNull(uri);

                ParsedURI requestParsedUri = new ParsedURI(uri);
                Service requestService = requestParsedUri.getService();

                String requestServiceName = requestService.getName();
                boolean isServiceMissing = requestServiceName == null;
                if(isServiceMissing)
                {
                    response(exchange, 400, "Invalid Request (Service Missing)");
                    return;
                }

                int trustedServiceIndex = isTrusted(TrustedPath.directories, requestServiceName);
                boolean isServiceUnknown = trustedServiceIndex == -1;

                ArrayList<TrustedDirectory> trustedActions = null;
                if(isServiceUnknown) // Check if service is known to the system.
                {
                    // no service called, e.g. only "/" as entry point.
                    response(exchange, 404, "Not Found (Unknown Service: " + requestServiceName + ")");
                    return;
                }

                trustedActions = TrustedPath.directories.get(trustedServiceIndex).getNext();
                if(trustedActions.isEmpty())
                {
                    response(exchange, 501, "Not Implemented (No Action Implemented For That Service)");
                    return;
                }

                Action action = requestService.getAction();
                String actionName = action.getName();
                boolean isActionMissing = actionName == null;
                if(isActionMissing)
                {
                    response(exchange, 400, "Invalid Request (Action Missing)");
                    return;
                }

                int trustedActionIndex = isTrusted(trustedActions, actionName);
                boolean isActionUnknown = trustedActionIndex == -1;

                String actionValue = null;
                if(isActionUnknown)
                {
                    // action given but not known to the system (for that given service)
                    response(exchange, 404, "Not Found (Unknown Action: " + actionName + ")");
                    return;
                }

                actionValue = action.getValue();
                if(actionValue != null)
                {
                    // All request details were fulfilled
                    response(exchange, 200, "OK");
                    return;
                }
                else
                {
                    // missing value for that action
                    response(exchange, 400, "Invalid Request (No Value For Action)");
                    return;
                }
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