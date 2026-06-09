package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import com.google.gson.Gson;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.request.borrower.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower.ResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ResidentialAddress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
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
        if(firstname == null) return null;
        if(lastname == null) return null;
        if(birthday == null) return null;
        if(address == null) return null;

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

        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOBorrowerCreate respBorrowerCreate = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null) return null;

            if(resp.statusCode() != 200) return null;

            if(resp.body() == null) return null;

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return null;

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return null;

            System.out.println("Incoming response:\n\n"+respJson+"\n\n");

            respBorrowerCreate = gson.fromJson(respJson, ResponseDTOBorrowerCreate.class);

            if(respBorrowerCreate == null) return null;

            if(respBorrowerCreate.getGlobalResponseCode() != GlobalResponseCode.CREATE_BORROWER_CREATION_SUCCESS.ordinal()) return null;

            return respBorrowerCreate.getBorrower();
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
        String borrowerActionURI = authority + "/borrower/read/" + id;
        URI uri = URI.create(borrowerActionURI);

        HttpRequest req = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOBorrowerRead respBorrowerRead = null;

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

            respBorrowerRead = gson.fromJson(respJson, ResponseDTOBorrowerRead.class);

            if(respBorrowerRead == null) return null;

            if(respBorrowerRead.getGlobalResponseCode() != GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal()) return null;

            if(respBorrowerRead.getBorrowers() == null || respBorrowerRead.getBorrowers().length != 1) return null;

            return respBorrowerRead.getBorrowers()[0];
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
        String borrowerActionURI = authority + "/borrower/read/-1";
        URI uri = URI.create(borrowerActionURI);

        HttpRequest req = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpResponse.BodyHandler<String> handl = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> resp = null;

        Object respObj = null;
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOBorrowerRead respBorrowerRead = null;

        try
        {
            resp = client.send(req, handl);

            if(resp == null) return new IBorrower[0];

            if(resp.statusCode() != 200) return new IBorrower[0];

            respObj = resp.body();

            if(respObj == null) return new IBorrower[0];

            respBytes = resp.body().getBytes();

            if(respBytes.length == 0) return new IBorrower[0];

            respJson = new String(respBytes, StandardCharsets.UTF_8);

            if(respJson.isEmpty()) return new IBorrower[0];

            respBorrowerRead = gson.fromJson(respJson, ResponseDTOBorrowerRead.class);

            if(respBorrowerRead == null) return new IBorrower[0];

            if(respBorrowerRead.getGlobalResponseCode() != GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal()) return new IBorrower[0];

            if(respBorrowerRead.getBorrowers() == null) return new IBorrower[0];

            return respBorrowerRead.getBorrowers();
        }
        catch(Exception e)
        {
            e.printStackTrace();

            return new IBorrower[0];
        }
    }

    @Override
    public boolean updateBorrower(IBorrower borrower)
    {
        if(borrower == null) return false;

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
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOBorrowerUpdate respBorrowerUpdate = null;

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

            respBorrowerUpdate = gson.fromJson(respJson, ResponseDTOBorrowerUpdate.class);

            if(respBorrowerUpdate == null) return false;

            return respBorrowerUpdate.getGlobalResponseCode() == GlobalResponseCode.UPDATE_BORROWER_SUCCESS.ordinal();
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
        byte[] respBytes = null;
        String respJson = null;
        IResponseDTOBorrowerDelete respBorrowerDelete = null;

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

            respBorrowerDelete = gson.fromJson(respJson, ResponseDTOBorrowerDelete.class);

            if(respBorrowerDelete == null) return false;

            return respBorrowerDelete.getGlobalResponseCode() == GlobalResponseCode.DELETE_BORROWER_SUCCESS.ordinal();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private LocalDate sampleBirthday()
    {
        return LocalDate.of(IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_DAY_OF_BIRTH);
    }

    private IResidentialAddress sampleAddress()
    {
        return new ResidentialAddress(IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_PLACE);
    }

    private boolean isBorrowerCreatable(String firstname, String lastname, int day, int month, int year, String street, String no, String place, int zip, double schufaScore)
    {
        if(firstname == null) return false;
        if(lastname == null) return false;
        if(street == null) return false;
        if(no == null) return false;
        if(place == null) return false;

        IBorrower borrower = null;
        LocalDate birthday = null;
        IResidentialAddress address = null;

        try
        {
            birthday = LocalDate.of(year, month, day);
            address = new ResidentialAddress(street, no, zip, place);

            borrower = createBorrower(firstname, lastname, birthday, address, schufaScore);

            if(borrower == null) return false;

            deleteBorrower(borrower.getId());

            return true;
        }
        catch(Exception exc)
        {

        }

        if(borrower != null)
            deleteBorrower(borrower.getId());

        return false;
    }

    @Override
    public boolean isValidFirstnameForCreation(String firstname)
    {
        if(firstname == null) return false;

        return isBorrowerCreatable(firstname, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidLastnameForCreation(String lastname)
    {
        if(lastname == null) return false;

        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, lastname, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidDayOfBirthForCreation(int day)
    {
        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, day, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidMonthOfBirthForCreation(int month)
    {
        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, month, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidYearOfBirthForCreation(int year)
    {
        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, year, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidStreetForCreation(String street)
    {
        if(street == null) return false;

        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, street, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidNoForCreation(String no)
    {
        if(no == null) return false;

        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, no, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidPlaceForCreation(String place)
    {
        if(place == null) return false;

        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, place, IBorrower.EXAMPLE_ZIP, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidZipForCreation(int zip)
    {
        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, zip, IBorrower.EXAMPLE_SCHUFA_SCORE);
    }

    @Override
    public boolean isValidSchufaScoreForCreation(double schufaScore)
    {
        return isBorrowerCreatable(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, IBorrower.EXAMPLE_DAY_OF_BIRTH, IBorrower.EXAMPLE_MONTH_OF_BIRTH, IBorrower.EXAMPLE_YEAR_OF_BIRTH, IBorrower.EXAMPLE_STREET, IBorrower.EXAMPLE_NO, IBorrower.EXAMPLE_PLACE, IBorrower.EXAMPLE_ZIP, schufaScore);
    }

    private IBorrower sampleBorrower()
    {
        return createBorrower(IBorrower.EXAMPLE_FIRSTNAME, IBorrower.EXAMPLE_LASTNAME, sampleBirthday(), sampleAddress(), 0.9d);
    }

    @Override
    public boolean isValidFirstnameForUpdate(String firstname)
    {
        if(firstname == null) return false;

        IBorrower sample = sampleBorrower();
        boolean updatable = false;

        if(!sample.setFirstname(firstname))
            return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidLastnameForUpdate(String lastname)
    {
        if(lastname == null) return false;

        IBorrower sample = sampleBorrower();
        boolean updatable = false;

        if(!sample.setLastname(lastname))
            return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidDayOfBirthForUpdate(int day)
    {
        IBorrower sample = sampleBorrower();
        int currentMonth = sample.getDateOfBirth().getMonthValue();
        int currentYear = sample.getDateOfBirth().getYear();
        LocalDate updatedDate = null;
        boolean updatable = false;

        try
        {
            updatedDate = LocalDate.of(currentYear, currentMonth, day);
        }
        catch (DateTimeException exc)
        {
            return false;
        }

        if(!sample.setDateOfBirth(updatedDate))
            return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidMonthOfBirthForUpdate(int month)
    {
        IBorrower sample = sampleBorrower();
        int currentDay = sample.getDateOfBirth().getDayOfMonth();
        int currentYear = sample.getDateOfBirth().getYear();
        LocalDate updatedDate = null;
        boolean updatable = false;

        try
        {
            updatedDate = LocalDate.of(currentYear, month, currentDay);
        }
        catch (NumberFormatException exc)
        {
            return false;
        }

        if(!sample.setDateOfBirth(updatedDate))
            return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidYearOfBirthForUpdate(int year)
    {
        IBorrower sample = sampleBorrower();
        int currentDay = sample.getDateOfBirth().getDayOfMonth();
        int currentMonth = sample.getDateOfBirth().getMonthValue();
        LocalDate updatedDate = null;
        boolean updatable = false;

        try
        {
            updatedDate = LocalDate.of(year, currentMonth, currentDay);
        }
        catch (Exception e)
        {
            return false;
        }

        if(!sample.setDateOfBirth(updatedDate))
            return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidStreetForUpdate(String street)
    {
        if(street == null) return false;

        IBorrower sample = sampleBorrower();
        IResidentialAddress updatedAddress = sample.getResidentialAddress();
        boolean updatable = false;

        if(!updatedAddress.setStreet(street)) return false;
        if(!sample.setResidentialAddress(updatedAddress)) return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidNoForUpdate(String no)
    {
        if(no == null) return false;

        IBorrower sample = sampleBorrower();
        IResidentialAddress updatedAddress = sample.getResidentialAddress();
        boolean updatable = false;

        if(!updatedAddress.setNo(no)) return false;
        if(!sample.setResidentialAddress(updatedAddress)) return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidPlaceForUpdate(String place)
    {
        if(place == null) return false;

        IBorrower sample = sampleBorrower();
        IResidentialAddress updatedAddress = sample.getResidentialAddress();
        boolean updatable = false;

        if(!updatedAddress.setPlace(place)) return false;
        if(!sample.setResidentialAddress(updatedAddress)) return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidZipForUpdate(int zip)
    {
        IBorrower sample = sampleBorrower();
        IResidentialAddress updatedAddress = sample.getResidentialAddress();
        boolean updatable = false;

        if(!updatedAddress.setZip(zip)) return false;
        if(!sample.setResidentialAddress(updatedAddress)) return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }

    @Override
    public boolean isValidSchufaScoreForUpdate(double schufaScore)
    {
        IBorrower sample = sampleBorrower();
        boolean updatable = false;

        if(!sample.setSchufaScore(schufaScore)) return false;

        updatable = updateBorrower(sample);

        // Delete created test object again from server (don't care if successful)
        deleteBorrower(sample.getId());

        return updatable;
    }
}
