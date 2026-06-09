package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import com.google.gson.Gson;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IResidentialAddress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Strictly or normally a model component (or rather consumer component here)
 * should not know exactly the implementation code for HTTP (in this case).
 * Anyway,
 * for usability and ease of use,
 * sometimes this strictness is ignored for the sake of quick implementations.
 */
public class BorrowerConsumer implements IBorrowerRepository
{
    private Gson gson;
    private HttpClient client;
    private String authority;

    public BorrowerConsumer(Gson gson, HttpClient client, String authority) throws IllegalArgumentException
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
    public IBorrower createBorrower(String firstname, String lastname, LocalDate birthday, IResidentialAddress address, double schufaScore) {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastname);
        Objects.requireNonNull(birthday);
        Objects.requireNonNull(address);

        int day = birthday.getDayOfMonth();
        int month = birthday.getMonthValue();
        int year = birthday.getYear();

        String street = address.getStreet();
        String no = address.getNo();
        String place = address.getPlace();
        int zip = address.getZip();

        String borrowerActionURI = authority + "/borrower/create/";
        URI uri = URI.create(borrowerActionURI);

        IRequestDTOBorrowerCreate borrowerCreateDTO = new RequestDTOBorrowerCreate(firstname, lastname, day, month, year, street, no, place, zip);

        String json = gson.toJson(borrowerCreateDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        String respJson = null;
        IResponseDTOBorrowerCreate respBorrowerCreate = null;

        try
        {
            resp = client.send(req, handl);

            if(resp.statusCode() == 200)
            {
                respObj = resp.body();

                if(respObj == null) return null;
                respJson = respObj.toString();
                respBorrowerCreate = gson.fromJson(respJson, ResponseDTOBorrowerCreate.class);

                if(respBorrowerCreate == null) return null;
                if(respBorrowerCreate.getGlobalResponseCode() != GlobalResponseCode.CREATE_BORROWER_CREATION_SUCCESS.ordinal()) return null;

                return respBorrowerCreate.getBorrower();
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IBorrower readBorrower(int id)
    {
        String borrowerActionURI = authority + "/borrower/read/";
        URI uri = URI.create(borrowerActionURI);

        IRequestDTOBorrowerRead borrowerReadDTO = new RequestDTOBorrowerRead(id);

        String json = gson.toJson(borrowerReadDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        String respJson = null;
        IResponseDTOBorrowerRead respBorrowerRead = null;

        try
        {
            resp = client.send(req, handl);

            if(resp.statusCode() == 200)
            {
                respObj = resp.body();

                if(respObj == null) return null;
                respJson = respObj.toString();
                respBorrowerRead = gson.fromJson(respJson, ResponseDTOBorrowerRead.class);

                if(respBorrowerRead == null) return null;
                if(respBorrowerRead.getGlobalResponseCode() != GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal()) return null;
                if(respBorrowerRead.getBorrowers() == null || respBorrowerRead.getBorrowers().length != 1) return null;

                return respBorrowerRead.getBorrowers()[0];
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IBorrower[] readAllBorrowers()
    {
        String borrowerActionURI = authority + "/borrower/read/";
        URI uri = URI.create(borrowerActionURI);

        IRequestDTOBorrowerRead borrowerReadDTO = new RequestDTOBorrowerRead(-1);

        String json = gson.toJson(borrowerReadDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        String respJson = null;
        IResponseDTOBorrowerRead respBorrowerRead = null;

        try
        {
            resp = client.send(req, handl);

            if(resp.statusCode() == 200)
            {
                respObj = resp.body();

                if(respObj == null) return null;
                respJson = respObj.toString();
                respBorrowerRead = gson.fromJson(respJson, ResponseDTOBorrowerRead.class);

                if(respBorrowerRead == null) return null;
                if(respBorrowerRead.getGlobalResponseCode() != GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal()) return null;
                if(respBorrowerRead.getBorrowers() == null) return null;

                return respBorrowerRead.getBorrowers();
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateBorrower(IBorrower borrower)
    {
        Objects.requireNonNull(borrower);

        int id = borrower.getId();

        String firstname = borrower.getFirstname();
        String lastname = borrower.getLastname();

        int day = borrower.getDateOfBirth().getDayOfMonth();
        int month = borrower.getDateOfBirth().getMonthValue();
        int year = borrower.getDateOfBirth().getYear();

        String street = borrower.getResidentialAddress().getStreet();
        String no = borrower.getResidentialAddress().getNo();
        String place = borrower.getResidentialAddress().getPlace();
        int zip = borrower.getResidentialAddress().getZip();

        int[] loans = borrower.getLoans();

        String borrowerActionURI = authority + "/borrower/update/";
        URI uri = URI.create(borrowerActionURI);

        IRequestDTOBorrowerUpdate borrowerUpdateDTO = new RequestDTOBorrowerUpdate(id, firstname, lastname, day, month, year, street, no, place, zip, loans);

        String json = gson.toJson(borrowerUpdateDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        String respJson = null;
        IResponseDTOBorrowerUpdate respBorrowerUpdate = null;

        try
        {
            resp = client.send(req, handl);

            if(resp.statusCode() == 200)
            {
                respObj = resp.body();

                if(respObj == null) return false;
                respJson = respObj.toString();
                respBorrowerUpdate = gson.fromJson(respJson, ResponseDTOBorrowerUpdate.class);

                if(respBorrowerUpdate == null) return false;

                return respBorrowerUpdate.getGlobalResponseCode() == GlobalResponseCode.UPDATE_BORROWER_SUCCESS.ordinal();
            }

            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBorrower(int id)
    {
        String borrowerActionURI = authority + "/borrower/delete/";
        URI uri = URI.create(borrowerActionURI);

        IRequestDTOBorrowerDelete borrowerDeleteDTO = new RequestDTOBorrowerDelete(id);

        String json = gson.toJson(borrowerDeleteDTO);

        HttpRequest.BodyPublisher pub = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest req = HttpRequest.newBuilder().POST(pub).uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        String respJson = null;
        IResponseDTOBorrowerDelete respBorrowerDelete = null;

        try
        {
            resp = client.send(req, handl);

            if(resp.statusCode() == 200)
            {
                respObj = resp.body();

                if(respObj == null) return false;
                respJson = respObj.toString();
                respBorrowerDelete = gson.fromJson(respJson, ResponseDTOBorrowerDelete.class);

                if(respBorrowerDelete == null) return false;

                return respBorrowerDelete.getGlobalResponseCode() == GlobalResponseCode.DELETE_BORROWER_SUCCESS.ordinal();
            }

            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isValidFirstnameForCreation(String firstname)
    {
        createBorrower();

        return false;
    }

    @Override
    public boolean isValidLastnameForCreation(String lastname)
    {
        return false;
    }

    @Override
    public boolean isValidDayOfBirthForCreation(int day)
    {
        return false;
    }

    @Override
    public boolean isValidMonthOfBirthForCreation(int month)
    {
        return false;
    }

    @Override
    public boolean isValidYearOfBirthForCreation(int year)
    {
        return false;
    }

    @Override
    public boolean isValidStreetForCreation(String street)
    {
        return false;
    }

    @Override
    public boolean isValidNoForCreation(String no)
    {
        return false;
    }

    @Override
    public boolean isValidPlaceForCreation(String place)
    {
        return false;
    }

    @Override
    public boolean isValidZipForCreation(int zip)
    {
        return false;
    }

    @Override
    public boolean isValidSchufaScoreForCreation(double schufaScore)
    {
        return false;
    }

    @Override
    public boolean isValidFirstnameForUpdate(String firstname)
    {
        return false;
    }

    @Override
    public boolean isValidLastnameForUpdate(String lastname)
    {
        return false;
    }

    @Override
    public boolean isValidDayOfBirthForUpdate(int day)
    {
        return false;
    }

    @Override
    public boolean isValidMonthOfBirthForUpdate(int month)
    {
        return false;
    }

    @Override
    public boolean isValidYearOfBirthForUpdate(int year)
    {
        return false;
    }

    @Override
    public boolean isValidStreetForUpdate(String street)
    {
        return false;
    }

    @Override
    public boolean isValidNoForUpdate(String no)
    {
        return false;
    }

    @Override
    public boolean isValidPlaceForUpdate(String place)
    {
        return false;
    }

    @Override
    public boolean isValidZipForUpdate(int zip)
    {
        return false;
    }

    @Override
    public boolean isValidSchufaScoreForUpdate(double schufaScore)
    {
        return false;
    }
}
