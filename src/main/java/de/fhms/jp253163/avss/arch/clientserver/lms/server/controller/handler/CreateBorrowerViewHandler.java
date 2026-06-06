package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.IController;
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
            return 0;
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
            return 1;
        }

        if(!borrowerRepository.isValidLastnameForCreation(lastname))
        {
            return 2;
        }

        if(!borrowerRepository.isValidDayOfBirthForCreation(day))
        {
            return 3;
        }

        if(!borrowerRepository.isValidMonthOfBirthForCreation(month))
        {
            return 4;
        }

        if(!borrowerRepository.isValidYearOfBirthForCreation(year))
        {
            return 5;
        }

        if(!borrowerRepository.isValidStreetForCreation(street))
        {
            return 6;
        }

        if(!borrowerRepository.isValidNoForCreation(no))
        {
            return 7;
        }

        if(!borrowerRepository.isValidPlaceForCreation(place))
        {
            return 8;
        }

        if(!borrowerRepository.isValidZipForCreation(zip))
        {
            return 9;
        }

        try
        {
            birthday = LocalDate.of(year, month, day);
        }
        catch (DateTimeException e)
        {
            return 10;
        }

        IResidentialAddress residentialAddress = null;

        try
        {
            residentialAddress = new ResidentialAddress(street, no, zip, place);
        }
        catch (IllegalArgumentException e)
        {
            return 11;
        }

        double schufaScore = 0.9d; // TODO Check if valid

        IBorrower borrower = borrowerRepository.createBorrower(firstname, lastname, birthday, residentialAddress, schufaScore);

        if(borrower == null)
            return 12;
        else
        {
            // Make the borrower table show all datasets now.
            return 13;
        }
    }
}
