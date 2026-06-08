package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDate;
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

    private Gson gson;

    public Controller(Gson gson, IBorrowerRepository borrowerRepository, ILoanRepository loanRepository) throws IOException, BindException
    {
        Objects.requireNonNull(gson);
        Objects.requireNonNull(borrowerRepository);
        Objects.requireNonNull(loanRepository);

        InetSocketAddress socketAddress = new InetSocketAddress(80);
        this.server = HttpServer.create(socketAddress, 0);
        this.server.setExecutor(null);

        this.gson = gson;
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
                 *
                 * So this webserver works as expected because
                 * URI java class simply follows the conventions.
                 *
                 * Hence,
                 * sender needs to know these conventions!
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

                boolean noServiceImplemented = TrustedPath.directories == null || TrustedPath.directories.isEmpty();
                if(noServiceImplemented)
                {
                    response(exchange, 501, "Not Implemented (Service Not Implemented)");
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
                boolean noActionImplemented = trustedActions == null || trustedActions.isEmpty();
                if(noActionImplemented)
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

                String body = new String(exchange.getRequestBody().readAllBytes());

                if(requestServiceName.equalsIgnoreCase("borrower"))
                {
                    routeBorrower(exchange, action, body);

                    return;
                }
                else if(requestServiceName.equalsIgnoreCase("loan"))
                {
                    routeLoan(exchange, action, body);

                    return;
                }

                // Normally a route function should be called.
                // If call goes to here then something went wrong with the trusted or non-implemented services.
                response(exchange, 501, "Not Implemented");
                return;
            }
        };
    }

    /**
     * Requires validation of the parameter "action" in advance (if valid).
     *
     * @param action
     * @param body
     */
    private void routeBorrower(HttpExchange exchange, Action action, String body) throws IOException
    {
        Objects.requireNonNull(exchange);
        Objects.requireNonNull(action);
        Objects.requireNonNull(body);

        boolean cud = action.getName().equalsIgnoreCase("create") || action.getName().equalsIgnoreCase("update") || action.getName().equalsIgnoreCase("delete");
        boolean r = action.getName().equalsIgnoreCase("read");

        if(r)
        {
            getBorrower(exchange, action);
            return;
        }
        else if(cud)
        {
            postBorrower(exchange, action, body);
            return;
        }
        else
        {
            response(exchange, 501, "Not Implemented (Action Not Implemented For That Service)");
            return;
        }
    }

    /**
     * Requires validation of the parameter "action" in advance (if valid).
     *
     * @param action
     * @param body
     */
    private void routeLoan(HttpExchange exchange, Action action, String body) throws IOException
    {
        Objects.requireNonNull(exchange);
        Objects.requireNonNull(action);
        Objects.requireNonNull(body);

        boolean cud = action.getName().equalsIgnoreCase("create") || action.getName().equalsIgnoreCase("update") || action.getName().equalsIgnoreCase("delete");
        boolean r = action.getName().equalsIgnoreCase("read");

        if(r)
        {
            getLoan(exchange, action);
            return;
        }
        else if(cud)
        {
            postLoan(exchange, action, body);
            return;
        }
        else
        {
            response(exchange, 501, "Not Implemented (Action Not Implemented For That Service)");
            return;
        }
    }

    @Override
    public void postBorrower(HttpExchange exchange, Action action, String body)
    {
        Objects.requireNonNull(action);
        Objects.requireNonNull(body);

        int id = -1;
        String firstname = null, lastname = null;
        LocalDate birthday = null;
        IResidentialAddress address = null;
        double schufaScore = -1;
        int[] loans = null;

        IBorrower[] borrowers = null;
        IBorrower borrower = null;

        try
        {
            if(action.getName().equalsIgnoreCase("create"))
            {
                IRequestDTOBorrowerCreate obj = gson.fromJson(body, RequestDTOBorrowerCreate.class);

                if(obj == null) // E.g. called create action but not gave new dataset information in body
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }

                firstname = obj.getFirstname();
                lastname = obj.getLastname();

                birthday = LocalDate.of(obj.getYearOfBirth(), obj.getMonthOfBirth(), obj.getDayOfBirth());

                address = new ResidentialAddress(obj.getStreet(), obj.getNo(), obj.getZip(), obj.getPlace());

                borrowers = new IBorrower[]{ borrowerRepository.createBorrower(firstname, lastname, birthday, address, 0.9d) };

                response(exchange, 200, gson.toJson(borrowers));
                return;
            }
            else if(action.getName().equalsIgnoreCase("update"))
            {
                IRequestDTOBorrowerUpdate obj = gson.fromJson(body, RequestDTOBorrowerUpdate.class);

                if(obj == null) // E.g. called update action but not gave update data
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }

                id = obj.getBorrowerID();
                firstname = obj.getFirstname();
                lastname = obj.getLastname();

                birthday = LocalDate.of(obj.getYearOfBirth(), obj.getMonthOfBirth(), obj.getDayOfBirth());

                address = new ResidentialAddress(obj.getStreet(), obj.getNo(), obj.getZip(), obj.getPlace());

                borrower = borrowerRepository.readBorrower(id);
                loans = obj.getLoans();

                if(borrower == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                // cannot be updated directly
                schufaScore = borrower.getSchufaScore();

                borrower = new Borrower(id, firstname, lastname, birthday, address, schufaScore, loans);

                if(borrowerRepository.updateBorrower(borrower))
                {
                    response(exchange, 200, gson.toJson(borrowers));
                    return;
                }
                else
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }
            }
            else if(action.getName().equalsIgnoreCase("delete") && StringUtils.isNumeric(action.getValue()))
            {
                id = Integer.parseInt(action.getValue());

                if(borrowerRepository.deleteBorrower(id))
                {
                    response(exchange, 200, "OK");
                    return;
                }
                else
                {
                    response(exchange, 404, "Not Found");
                    return;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return;
        }
    }

    @Override
    public void getBorrower(HttpExchange exchange, Action action)
    {
        try
        {
            int id = Integer.parseInt(action.getValue());

            IBorrower[] borrowers = null;
            String bodySendable = null;

            if(id == -1)
            {
                borrowers = borrowerRepository.readAllBorrowers();

                if (borrowers == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                bodySendable = gson.toJson(borrowers);
                response(exchange, 200, bodySendable);
                return;
            }
            else
            {
                borrowers = new IBorrower[]{ borrowerRepository.readBorrower(id) };

                if(borrowers[0] == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                bodySendable = gson.toJson(borrowers);
                response(exchange, 200, bodySendable);
                return;
            }
        }
        catch (Exception e0)
        {
            e0.printStackTrace();

            try
            {
                response(exchange, 400, "Bad Request");
                return;
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void postLoan(HttpExchange exchange, Action action, String body)
    {
        Objects.requireNonNull(action);
        Objects.requireNonNull(body);

        int id = -1;
        int borrower = -1;
        double amount = -1;
        double interestRate = -1;
        int term = -1;

        ILoan[] loans = null;
        ILoan loan = null;

        try
        {
            if(action.getName().equalsIgnoreCase("create"))
            {
                IRequestDTOLoanCreate obj = gson.fromJson(body, RequestDTOLoanCreate.class);

                if(obj == null) // E.g. called create action but not gave new dataset information in body
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }

                borrower = obj.getBorrower();
                amount = obj.getAmount();
                interestRate = obj.getInterestRate();
                term = obj.getTerm();

                loans = new ILoan[]{ loanRepository.createLoan(borrower, amount, interestRate, term) };

                response(exchange, 200, gson.toJson(loans));
                return;
            }
            else if(action.getName().equalsIgnoreCase("update"))
            {
                IRequestDTOLoanUpdate obj = gson.fromJson(body, RequestDTOLoanUpdate.class);

                if(obj == null) // E.g. called update action but not gave update data
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }

                loan = loanRepository.readLoan(obj.getLoanID());

                if(loan == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                borrower = obj.isBookInstallmentWanted() ? loan.getBorrower() : obj.getBorrowerID();

                ILoan loanUpdated = new Loan(id, loan.getAmount(), loan.getInterestRate(), loan.getTerm(), borrower);

                if(obj.isBookInstallmentWanted() && !loanUpdated.payInstallment())
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }

                if(loanRepository.updateLoan(loan))
                {
                    response(exchange, 200, "OK");
                    return;
                }
                else
                {
                    response(exchange, 400, "Bad Request");
                    return;
                }
            }
            else if(action.getName().equalsIgnoreCase("delete") && StringUtils.isNumeric(action.getValue()))
            {
                id = Integer.parseInt(action.getValue());

                if(loanRepository.deleteLoan(id))
                {
                    response(exchange, 200, "OK");
                    return;
                }
                else
                {
                    response(exchange, 404, "Not Found");
                    return;
                }
            }
        }
        catch(Exception e0)
        {
            e0.printStackTrace();

            try
            {
                response(exchange, 400, "Bad Request");
                return;
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void getLoan(HttpExchange exchange, Action action)
    {
        try
        {
            int id = Integer.parseInt(action.getValue());

            ILoan[] loans = null;
            String sendableBody = null;

            if(id == -1)
            {
                loans = loanRepository.readAllLoans();

                if(loans == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                sendableBody = gson.toJson(loans);
                response(exchange, 200, sendableBody);
                return;
            }
            else
            {
                loans = new ILoan[]{ loanRepository.readLoan(id) };

                if(loans[0] == null)
                {
                    response(exchange, 404, "Not Found");
                    return;
                }

                sendableBody = gson.toJson(loans);
                response(exchange, 200, sendableBody);
                return;
            }
        }
        catch (Exception e0)
        {
            e0.printStackTrace();

            try
            {
                response(exchange, 400, "Bad Request");
                return;
            }
            catch (Exception e1)
            {
                e1.printStackTrace();

                return;
            }
        }
    }

    @Override
    public void start()
    {
        this.server.start();
    }
}