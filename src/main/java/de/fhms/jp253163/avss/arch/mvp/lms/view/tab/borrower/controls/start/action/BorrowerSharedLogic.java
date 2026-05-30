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
    public static RequestDTOBorrowerCreate genCreateBorrowerDTO(BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
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

        try
        {
            yearParsed = Integer.parseInt(year);
        }
        catch (NumberFormatException e)
        {
            yearParsed = -1;
        }

        try
        {
            monthParsed = Integer.parseInt(month);
        }
        catch (NumberFormatException e)
        {
            monthParsed = -1;
        }

        try
        {
            dayParsed = Integer.parseInt(day);
        }
        catch (NumberFormatException e)
        {
            dayParsed = -1;
        }

        return new RequestDTOBorrowerCreate(firstname, lastname, dayParsed, monthParsed, yearParsed, street, no, place, zipParsed);
    }

    public static RequestDTOBorrowerUpdate genUpdateBorrowerDTO(BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        RequestDTOBorrowerCreate helpingDTO = genCreateBorrowerDTO(textfieldPanel);

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

        return new RequestDTOBorrowerUpdate(idParsed, helpingDTO.getFirstname(), helpingDTO.getLastname(), birthdayParsed, helpingDTO.getStreet(), helpingDTO.getNo(), helpingDTO.getPlace(), helpingDTO.getZip(), null);
    }
}
