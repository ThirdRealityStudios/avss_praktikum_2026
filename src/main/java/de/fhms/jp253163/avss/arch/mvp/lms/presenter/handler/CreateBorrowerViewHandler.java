package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ResidentialAddress;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;

import java.time.DateTimeException;
import java.time.LocalDate;

public class CreateBorrowerViewHandler implements ICreateBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private IPresenter presenter;

    public CreateBorrowerViewHandler(IBorrowerRepository borrowerRepository, IPresenter presenter) throws NullPointerException
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
        System.out.println("Day: " + day);
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

        if(firstname.isEmpty())
        {
            presenter.getView().showMessage("Please fill in firstname!");
            return;
        }

        if(lastname.isEmpty())
        {
            presenter.getView().showMessage("Please fill in lastname!");
            return;
        }

        if(day < 1)
        {
            presenter.getView().showMessage("Please fill in a valid day!");
            return;
        }

        if(month < 1)
        {
            presenter.getView().showMessage("Please fill in a valid month!");
            return;
        }

        if(year < 1910)
        {
            presenter.getView().showMessage("Please fill in a valid year!");
            return;
        }

        if(street.isEmpty())
        {
            presenter.getView().showMessage("Please fill in street!");
            return;
        }

        if(no.isEmpty())
        {
            presenter.getView().showMessage("Please fill in no!");
            return;
        }

        if(place.isEmpty())
        {
            presenter.getView().showMessage("Please fill in place!");
            return;
        }

        if(zip < 0)
        {
            presenter.getView().showMessage("Please fill in a valid zip!");
            return;
        }

        try
        {
            birthday = LocalDate.of(year, month, day);
        }
        catch (DateTimeException e)
        {
            presenter.getView().showMessage("Could not process birthday!");
            return;
        }

        IResidentialAddress residentialAddress = null;

        try
        {
            residentialAddress = new ResidentialAddress(street, no, zip, place);
        }
        catch (IllegalArgumentException e)
        {
            presenter.getView().showMessage("Could not process address!");

            return;
        }

        double schufaScore = 0.9d; // TODO Check if valid

        IBorrower borrower = borrowerRepository.createBorrower(firstname, lastname, birthday, residentialAddress, schufaScore);

        if(borrower == null)
            presenter.getView().showMessage("Could not create borrower!");
        else
            presenter.getView().showMessage("Created borrower successfully!");
    }
}
