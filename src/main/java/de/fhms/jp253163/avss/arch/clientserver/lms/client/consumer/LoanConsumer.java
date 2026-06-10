package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import com.google.gson.Gson;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Strictly or normally a model component (or rather consumer component here)
 * should not know exactly the implementation code for HTTP (in this case).
 * Anyway,
 * for usability and ease of use,
 * sometimes this strictness is ignored for the sake of quick implementations.
 */
public class LoanConsumer implements ILoanRepository
{
    private BorrowerConsumer borrowerConsumer;
    private Gson gson;
    private HttpClient client;
    private String authority;

    public LoanConsumer(BorrowerConsumer borrowerConsumer, Gson gson, HttpClient client, String authority)
    {
        Objects.requireNonNull(borrowerConsumer);
        Objects.requireNonNull(gson);
        Objects.requireNonNull(client);
        Objects.requireNonNull(authority);

        if(authority.isEmpty()) throw new IllegalArgumentException("authority can't be empty");

        this.borrowerConsumer = borrowerConsumer;
        this.gson = gson;
        this.client = client;
        this.authority = authority;
    }

    @Override
    public ILoan createLoan(int borrower, double amount, double interestRate, int term)
    {
        String loanActionURI = authority + "/loan/create/";
        URI uri = URI.create(loanActionURI);

        IRequestDTOLoanCreate loanCreateDTO = new RequestDTOLoanCreate(borrower, amount, interestRate, term);

        String json = gson.toJson(loanCreateDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOLoanCreate respLoanCreate = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null) return null;

            if(resp.statusCode() != 200) return null;

            respObj = resp.body();

            if(respObj == null) return null;

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return null;

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return null;

            respLoanCreate = gson.fromJson(respJson, ResponseDTOLoanCreate.class);

            if(respLoanCreate == null) return null;

            if(respLoanCreate.getGlobalResponseCode() != GlobalResponseCode.CREATE_LOAN_CREATION_SUCCESS.ordinal()) return null;

            return respLoanCreate.getLoan();
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public ILoan readLoan(int id)
    {
        String loanActionURI = authority + "/loan/read/" + id;
        URI uri = URI.create(loanActionURI);

        HttpRequest req = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOLoanRead respLoanRead = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null) return null;

            if(resp.statusCode() != 200) return null;

            respObj = resp.body();

            if(respObj == null) return null;

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return null;

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return null;

            respLoanRead = gson.fromJson(respJson, ResponseDTOLoanRead.class);

            if(respLoanRead == null) return null;

            if(respLoanRead.getGlobalResponseCode() != GlobalResponseCode.READ_LOAN_FOUND_SUCCESS.ordinal()) return null;

            if(respLoanRead.getLoans() == null || respLoanRead.getLoans().length != 1) return null;

            return respLoanRead.getLoans()[0];
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public ILoan[] readAllLoans()
    {
        String loanActionURI = authority + "/loan/read/-1";
        URI uri = URI.create(loanActionURI);

        HttpRequest req = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOLoanRead respLoanRead = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null) return new ILoan[0];

            if(resp.statusCode() != 200) return new ILoan[0];

            respObj = resp.body();

            if(respObj == null) return new ILoan[0];

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return new ILoan[0];

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return new ILoan[0];

            respLoanRead = gson.fromJson(respJson, ResponseDTOLoanRead.class);

            if(respLoanRead == null) return new ILoan[0];

            if(respLoanRead.getGlobalResponseCode() != GlobalResponseCode.READ_LOAN_FOUND_SUCCESS.ordinal()) return new ILoan[0];

            if(respLoanRead.getLoans() == null) return new ILoan[0];

            return respLoanRead.getLoans();
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return new ILoan[0];
        }
    }

    /**
     * The server only accepts one installment booking (fixed) per HTTP request.
     * Hence,
     * you cannot completely set the remaining balance directly to zero.
     * The server will ignore it.
     * The server only wants to know whether there should be an
     * installment booking or not.
     * Or if you want to update other loan details
     * (like the borrower ID - but not important for this function call!).
     *
     * @param clientSideModified The modified version on your client.
     * @return True if a modification of the remaining balance or term could be determined = server do pay an installment!
     */
    private boolean isPayInstallmentRequestedForServerSide(ILoan clientSideModified)
    {
        /**
         * The untouched server version of that loan (before update e.g.). You might want to call readLoan(..) for that maybe.
         */
        ILoan serverOriginal = readLoan(clientSideModified.getId());

        if(serverOriginal == null)
        {
            System.out.println("Client consumer: Server not having loan with ID "+clientSideModified.getId()+" (ERROR)");
            System.out.println("Hence, exiting... (cannot determine whether loan was modified to request pay installment for server or not)");
            return false;
        }

        boolean bookInstallment = false;

        bookInstallment = serverOriginal.getRemainingBalance() != clientSideModified.getRemainingBalance() || serverOriginal.getRemainingTerm() != clientSideModified.getRemainingTerm();

        if(bookInstallment)
            System.out.println("Client consumer requests server to pay installment for loan with ID: " + clientSideModified.getId());
        else
            System.out.println("Client does NOT request server to pay installment for loan with ID: " + clientSideModified.getId());

        return bookInstallment;
    }

    @Override
    public boolean updateLoan(ILoan loan)
    {
        System.out.println("Requesting loan update...");
        if(loan == null)
        {
            System.out.println("But no updatable domain object given (exiting)....");
            return false;
        }

        int id = loan.getId();
        System.out.println("Loan to update has ID: " + id);

        int borrower = loan.getBorrower();

        String loanActionURI = authority + "/loan/update/";
        URI uri = URI.create(loanActionURI);

        System.out.println("Loan found via HTTP server query (OK)");

        IRequestDTOLoanUpdate loanUpdateDTO = new RequestDTOLoanUpdate(id, borrower, isPayInstallmentRequestedForServerSide(loan));

        String json = gson.toJson(loanUpdateDTO);
        System.out.println("Creating update request:\n\n" + json + "\n\n");

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOLoanUpdate respLoanUpdate = null;

        try
        {
            System.out.println("Sending update HTTP request now...");
            resp = client.send(req, handl);

            if(resp == null)
            {
                System.out.println("Got no response back (null, ERROR). Exiting...");
                return false;
            }

            if(resp.statusCode() != 200)
            {
                System.out.println("Got no success back (response code is NOT 200, ERROR). Exiting...");
                return false;
            }

            respObj = resp.body();

            if(respObj == null)
            {
                System.out.println("Got NO response BODY back (null, ERROR). Exiting...");
                return false;
            }

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0)
            {
                System.out.println("Got EMPTY response BODY back (ERROR). Exiting...");
                return false;
            }

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty())
            {
                System.out.println("Parsed EMPTY UTF-8 response BODY (ERROR). Exiting...");
                return false;
            }

            System.out.println("Client consumer - got HTTP response with following JSON:\n\n"+respJson+"\n\n");

            respLoanUpdate = gson.fromJson(respJson, ResponseDTOLoanUpdate.class);

            if(respLoanUpdate == null)
            {
                System.out.println("Could not parse BODY to JSON (ERROR). Exiting...");
                return false;
            }

            boolean globalResponseCodeSuccess = respLoanUpdate.getGlobalResponseCode() == GlobalResponseCode.UPDATE_LOAN_SUCCESS.ordinal();

            System.out.println("Global response code (business code) status ok?: " + globalResponseCodeSuccess + " (" + respLoanUpdate.getGlobalResponseCode() + ")");

            return globalResponseCodeSuccess;
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean deleteLoan(int loan)
    {
        String loanActionURI = authority + "/loan/delete/";
        URI uri = URI.create(loanActionURI);

        IRequestDTOLoanDelete loanDeleteDTO = new RequestDTOLoanDelete(loan);

        String json = gson.toJson(loanDeleteDTO);

        if(json.isEmpty()) // was accidentally inverted logic
        {
            System.out.println("Trying to send EMPTY (BODY) request to server!");

            return false;
        }
        else
        {
            System.out.println("Trying to send request to server:\n\n"+json+"\n\n");
        }

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOLoanDelete respLoanDelete = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null)
            {
                System.out.println("Got NO RESPONSE from server");
                return false;
            }

            System.out.println("Got response code from server: " + resp.statusCode());

            if(resp.statusCode() != 200)
            {
                System.out.println("Exiting loan deletion due to bad response code...");
                return false;
            }

            respObj = resp.body();

            if(respObj == null)
            {
                System.out.println("Got NO response BODY from server");
                return false;
            }

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0)
            {
                System.out.println("Got EMPTY response BODY from server (code point 0)");
                return false;
            }

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty())
            {
                System.out.println("Got EMPTY response BODY from server (code point 1)");
                return false;
            }

            System.out.println("Got the following body from server:\n\n"+respJson+"\n\n");

            respLoanDelete = gson.fromJson(respJson, ResponseDTOLoanDelete.class);

            if(respLoanDelete == null)
            {
                System.out.println("Cannot parse body to JSON from response!");
                return false;
            }

            return respLoanDelete.getGlobalResponseCode() == GlobalResponseCode.DELETE_LOAN_SUCCESS.ordinal();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isLoanCreatable(int borrower, double amount, double interestRate, int term)
    {
        ILoan loan = null;

        try
        {
            loan = createLoan(borrower, amount, interestRate, term);

            if(loan == null) return false;

            forceDelete(loan);

            return true;
        }
        catch(Exception exc)
        {

        }

        if(loan != null)
        {
            forceDelete(loan);
        }

        return false;
    }

    @Override
    public boolean isValidBorrowerIDForCreation(int borrower)
    {
        return isLoanCreatable(borrower, ILoan.EXAMPLE_AMOUNT, ILoan.EXAMPLE_INTEREST_RATE, ILoan.EXAMPLE_TERM);
    }

    @Override
    public boolean isValidAmountForCreation(double amount)
    {
        return isLoanCreatable(ILoan.EXAMPLE_BORROWER, amount, ILoan.EXAMPLE_INTEREST_RATE, ILoan.EXAMPLE_TERM);
    }

    @Override
    public boolean isValidInterestRateForCreation(double interestRate)
    {
        return isLoanCreatable(ILoan.EXAMPLE_BORROWER, ILoan.EXAMPLE_AMOUNT, interestRate, ILoan.EXAMPLE_TERM);
    }

    @Override
    public boolean isValidTermForCreation(int term)
    {
        return isLoanCreatable(ILoan.EXAMPLE_BORROWER, ILoan.EXAMPLE_AMOUNT, ILoan.EXAMPLE_INTEREST_RATE, term);
    }

    private ILoan sampleLoan()
    {
        return createLoan(ILoan.EXAMPLE_BORROWER, ILoan.EXAMPLE_AMOUNT, ILoan.EXAMPLE_INTEREST_RATE, ILoan.EXAMPLE_TERM);
    }

    /**
     * @param loan Must provided. Cannot be null!!
     */
    private void forceDelete(ILoan loan)
    {
        Objects.requireNonNull(loan);

        ILoan updatedLoanServerVersion = null;

        // "Clear" all remaining balances and remaining terms,
        // sending as many HTTP request as needed.
        // This is,
        // because the server only accepts one installment payment per HTTP request.
        // You also cannot pay more than fixed installment rate per HTTP request.
        // So, the installment amount cannot be chosen by the client.
        // The server will only accept its own way of paying of the installment.
        // The server will know that an installment payment is requested
        // because the client prepares the HTTP request correctly
        // (like: there is a difference between the server and client version -
        //  judging by the remaining balance and remaining term - so please server, do pay an installment)
        do
        {
            updatedLoanServerVersion = readLoan(loan.getId());
            updatedLoanServerVersion.payInstallment();
        }
        while(updateLoan(updatedLoanServerVersion));

        if(!deleteLoan(loan.getId()))
            System.out.println("Force deletion failed for loan:\n\n"+loan.toString()+"\n\n");
        else
            System.out.println("Force deletion successful for loan ID: " + loan.getId());
    }

    @Override
    public boolean isValidBorrowerIDForUpdate(int borrower)
    {
        ILoan sample = sampleLoan();
        boolean updatable = false;

        if(!sample.setBorrower(borrower))
        {
            forceDelete(sample);

            return false;
        }

        updatable = updateLoan(sample);

        forceDelete(sample);

        return updatable;
    }
}
