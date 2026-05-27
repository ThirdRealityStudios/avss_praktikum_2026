package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerNamePanel;

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
        BorrowerNamePanel namePanel = createUpdatePanel.getTextfieldPanel().getNamePanel();
        BorrowerBirthdayPanel birthdayPanel = createUpdatePanel.getTextfieldPanel().getBirthdayPanel();
        BorrowerAddressPanel addressPanel = createUpdatePanel.getTextfieldPanel().getAddressPanel();

        String id = ignoreID ? "-1" : "-1", // createUpdatePanel.getView().get....getFocusedId();
                firstname = namePanel.getFirstname().getJTextField().getText(),
                lastname = namePanel.getLastname().getJTextField().getText(),
                day = birthdayPanel.getDay().getJTextField().getText(),
                month = birthdayPanel.getMonth().getJTextField().getText(),
                year = birthdayPanel.getYear().getJTextField().getText(),
                street = addressPanel.getStreet().getJTextField().getText(),
                no = addressPanel.getNo().getJTextField().getText(),
                place = addressPanel.getPlace().getJTextField().getText(),
                zip = addressPanel.getZip().getJTextField().getText();

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
