package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class BorrowerSharedLogic
{
    private BorrowerCreateUpdatePanel createUpdatePanel;

    public BorrowerSharedLogic(BorrowerCreateUpdatePanel createUpdatePanel)
    {
        Objects.requireNonNull(createUpdatePanel);

        this.createUpdatePanel = createUpdatePanel;
    }

    public BorrowerSharedLogicDTO prepareUserEntries(boolean ignoreID)
    {
        String id = ignoreID ? "-1" : "-1", // createUpdatePanel.getView().get....getFocusedId();
                firstname = createUpdatePanel.getNamePanel().getFirstname().getJTextField().getText(),
                lastname = createUpdatePanel.getNamePanel().getLastname().getJTextField().getText(),
                day = createUpdatePanel.getBirthdayPanel().getDay().getJTextField().getText(),
                month = createUpdatePanel.getBirthdayPanel().getMonth().getJTextField().getText(),
                year = createUpdatePanel.getBirthdayPanel().getYear().getJTextField().getText(),
                street = createUpdatePanel.getAddressPanel().getStreet().getJTextField().getText(),
                no = createUpdatePanel.getAddressPanel().getNo().getJTextField().getText(),
                place = createUpdatePanel.getAddressPanel().getPlace().getJTextField().getText(),
                zip = createUpdatePanel.getAddressPanel().getZip().getJTextField().getText();

        if(id == null) id = "";
        if(firstname == null) firstname = "";
        if(lastname == null) lastname = "";
        if(day == null) day = "";
        if(month == null) month = "";
        if(year == null) year = "";
        if(street == null) street = "";
        if(no == null) no = "";
        if(place == null) place = "";
        if(zip == null) zip = "";

        int zipParsed = -1;
        int idParsed = -1;

        try
        {
            idParsed = Integer.parseInt(id);
        }
        catch (NumberFormatException nfe)
        {
            createUpdatePanel.getView().showMessage("Cannot update borrower! (ID corrupted)");

            return null;
        }

        try
        {
            zipParsed = Integer.parseInt(zip);
        }
        catch (NumberFormatException nfe)
        {
            createUpdatePanel.getView().showMessage("Incorrect zip code! (not a number)");

            return null;
        }

        int yearParsed = -1, monthParsed = -1, dayParsed = -1;

        try
        {
            yearParsed = Integer.parseInt(year);
            monthParsed = Integer.parseInt(month);
            dayParsed = Integer.parseInt(day);
        }
        catch (NumberFormatException nfe)
        {
            createUpdatePanel.getView().showMessage("Incorrect birthday! (not everywhere numbers)");

            return null;
        }

        LocalDate birthdayParsed = null;

        try
        {
            birthdayParsed = LocalDate.of(yearParsed, monthParsed, dayParsed);
        }
        catch (DateTimeException dte)
        {
            createUpdatePanel.getView().showMessage("Incorrect birthday! (not a valid date)");

            return null;
        }

        BorrowerSharedLogicDTO sharedLogicDTO = new BorrowerSharedLogicDTO(idParsed, firstname, lastname, birthdayParsed, street, no, place, zipParsed);

        return sharedLogicDTO;
    }
}
