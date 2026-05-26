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
    public void invoke(IRequestDTOBorrowerUpdate iRequestDTOBorrowerUpdate) {
        if(iRequestDTOBorrowerUpdate == null)
        {
            presenter.getView().showMessage("Invalid syntax!");

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
        int month = iRequestDTOBorrowerUpdate.getMonthOfBirth();
        int year = iRequestDTOBorrowerUpdate.getYearOfBirth();

        int[] loans = iRequestDTOBorrowerUpdate.getLoans();

        IBorrower currentBorrower = borrowerRepository.readBorrower(borrowerID);

        if(currentBorrower == null)
        {
            presenter.getView().showMessage("Could not find that borrower!");

            return;
        }

        if(firstname != null)
            currentBorrower.setFirstname(firstname);

        if(lastname != null)
            currentBorrower.setLastname(lastname);

        IResidentialAddress address = currentBorrower.getResidentialAddress();

        if(address != null)
        {
            if(street != null)
                address.setStreet(street);

            if(no != null)
                address.setNo(no);

            if(place != null)
                address.setPlace(place);

            address.setZip(zip);

            currentBorrower.setResidentialAddress(address);
        }

        LocalDate birthday = currentBorrower.getDateOfBirth();

        if(year < 1 || year > 9999)
            year = birthday.getYear();

        if(month < 1 || month > 12)
            month = birthday.getMonthValue();

        if(day < 1 || day > 31)
            day = birthday.getDayOfMonth();

        try
        {
            LocalDate updatedBirthday = LocalDate.of(year, month, day);

            currentBorrower.setDateOfBirth(updatedBirthday);

            if(loans != null)
            {
                currentBorrower.setLoans(loans);
            }

            if(borrowerRepository.updateBorrower(currentBorrower))
                presenter.getView().showMessage("Updated borrower successfully!");
            else
                presenter.getView().showMessage("Failed to update borrower!");
        }
        catch(DateTimeException e)
        {

        }
    }
}
