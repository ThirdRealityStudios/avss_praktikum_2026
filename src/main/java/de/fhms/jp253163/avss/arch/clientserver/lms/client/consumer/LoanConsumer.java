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
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
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

    @Override
    public boolean updateLoan(ILoan loan)
    {
        if(loan == null) return false;

        int id = loan.getId();

        int borrower = loan.getBorrower();

        String loanActionURI = authority + "/loan/update/";
        URI uri = URI.create(loanActionURI);

        boolean bookInstallment = false;

        // Determine differences of remaining balance (compare client and server dataset version)
        // to see if it was modified (after the clicking the button "Book Installment" on GUI e.g.).
        // Version comparison is only needed in this special case because the server
        // decides here between clicking the "Book Installment" button action and between
        // changes of other values (via the text user fields normally).
        ILoan serverLoanVersion = readLoan(id);
        if(serverLoanVersion == null) return false;
        bookInstallment = serverLoanVersion.getRemainingBalance() != loan.getRemainingBalance() || serverLoanVersion.getRemainingTerm() != loan.getRemainingTerm();

        IRequestDTOLoanUpdate loanUpdateDTO = new RequestDTOLoanUpdate(id, borrower, bookInstallment);

        String json = gson.toJson(loanUpdateDTO);

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
            resp = client.send(req, handl);

            if(resp == null) return false;

            if(resp.statusCode() != 200) return false;

            respObj = resp.body();

            if(respObj == null) return false;

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return false;

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return false;

            respLoanUpdate = gson.fromJson(respJson, ResponseDTOLoanUpdate.class);

            if(respLoanUpdate == null) return false;

            return respLoanUpdate.getGlobalResponseCode() == GlobalResponseCode.UPDATE_LOAN_SUCCESS.ordinal();
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

            if(resp == null) return false;

            if(resp.statusCode() != 200) return false;

            respObj = resp.body();

            if(respObj == null) return false;

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return false;

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return false;

            respLoanDelete = gson.fromJson(respJson, ResponseDTOLoanDelete.class);

            if(respLoanDelete == null) return false;

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

    private void forceDelete(ILoan loan)
    {
        loan.setRemainingBalance(0); // clear remaining balances to enable deletion
        loan.setRemainingTerm(0);
        deleteLoan(loan.getId());
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
