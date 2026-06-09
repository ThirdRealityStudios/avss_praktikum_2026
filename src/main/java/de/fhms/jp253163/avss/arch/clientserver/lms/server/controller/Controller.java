package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.*;
import org.apache.commons.lang3.StringUtils;

import java.beans.Encoder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Controller implements IController
{
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;

    private HttpServer server;

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
                continue;
            }

            trustedServiceName = trustedDir.getValue();

            if(trustedServiceName == null)
            {
                continue;
            }

            if(trustedServiceName.equalsIgnoreCase(directoryName))
                return i;
        }

        return -1;
    }

    private void response(HttpExchange exchange, int responseCode, String responseData) throws IOException
    {
        Objects.requireNonNull(exchange);
        Objects.requireNonNull(responseData);

        byte[] byteCode = responseData.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(responseCode, byteCode.length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(byteCode);
        outputStream.close();

        System.out.println("Responding with:\n\n"+responseData+"\n\n");
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

    /**
     * @param exchange
     * @param isCreation If 'true', then creation is meant (respond with borrower creation response JSON). Otherwise, an update error response is meant.
     * @param code
     * @param httpResponseCode E.g. 400, 404
     * @throws IOException
     */
    private void sendBorrowerCUErrorResponse(HttpExchange exchange, boolean isCreation, GlobalResponseCode code, int httpResponseCode) throws IOException
    {
        String json = null;

        if(isCreation)
        {
            IResponseDTOBorrowerCreate resp = new ResponseDTOBorrowerCreate(null, code);
            json = gson.toJson(resp);

        }
        else // is update
        {
            IResponseDTOBorrowerUpdate resp = new ResponseDTOBorrowerUpdate(code);
            json = gson.toJson(resp);
        }

        response(exchange, httpResponseCode, json);
    }

    /**
     * @param exchange
     * @param isCreation If 'true' then a borrower create response object will be sent back. Otherwise, an update is assumed, sending back a borrower update response.
     * @param day
     * @param month
     * @param year
     * @return
     * @throws IOException
     */
    private boolean abortOnInvalidBirthday(HttpExchange exchange, boolean isCreation, int day, int month, int year) throws IOException
    {
        if(!borrowerRepository.isValidDayOfBirthForUpdate(day))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_DAY_OF_BIRTH, 400);
            return true;
        }

        if(!borrowerRepository.isValidMonthOfBirthForUpdate(month))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_MONTH_OF_BIRTH, 400);
            return true;
        }

        if(!borrowerRepository.isValidYearOfBirthForUpdate(year))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_YEAR_OF_BIRTH, 400);
            return true;
        }

        return false;
    }

    private boolean abortOnInvalidNames(HttpExchange exchange, boolean isCreation, String firstname, String lastname) throws IOException
    {
        if(!borrowerRepository.isValidFirstnameForUpdate(firstname))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_FIRSTNAME, 400);
            return true;
        }

        if(!borrowerRepository.isValidLastnameForUpdate(lastname))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_LASTNAME, 400);
            return true;
        }

        return false;
    }

    private boolean abortOnInvalidAddress(HttpExchange exchange, boolean isCreation, String street, String no, String place, int zip) throws IOException
    {
        if(!borrowerRepository.isValidStreetForUpdate(street))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_STREET, 400);
            return true;
        }

        if(!borrowerRepository.isValidNoForUpdate(no))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_NO, 400);
            return true;
        }

        if(!borrowerRepository.isValidPlaceForUpdate(place))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_PLACE, 400);
            return true;
        }

        if(!borrowerRepository.isValidZipForUpdate(zip))
        {
            sendBorrowerCUErrorResponse(exchange, isCreation, GlobalResponseCode.UPDATE_BORROWER_INVALID_ZIP, 400);
            return true;
        }

        return false;
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

        Borrower[] borrowers = null;
        Borrower borrower = null;

        try
        {
            if(action.getName().equalsIgnoreCase("create"))
            {
                IRequestDTOBorrowerCreate obj = gson.fromJson(body, RequestDTOBorrowerCreate.class);

                if(obj == null) // E.g. called create action but not gave new dataset information in body
                {
                    sendBorrowerCUErrorResponse(exchange, true, GlobalResponseCode.CREATE_BORROWER_INVALID_CREATION_REQUEST, 400);
                    return;
                }

                if(abortOnInvalidNames(exchange, true, obj.getFirstname(), obj.getLastname()))
                    return;

                firstname = obj.getFirstname();
                lastname = obj.getLastname();

                if(abortOnInvalidBirthday(exchange, true, obj.getDayOfBirth(), obj.getMonthOfBirth(), obj.getYearOfBirth()))
                    return;

                birthday = LocalDate.of(obj.getYearOfBirth(), obj.getMonthOfBirth(), obj.getDayOfBirth());

                if(abortOnInvalidAddress(exchange, true, obj.getStreet(), obj.getNo(), obj.getPlace(), obj.getZip()))
                    return;

                address = new ResidentialAddress(obj.getStreet(), obj.getNo(), obj.getZip(), obj.getPlace());

                borrower = (Borrower) borrowerRepository.createBorrower(firstname, lastname, birthday, address, 0.9d);

                IResponseDTOBorrowerCreate resp = new ResponseDTOBorrowerCreate(borrower, GlobalResponseCode.CREATE_BORROWER_CREATION_SUCCESS);
                String json = gson.toJson(resp);
                response(exchange, 200, json);
                return;
            }
            else if(action.getName().equalsIgnoreCase("update"))
            {
                IRequestDTOBorrowerUpdate obj = gson.fromJson(body, RequestDTOBorrowerUpdate.class);

                if(obj == null) // E.g. called update action but not gave update data
                {
                    sendBorrowerCUErrorResponse(exchange, false, GlobalResponseCode.UPDATE_BORROWER_NO_SELECTED_DATASET, 400);
                    return;
                }

                id = obj.getBorrowerID();

                if(abortOnInvalidNames(exchange, false, obj.getFirstname(), obj.getLastname()))
                    return;

                firstname = obj.getFirstname();
                lastname = obj.getLastname();

                if(abortOnInvalidBirthday(exchange, false, obj.getDayOfBirth(), obj.getMonthOfBirth(), obj.getYearOfBirth()))
                    return;

                birthday = LocalDate.of(obj.getYearOfBirth(), obj.getMonthOfBirth(), obj.getDayOfBirth());

                if(abortOnInvalidAddress(exchange, false, obj.getStreet(), obj.getNo(), obj.getPlace(), obj.getZip()))
                    return;

                address = new ResidentialAddress(obj.getStreet(), obj.getNo(), obj.getZip(), obj.getPlace());

                borrower = (Borrower) borrowerRepository.readBorrower(id);
                loans = obj.getLoans();

                if(borrower == null)
                {
                    sendBorrowerCUErrorResponse(exchange, false, GlobalResponseCode.UPDATE_BORROWER_NOT_FOUND, 404);
                    return;
                }

                // cannot be updated directly
                schufaScore = borrower.getSchufaScore();

                borrower = new Borrower(id, firstname, lastname, birthday, address, schufaScore, loans);

                if(borrowerRepository.updateBorrower(borrower))
                {
                    IResponseDTOBorrowerUpdate resp = new ResponseDTOBorrowerUpdate(GlobalResponseCode.UPDATE_BORROWER_SUCCESS);
                    String json = gson.toJson(resp);
                    response(exchange, 200, json);
                    return;
                }
                else
                {
                    sendBorrowerCUErrorResponse(exchange, false, GlobalResponseCode.UPDATE_BORROWER_INVALID_REQUEST, 400);
                    return;
                }
            }
            else if(action.getName().equalsIgnoreCase("delete"))
            {
                IRequestDTOBorrowerDelete obj = gson.fromJson(body, RequestDTOBorrowerDelete.class);

                String json = null;
                IResponseDTOBorrowerDelete resp = null;

                if(obj == null)
                {
                    resp = new ResponseDTOBorrowerDelete(GlobalResponseCode.DELETE_BORROWER_ERROR);
                    json = gson.toJson(resp);

                    response(exchange, 400, json);
                    return;
                }

                id = obj.getBorrowerID();

                if(borrowerRepository.deleteBorrower(id))
                {
                    resp = new ResponseDTOBorrowerDelete(GlobalResponseCode.DELETE_BORROWER_SUCCESS);
                    json = gson.toJson(resp);
                    response(exchange, 200, json);
                    return;
                }
                else
                {
                    resp = new ResponseDTOBorrowerDelete(GlobalResponseCode.DELETE_BORROWER_ERROR);
                    json = gson.toJson(resp);
                    response(exchange, 404, json);
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
        String json;
        IResponseDTOBorrowerRead resp;

        try
        {
            boolean isInvalidID = action.getValue() == null || !StringUtils.isNumeric(action.getValue()) && !action.getValue().equalsIgnoreCase("-1");

            if(isInvalidID)
            {
                resp = new ResponseDTOBorrowerRead(new Borrower[0], GlobalResponseCode.READ_BORROWER_INVALID_REQUEST);
                json = gson.toJson(resp);
                response(exchange, 400, json);
                return;
            }

            int id = Integer.parseInt(action.getValue());

            Borrower[] borrowers = null;

            if(id == -1)
            {
                borrowers = (Borrower[]) borrowerRepository.readAllBorrowers();

                if(borrowers == null || borrowers.length == 0)
                {
                    resp = new ResponseDTOBorrowerRead(new Borrower[0], GlobalResponseCode.READ_BORROWER_EMPTY_DATABASE);
                    json = gson.toJson(resp);
                    response(exchange, 404, json);
                    return;
                }

                resp = new ResponseDTOBorrowerRead(borrowers, GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS);
                json = gson.toJson(resp);
                response(exchange, 200, json);
                return;
            }
            else
            {
                borrowers = new Borrower[]{ (Borrower) borrowerRepository.readBorrower(id) };

                if(borrowers[0] == null)
                {
                    resp = new ResponseDTOBorrowerRead(new Borrower[0], GlobalResponseCode.READ_BORROWER_NOT_FOUND);
                    json = gson.toJson(resp);
                    response(exchange, 404, json);
                    return;
                }

                resp = new ResponseDTOBorrowerRead(borrowers, GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS);
                json = gson.toJson(resp);
                response(exchange, 200, json);
                return;
            }
        }
        catch (Exception e0)
        {
            e0.printStackTrace();

            try
            {
                resp = new ResponseDTOBorrowerRead(new Borrower[0], GlobalResponseCode.READ_BORROWER_INVALID_REQUEST);
                json = gson.toJson(resp);
                response(exchange, 400, json);
                return;
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                return;
            }
        }
    }

    private boolean abortOnInvalidLoanBaseData(HttpExchange exchange, boolean isUpdate, int borrower, double amount, double interestRate, int term) throws IOException
    {
        String json;
        IResponseDTOLoanCreate respC;
        IResponseDTOLoanUpdate respU;

        if(isUpdate)
        {
            if(!loanRepository.isValidBorrowerIDForUpdate(borrower))
            {
                respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_INVALID_BORROWER_ID);
                json = gson.toJson(respU);
                response(exchange, 400, json);
            }
        }
        else
        {
            if(!loanRepository.isValidBorrowerIDForCreation(borrower))
            {
                respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_INVALID_BORROWER_ID);
                json = gson.toJson(respC);
                response(exchange, 400, json);
                return true;
            }

            if(!loanRepository.isValidAmountForCreation(amount))
            {
                respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_INVALID_AMOUNT);
                json = gson.toJson(respC);
                response(exchange, 400, json);
                return true;
            }

            if(!loanRepository.isValidInterestRateForCreation(interestRate))
            {
                respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_INVALID_INTEREST_RATE);
                json = gson.toJson(respC);
                response(exchange, 400, json);
                return true;
            }

            if(!loanRepository.isValidTermForCreation(term))
            {
                respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_INVALID_TERM);
                json = gson.toJson(respC);
                response(exchange, 400, json);
                return true;
            }
        }

        return false;
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

        Loan[] loans = null;
        Loan loan = null;

        IResponseDTOLoanCreate respC;
        IResponseDTOLoanRead respR;
        IResponseDTOLoanUpdate respU;
        IResponseDTOLoanDelete respD;
        String json;

        try
        {
            if(action.getName().equalsIgnoreCase("create"))
            {
                IRequestDTOLoanCreate obj = gson.fromJson(body, RequestDTOLoanCreate.class);

                if(obj == null) // E.g. called create action but not gave new dataset information in body
                {
                    respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_INVALID_CREATION_REQUEST);
                    json = gson.toJson(respC);
                    response(exchange, 400, json);
                    return;
                }

                borrower = obj.getBorrower();
                amount = obj.getAmount();
                interestRate = obj.getInterestRate();
                term = obj.getTerm();

                if(abortOnInvalidLoanBaseData(exchange, false, borrower, amount, interestRate, term))
                    return;

                loan = (Loan) loanRepository.createLoan(borrower, amount, interestRate, term);

                if(loan == null)
                {
                    respC = new ResponseDTOLoanCreate(null, GlobalResponseCode.CREATE_LOAN_CREATION_ERROR);
                    json = gson.toJson(respC);
                    response(exchange, 400, json);
                    return;
                }

                respC = new ResponseDTOLoanCreate(loan, GlobalResponseCode.CREATE_LOAN_CREATION_SUCCESS);
                json = gson.toJson(respC);
                response(exchange, 200, json);
                return;
            }
            else if(action.getName().equalsIgnoreCase("update"))
            {
                IRequestDTOLoanUpdate obj = gson.fromJson(body, RequestDTOLoanUpdate.class);

                if(obj == null) // E.g. called update action but not gave update data
                {
                    respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_NO_SELECTED_DATASET);
                    json = gson.toJson(respU);
                    response(exchange, 400, json);
                    return;
                }

                loan = (Loan) loanRepository.readLoan(obj.getLoanID());

                if(loan == null)
                {
                    respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_NOT_FOUND);
                    json = gson.toJson(respU);
                    response(exchange, 404, json);
                    return;
                }

                borrower = obj.isBookInstallmentWanted() ? loan.getBorrower() : obj.getBorrowerID();

                // Will only check if "borrower" above is as it should be. The others are ignored.
                if(abortOnInvalidLoanBaseData(exchange, true, borrower, amount, interestRate, term))
                    return;

                Loan loanUpdated = new Loan(id, loan.getAmount(), loan.getInterestRate(), loan.getTerm(), borrower);

                if(obj.isBookInstallmentWanted() && !loanUpdated.payInstallment())
                {
                    respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_ERROR);
                    json = gson.toJson(respU);
                    response(exchange, 400, json);
                    return;
                }

                if(loanRepository.updateLoan(loan))
                {
                    respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_SUCCESS);
                    json = gson.toJson(respU);
                    response(exchange, 200, json);
                    return;
                }
                else
                {
                    respU = new ResponseDTOLoanUpdate(GlobalResponseCode.UPDATE_LOAN_ERROR);
                    json = gson.toJson(respU);
                    response(exchange, 400, json);
                    return;
                }
            }
            else if(action.getName().equalsIgnoreCase("delete"))
            {
                IRequestDTOLoanDelete obj = gson.fromJson(body, RequestDTOLoanDelete.class);

                if(obj == null) // E.g. called delete action but not gave deletion data
                {
                    respD = new ResponseDTOLoanDelete(GlobalResponseCode.DELETE_LOAN_NO_SELECTED_DATASET);
                    json = gson.toJson(respD);
                    response(exchange, 400, json);
                    return;
                }

                id = obj.getLoanID();

                if(loanRepository.deleteLoan(id))
                {
                    respD = new ResponseDTOLoanDelete(GlobalResponseCode.DELETE_LOAN_SUCCESS);
                    json = gson.toJson(respD);
                    response(exchange, 200, json);
                    return;
                }
                else
                {
                    respD = new ResponseDTOLoanDelete(GlobalResponseCode.DELETE_LOAN_INVALID_REQUEST);
                    json = gson.toJson(respD);
                    response(exchange, 404, json);
                    return;
                }
            }
            else
            {
                response(exchange, 400, "Bad Request");
                return;
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
        String json;
        IResponseDTOLoanRead resp;

        try
        {
            boolean isInvalidID = action.getValue() == null || !StringUtils.isNumeric(action.getValue()) && !action.getValue().equalsIgnoreCase("-1");

            if(isInvalidID)
            {
                resp = new ResponseDTOLoanRead(new Loan[0], GlobalResponseCode.READ_LOAN_INVALID_REQUEST);
                json = gson.toJson(resp);
                response(exchange, 400, json);
                return;
            }

            int id = Integer.parseInt(action.getValue());

            Loan[] loans = null;
            if(id == -1)
            {
                loans = (Loan[]) loanRepository.readAllLoans();

                if(loans == null || loans.length == 0)
                {
                    resp = new ResponseDTOLoanRead(new Loan[0], GlobalResponseCode.READ_LOAN_EMPTY_DATABASE);
                    json = gson.toJson(resp);
                    response(exchange, 404, json);
                    return;
                }

                resp = new ResponseDTOLoanRead(loans, GlobalResponseCode.READ_LOAN_FOUND_SUCCESS);
                json = gson.toJson(resp);
                response(exchange, 200, json);
                return;
            }
            else
            {
                loans = new Loan[]{ (Loan) loanRepository.readLoan(id) };

                if(loans[0] == null)
                {
                    resp = new ResponseDTOLoanRead(new Loan[0], GlobalResponseCode.READ_LOAN_NOT_FOUND);
                    json = gson.toJson(resp);
                    response(exchange, 404, json);
                    return;
                }

                resp = new ResponseDTOLoanRead(loans, GlobalResponseCode.READ_LOAN_FOUND_SUCCESS);
                json = gson.toJson(resp);
                response(exchange, 200, json);
                return;
            }
        }
        catch (Exception e0)
        {
            e0.printStackTrace();

            try
            {
                resp = new ResponseDTOLoanRead(new Loan[0], GlobalResponseCode.READ_LOAN_INVALID_REQUEST);
                json = gson.toJson(resp);
                response(exchange, 400, json);
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