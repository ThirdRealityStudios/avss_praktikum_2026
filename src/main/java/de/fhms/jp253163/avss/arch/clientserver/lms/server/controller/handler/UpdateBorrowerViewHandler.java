package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class UpdateBorrowerViewHandler implements IUpdateBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;

    public UpdateBorrowerViewHandler(IBorrowerRepository borrowerRepository) throws NullPointerException
    {
        Objects.requireNonNull(borrowerRepository);

        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public int invoke(IRequestDTOBorrowerUpdate iRequestDTOBorrowerUpdate)
    {
        if(iRequestDTOBorrowerUpdate == null)
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_REQUEST.ordinal();
        }

        int borrowerID = iRequestDTOBorrowerUpdate.getBorrowerID();

        String firstname = iRequestDTOBorrowerUpdate.getFirstname();
        String lastname = iRequestDTOBorrowerUpdate.getLastname();

        String street = iRequestDTOBorrowerUpdate.getStreet();
        String no = iRequestDTOBorrowerUpdate.getNo();
        String place = iRequestDTOBorrowerUpdate.getPlace();
        int zip = iRequestDTOBorrowerUpdate.getZip();

        int day = iRequestDTOBorrowerUpdate.getDayOfBirth();
        int month = iRequestDTOBorrowerUpdate.getMonthOfBirth();
        int year = iRequestDTOBorrowerUpdate.getYearOfBirth();

        int[] loans = iRequestDTOBorrowerUpdate.getLoans();

        if(borrowerID < 0)
        {
            return GlobalResponseCode.UPDATE_BORROWER_NO_SELECTED_DATASET.ordinal();
        }

        if(!borrowerRepository.isValidFirstnameForUpdate(firstname))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_FIRSTNAME.ordinal();
        }

        if(!borrowerRepository.isValidLastnameForUpdate(lastname))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_LASTNAME.ordinal();
        }

        if(!borrowerRepository.isValidDayOfBirthForUpdate(day))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_DAY_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidMonthOfBirthForUpdate(month))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_MONTH_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidYearOfBirthForUpdate(year))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_YEAR_OF_BIRTH.ordinal();
        }

        if(!borrowerRepository.isValidStreetForUpdate(street))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_STREET.ordinal();
        }

        if(!borrowerRepository.isValidNoForUpdate(no))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_NO.ordinal();
        }

        if(!borrowerRepository.isValidPlaceForUpdate(place))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_PLACE.ordinal();
        }

        if(!borrowerRepository.isValidZipForUpdate(zip))
        {
            return GlobalResponseCode.UPDATE_BORROWER_INVALID_ZIP.ordinal();
        }

        IBorrower currentBorrower = borrowerRepository.readBorrower(borrowerID);

        if(currentBorrower == null)
        {
            return GlobalResponseCode.UPDATE_BORROWER_NOT_FOUND.ordinal();
        }

        // Below now: checks if values should stay the same, so use the old values where necessary
        if(firstname == null) firstname = currentBorrower.getFirstname();

        if(lastname == null) lastname = currentBorrower.getLastname();

        IResidentialAddress address = currentBorrower.getResidentialAddress();

        if(address == null)
        {
            return GlobalResponseCode.UPDATE_BORROWER_ADDRESS_PARSE_ERROR.ordinal();
        }

        if(street == null) lastname = currentBorrower.getResidentialAddress().getStreet();

        if(no == null) no = currentBorrower.getResidentialAddress().getNo();

        if(place == null) place = currentBorrower.getResidentialAddress().getPlace();

        if(loans == null) loans = currentBorrower.getLoans();

        currentBorrower.setFirstname(firstname);
        currentBorrower.setLastname(lastname);

        address.setStreet(street);
        address.setNo(no);
        address.setPlace(place);
        address.setZip(zip);

        currentBorrower.setResidentialAddress(address);

        LocalDate updatedBirthday = null;

        try
        {
            updatedBirthday = LocalDate.of(year, month, day);
        }
        catch(DateTimeException e)
        {
            return GlobalResponseCode.UPDATE_BORROWER_BIRTHDAY_PARSE_ERROR.ordinal();
        }

        currentBorrower.setDateOfBirth(updatedBirthday);

        if(loans != null) // does not show message because it is ruled on the loan tab page.
        {
            currentBorrower.setLoans(loans);
        }

        if(borrowerRepository.updateBorrower(currentBorrower))
        {
            return GlobalResponseCode.UPDATE_BORROWER_SUCCESS.ordinal();
        }
        else
            return GlobalResponseCode.UPDATE_BORROWER_ERROR.ordinal();
    }
}
