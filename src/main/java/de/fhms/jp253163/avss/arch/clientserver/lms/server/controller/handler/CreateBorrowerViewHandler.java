package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.IController;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ResidentialAddress;

import java.time.DateTimeException;
import java.time.LocalDate;

public class CreateBorrowerViewHandler implements ICreateBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;

    public CreateBorrowerViewHandler(IBorrowerRepository borrowerRepository, IController presenter) throws NullPointerException
    {
        if(borrowerRepository == null || presenter == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public int invoke(IRequestDTOBorrowerCreate iRequestDTOBorrowerCreate)
    {
        if(iRequestDTOBorrowerCreate == null)
        {
            return GlobalResponseCode.BORROWER_INVALID_CREATION_REQUEST.ordinal();
        }

        String firstname = iRequestDTOBorrowerCreate.getFirstname();
        String lastname = iRequestDTOBorrowerCreate.getLastname();

        int day = iRequestDTOBorrowerCreate.getDayOfBirth();
        int month = iRequestDTOBorrowerCreate.getMonthOfBirth();
        int year = iRequestDTOBorrowerCreate.getYearOfBirth();

        LocalDate birthday = null;

        String street = iRequestDTOBorrowerCreate.getStreet();
        String no = iRequestDTOBorrowerCreate.getNo();
        int zip = iRequestDTOBorrowerCreate.getZip();
        String place = iRequestDTOBorrowerCreate.getPlace();

        if(firstname == null) firstname = "";
        if(lastname == null) lastname = "";
        if(street == null) street = "";
        if(no == null) no = "";
        if(place == null) place = "";

        if(!borrowerRepository.isValidFirstnameForCreation(firstname))
        {
            return GlobalResponseCode.BORROWER_INVALID_FIRSTNAME.ordinal();
        }

        if(!borrowerRepository.isValidLastnameForCreation(lastname))
        {
            return GlobalResponseCode.BORROWER_INVALID_LASTNAME.ordinal();
        }

        if(!borrowerRepository.isValidDayOfBirthForCreation(day))
        {
            return GlobalResponseCode.BORROWER_INVALID_DAY_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidMonthOfBirthForCreation(month))
        {
            return GlobalResponseCode.BORROWER_INVALID_MONTH_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidYearOfBirthForCreation(year))
        {
            return GlobalResponseCode.BORROWER_INVALID_YEAR_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidStreetForCreation(street))
        {
            return GlobalResponseCode.BORROWER_INVALID_STREET.ordinal();
        }

        if(!borrowerRepository.isValidNoForCreation(no))
        {
            return GlobalResponseCode.BORROWER_INVALID_NO.ordinal();
        }

        if(!borrowerRepository.isValidPlaceForCreation(place))
        {
            return GlobalResponseCode.BORROWER_INVALID_PLACE.ordinal();
        }

        if(!borrowerRepository.isValidZipForCreation(zip))
        {
            return GlobalResponseCode.BORROWER_INVALID_ZIP.ordinal();
        }

        try
        {
            birthday = LocalDate.of(year, month, day);
        }
        catch (DateTimeException e)
        {
            return GlobalResponseCode.BORROWER_BIRTHDAY_PARSE_ERROR.ordinal();
        }

        IResidentialAddress residentialAddress = null;

        try
        {
            residentialAddress = new ResidentialAddress(street, no, zip, place);
        }
        catch (IllegalArgumentException e)
        {
            return GlobalResponseCode.BORROWER_ADDRESS_PARSE_ERROR.ordinal();
        }

        double schufaScore = 0.9d; // TODO Check if valid

        IBorrower borrower = borrowerRepository.createBorrower(firstname, lastname, birthday, residentialAddress, schufaScore);

        if(borrower == null)
            return GlobalResponseCode.BORROWER_CREATION_ERROR.ordinal();
        else
        {
            // Make the borrower table show all datasets now.
            return GlobalResponseCode.BORROWER_CREATION_SUCCESS.ordinal();
        }
    }
}
