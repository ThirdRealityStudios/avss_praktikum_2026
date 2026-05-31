package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

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

    public static RequestDTOBorrowerUpdate genUpdateBorrowerDTO(BorrowerTextfieldPanel textfieldPanel, ScrollableTablePanel tablePanel) throws NullPointerException
    {
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(tablePanel, "table panel is null");

        RequestDTOBorrowerCreate helpingDTO = genCreateBorrowerDTO(textfieldPanel);

        JTable table = tablePanel.getTable();

        int selectedRowsIDs[] = new int[0];

        if(table != null)
        {
            selectedRowsIDs = table.getSelectedRows(); // TODO createUpdatePanel.getView().get....getFocusedId();
        }

        int selectedRowID = -1;

        if(selectedRowsIDs.length > 0)
        {
            selectedRowID = selectedRowsIDs[0]; // even though multiple maybe selected, only take the first one chosen.
            System.out.println("THE FIRST ROW SELECTED IS: " + selectedRowID);
        }
        else
        {
            System.out.println("NO ROW SELECTED !!!!!!!!!!!!!!!!!!!!!!");
        }

        TableModel model = table != null ? table.getModel() : null;
        String borrowerID = "-1";

        if(model != null)
        {
            borrowerID = model.getValueAt(selectedRowID, 0).toString(); // select Borrower ID column.
            System.out.println("BORROWER ID FROM ROW IS: " + borrowerID);
        }
        else
            System.out.println("COULD NOT GET {BORROWER ID} FROM SELECTED ROW!!!!!!!!!!");


        int borrowerIDParsed = -1;
        try
        {
            borrowerIDParsed = Integer.parseInt(borrowerID);
            System.out.println("CONVERTED {BORROWER ID} FROM ROW IS: " + borrowerIDParsed);
        }
        catch (NumberFormatException e)
        {
            System.out.println("COULD NOT CONVERT THE {BORROWER ID} FROM THE ROW!!!!!!");
        }

        return new RequestDTOBorrowerUpdate(borrowerIDParsed, helpingDTO.getFirstname(), helpingDTO.getLastname(), helpingDTO.getDayOfBirth(), helpingDTO.getMonthOfBirth(), helpingDTO.getYearOfBirth(), helpingDTO.getStreet(), helpingDTO.getNo(), helpingDTO.getPlace(), helpingDTO.getZip(), null);
    }
}
