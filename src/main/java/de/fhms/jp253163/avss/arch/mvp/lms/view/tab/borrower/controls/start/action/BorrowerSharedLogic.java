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
    public static RequestDTOBorrowerCreate genCreateBorrowerDTO(IView view, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
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

        int zipParsed = -1;

        try
        {
            zipParsed = Integer.parseInt(zip);
        }
        catch (NumberFormatException nfe)
        {
            zipParsed = -1;
        }

        int yearParsed = -1, monthParsed = -1, dayParsed = -1;
        LocalDate birthdayParsed = null;

        try
        {
            yearParsed = Integer.parseInt(year);
            monthParsed = Integer.parseInt(month);
            dayParsed = Integer.parseInt(day);

            try
            {
                birthdayParsed = LocalDate.of(yearParsed, monthParsed, dayParsed);
            }
            catch (DateTimeException dte)
            {
                birthdayParsed = null;
            }
        }
        catch (NumberFormatException nfe)
        {
            birthdayParsed = null;
        }

        return new RequestDTOBorrowerCreate(firstname, lastname, street, no, place, zipParsed, birthdayParsed);
    }

    public static RequestDTOBorrowerUpdate genUpdateBorrowerDTO(IView view, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        RequestDTOBorrowerCreate helpingDTO = genCreateBorrowerDTO(view, textfieldPanel);

        String id = "-1"; // TODO createUpdatePanel.getView().get....getFocusedId();

        int idParsed = -1;
        try
        {
            idParsed = Integer.parseInt(id);
        }
        catch (NumberFormatException nfe)
        {
            idParsed = -1;
        }

        LocalDate birthdayParsed = null;

        try
        {
            birthdayParsed = LocalDate.of(helpingDTO.getYearOfBirth(), helpingDTO.getMonthOfBirth(), helpingDTO.getDayOfBirth());
        }
        catch (DateTimeException e)
        {
            birthdayParsed = null;
        }

        return new RequestDTOBorrowerUpdate(idParsed, helpingDTO.getFirstname(), helpingDTO.getLastname(), birthdayParsed, helpingDTO.getStreet(), helpingDTO.getNo(), helpingDTO.getPlace(), helpingDTO.getZip(), null);
    }
}
