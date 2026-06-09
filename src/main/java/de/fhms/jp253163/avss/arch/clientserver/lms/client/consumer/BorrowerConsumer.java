package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import com.google.gson.Gson;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerCreate;
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
        HttpResponse resp = null;

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
    public IBorrower readBorrower(int i) {
        return null;
    }

    @Override
    public IBorrower[] readAllBorrowers() {
        return new IBorrower[0];
    }

    @Override
    public boolean updateBorrower(IBorrower iBorrower) {
        return false;
    }

    @Override
    public boolean deleteBorrower(int i) {
        return false;
    }

    @Override
    public boolean isValidFirstnameForCreation(String s) {
        return false;
    }

    @Override
    public boolean isValidLastnameForCreation(String s) {
        return false;
    }

    @Override
    public boolean isValidDayOfBirthForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidMonthOfBirthForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidYearOfBirthForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidStreetForCreation(String s) {
        return false;
    }

    @Override
    public boolean isValidNoForCreation(String s) {
        return false;
    }

    @Override
    public boolean isValidPlaceForCreation(String s) {
        return false;
    }

    @Override
    public boolean isValidZipForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidSchufaScoreForCreation(double v) {
        return false;
    }

    @Override
    public boolean isValidFirstnameForUpdate(String s) {
        return false;
    }

    @Override
    public boolean isValidLastnameForUpdate(String s) {
        return false;
    }

    @Override
    public boolean isValidDayOfBirthForUpdate(int i) {
        return false;
    }

    @Override
    public boolean isValidMonthOfBirthForUpdate(int i) {
        return false;
    }

    @Override
    public boolean isValidYearOfBirthForUpdate(int i) {
        return false;
    }

    @Override
    public boolean isValidStreetForUpdate(String s) {
        return false;
    }

    @Override
    public boolean isValidNoForUpdate(String s) {
        return false;
    }

    @Override
    public boolean isValidPlaceForUpdate(String s) {
        return false;
    }

    @Override
    public boolean isValidZipForUpdate(int i) {
        return false;
    }

    @Override
    public boolean isValidSchufaScoreForUpdate(double v) {
        return false;
    }
}
