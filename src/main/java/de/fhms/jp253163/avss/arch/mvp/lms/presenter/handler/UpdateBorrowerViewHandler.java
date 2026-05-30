package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;

import java.time.DateTimeException;
import java.time.LocalDate;

public class UpdateBorrowerViewHandler implements IUpdateBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private IPresenter presenter;

    public UpdateBorrowerViewHandler(IBorrowerRepository borrowerRepository, IPresenter presenter) throws NullPointerException
    {
        if(borrowerRepository == null || presenter == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOBorrowerUpdate iRequestDTOBorrowerUpdate)
    {
        if(iRequestDTOBorrowerUpdate == null)
        {
            presenter.getView().showMessage("Invalid update request!");

            return;
        }

        int borrowerID = iRequestDTOBorrowerUpdate.getBorrowerID();

        String firstname = iRequestDTOBorrowerUpdate.getFirstname();
        String lastname = iRequestDTOBorrowerUpdate.getLastname();

        String street = iRequestDTOBorrowerUpdate.getStreet();
        String no = iRequestDTOBorrowerUpdate.getNo();
        String place = iRequestDTOBorrowerUpdate.getPlace();
        int zip = iRequestDTOBorrowerUpdate.getZip();

        int day = iRequestDTOBorrowerUpdate.getDayOfBirth();
        System.out.println("Day: " + day);
        int month = iRequestDTOBorrowerUpdate.getMonthOfBirth();
        int year = iRequestDTOBorrowerUpdate.getYearOfBirth();

        int[] loans = iRequestDTOBorrowerUpdate.getLoans();

        if(borrowerID < 0)
        {
            presenter.getView().showMessage("Please fill in a valid borrower ID!");
            return;
        }

        if(firstname != null && firstname.isEmpty())
        {
            presenter.getView().showMessage("Please fill in firstname!");
            return;
        }

        if(lastname != null && lastname.isEmpty())
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

        if(street != null && street.isEmpty())
        {
            presenter.getView().showMessage("Please fill in street!");
            return;
        }

        if(no != null && no.isEmpty())
        {
            presenter.getView().showMessage("Please fill in no!");
            return;
        }

        if(place != null && place.isEmpty())
        {
            presenter.getView().showMessage("Please fill in place!");
            return;
        }

        if(zip < 0)
        {
            presenter.getView().showMessage("Please fill in a valid zip!");
            return;
        }

        IBorrower currentBorrower = borrowerRepository.readBorrower(borrowerID);

        if(currentBorrower == null)
        {
            presenter.getView().showMessage("Could not find that borrower!");
            return;
        }

        // Below now: checks if values should stay the same, so use the old values where necessary
        if(firstname == null) firstname = currentBorrower.getFirstname();

        if(lastname == null) lastname = currentBorrower.getLastname();

        IResidentialAddress address = currentBorrower.getResidentialAddress();

        if(address == null)
        {
            presenter.getView().showMessage("Could not process old address!");
            return;
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

        // LocalDate birthday = currentBorrower.getDateOfBirth();

        /*
        if(year < 1 || year > 9999)
        {
            year = birthday.getYear();

        }

        if(month < 1 || month > 12)
            month = birthday.getMonthValue();

        if(day < 1 || day > 31)
            day = birthday.getDayOfMonth();
         */

        LocalDate updatedBirthday = null;

        try
        {
            updatedBirthday = LocalDate.of(year, month, day);
        }
        catch(DateTimeException e)
        {
            presenter.getView().showMessage("Could not process new birthday!");
            return;
        }

        currentBorrower.setDateOfBirth(updatedBirthday);

        if(loans != null) // does not show message because it is ruled on the loan tab page.
        {
            currentBorrower.setLoans(loans);
        }

        if(borrowerRepository.updateBorrower(currentBorrower))
            presenter.getView().showMessage("Updated borrower successfully!");
        else
            presenter.getView().showMessage("Failed to update borrower!");
    }
}
