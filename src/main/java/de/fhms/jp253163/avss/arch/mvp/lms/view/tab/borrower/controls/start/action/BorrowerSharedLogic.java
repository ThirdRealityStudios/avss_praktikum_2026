package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerNamePanel;

import java.time.DateTimeException;
import java.time.LocalDate;

public class BorrowerSharedLogic
{
    public static RequestDTOBorrowerCreate genCreateLoanDTO(IView view, BorrowerTextfieldPanel textfieldPanel)
    {
        BorrowerNamePanel namePanel = textfieldPanel.getNamePanel();
        BorrowerBirthdayPanel birthdayPanel = textfieldPanel.getBirthdayPanel();
        BorrowerAddressPanel addressPanel = textfieldPanel.getAddressPanel();

        String firstname = namePanel.getFirstname().getJTextField().getText(),
                lastname = namePanel.getLastname().getJTextField().getText(),
                day = birthdayPanel.getDay().getJTextField().getText(),
                month = birthdayPanel.getMonth().getJTextField().getText(),
                year = birthdayPanel.getYear().getJTextField().getText(),
                street = addressPanel.getStreet().getJTextField().getText(),
                no = addressPanel.getNo().getJTextField().getText(),
                place = addressPanel.getPlace().getJTextField().getText(),
                zip = addressPanel.getZip().getJTextField().getText();

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

        try
        {
            zipParsed = Integer.parseInt(zip);
        }
        catch (NumberFormatException nfe)
        {
            view.showMessage("Incorrect zip code! (not a number)");

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
            view.showMessage("Incorrect birthday! (not numbers everywhere)");

            return null;
        }

        LocalDate birthdayParsed = null;

        try
        {
            birthdayParsed = LocalDate.of(yearParsed, monthParsed, dayParsed);
        }
        catch (DateTimeException dte)
        {
            view.showMessage("Incorrect birthday! (not a valid date)");

            return null;
        }

        return new RequestDTOBorrowerCreate(firstname, lastname, street, no, place, zipParsed, birthdayParsed);
    }

    public static RequestDTOBorrowerUpdate genUpdateLoanDTO(IView view, BorrowerTextfieldPanel textfieldPanel)
    {
        RequestDTOBorrowerCreate helpingDTO = genCreateLoanDTO(view, textfieldPanel);

        String id = "-1"; // TODO createUpdatePanel.getView().get....getFocusedId();
        if(id == null) id = "";

        int idParsed = -1;
        try
        {
            idParsed = Integer.parseInt(id);
        }
        catch (NumberFormatException nfe)
        {
            view.showMessage("Borrower ID is not a number!");

            return null;
        }

        LocalDate birthday = LocalDate.of(helpingDTO.getYearOfBirth(), helpingDTO.getMonthOfBirth(), helpingDTO.getDayOfBirth());

        return new RequestDTOBorrowerUpdate(idParsed, helpingDTO.getFirstname(), helpingDTO.getLastname(), birthday, helpingDTO.getStreet(), helpingDTO.getNo(), helpingDTO.getPlace(), helpingDTO.getZip(), null);
    }
}
