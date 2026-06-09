package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import com.google.gson.Gson;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.loan.IRequestDTOLoanRead;
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
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.loan.RequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan.ResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ResidentialAddress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
    private Gson gson;
    private HttpClient client;
    private String authority;

    public LoanConsumer(Gson gson, HttpClient client, String authority)
    {
        Objects.requireNonNull(gson);
        Objects.requireNonNull(client);
        Objects.requireNonNull(authority);

        if(authority.isEmpty()) throw new IllegalArgumentException("authority can't be empty");

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
    public ILoan readLoan(int loan)
    {
        String loanActionURI = authority + "/loan/read/";
        URI uri = URI.create(loanActionURI);

        IRequestDTOLoanRead loanReadDTO = new RequestDTOLoanRead(loan);

        String json = gson.toJson(loanReadDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

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
        String loanActionURI = authority + "/loan/read/";
        URI uri = URI.create(loanActionURI);

        IRequestDTOLoanRead loanReadDTO = new RequestDTOLoanRead(-1);

        String json = gson.toJson(loanReadDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

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
        Objects.requireNonNull(loan);

        int id = loan.getId();

        int borrower = loan.getBorrower();

        String loanActionURI = authority + "/loan/update/";
        URI uri = URI.create(loanActionURI);

        IRequestDTOLoanUpdate loanUpdateDTO = new RequestDTOLoanUpdate(id, borrower, false);

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

            deleteLoan(loan.getId());

            return true;
        }
        catch(Exception exc)
        {

        }

        if(loan != null)
            deleteLoan(loan.getId());

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

    @Override
    public boolean isValidBorrowerIDForUpdate(int borrower)
    {
        ILoan sample = sampleLoan();
        boolean updatable = false;

        if(!sample.setBorrower(borrower))
            return false;

        updatable = updateLoan(sample);

        // Delete created test object again from server (don't care if successful)
        deleteLoan(sample.getId());

        return updatable;
    }
}
