package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class CreateBorrowerViewHandler implements ICreateBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private IController presenter;

    public CreateBorrowerViewHandler(IBorrowerRepository borrowerRepository, IController presenter) throws NullPointerException
    {
        if(borrowerRepository == null || presenter == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOBorrowerCreate iRequestDTOBorrowerCreate)
    {
        if(iRequestDTOBorrowerCreate == null)
        {
            presenter.getView().showMessage("Invalid creation request!");

            return;
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
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_FIRSTNAME);
            return;
        }

        if(!borrowerRepository.isValidLastnameForCreation(lastname))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_LASTNAME);
            return;
        }

        if(!borrowerRepository.isValidDayOfBirthForCreation(day))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_DAY_OF_BIRTH);
            return;
        }

        if(!borrowerRepository.isValidMonthOfBirthForCreation(month))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_MONTH_OF_BIRTH);
            return;
        }

        if(!borrowerRepository.isValidYearOfBirthForCreation(year))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_YEAR_OF_BIRTH);
            return;
        }

        if(!borrowerRepository.isValidStreetForCreation(street))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_STREET);
            return;
        }

        if(!borrowerRepository.isValidNoForCreation(no))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_NO);
            return;
        }

        if(!borrowerRepository.isValidPlaceForCreation(place))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_PLACE);
            return;
        }

        if(!borrowerRepository.isValidZipForCreation(zip))
        {
            presenter.getView().showMessage(BorrowerFieldErrorMessage.INVALID_ZIP);
            return;
        }

        try
        {
            birthday = LocalDate.of(year, month, day);
        }
        catch (DateTimeException e)
        {
            presenter.getView().showMessage("Could not process the provided birthday! Is it really correct?");
            return;
        }

        IResidentialAddress residentialAddress = null;

        try
        {
            residentialAddress = new ResidentialAddress(street, no, zip, place);
        }
        catch (IllegalArgumentException e)
        {
            presenter.getView().showMessage("Could not process the provided address!");

            return;
        }

        double schufaScore = 0.9d; // TODO Check if valid

        IBorrower borrower = borrowerRepository.createBorrower(firstname, lastname, birthday, residentialAddress, schufaScore);

        if(borrower == null)
            presenter.getView().showMessage("Could not create borrower!");
        else
        {
            // Make the borrower table show all datasets now.
            new ReadBorrowerViewHandler(borrowerRepository, presenter).invoke(new RequestDTOBorrowerRead(-1));

            presenter.getView().showMessage("Created borrower successfully!");
        }
    }
}
