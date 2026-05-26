package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c.BorrowerCreatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class BorrowerCreateActionListener implements ActionListener
{
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private BorrowerCreatePanel createPanel;

    public BorrowerCreateActionListener(ICreateBorrowerViewHandler createBorrowerViewHandler, BorrowerCreatePanel createPanel) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(createPanel);

        this.createBorrowerViewHandler = createBorrowerViewHandler;
        this.createPanel = createPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String firstname = createPanel.getCreateNamePanel().getFirstname().getJTextField().getText(),
            lastname = createPanel.getCreateNamePanel().getLastname().getJTextField().getText(),
            day = createPanel.getCreateBirthdayPanel().getDay().getJTextField().getText(),
            month = createPanel.getCreateBirthdayPanel().getMonth().getJTextField().getText(),
            year = createPanel.getCreateBirthdayPanel().getYear().getJTextField().getText(),
            street = createPanel.getCreateAddressPanel().getStreet().getJTextField().getText(),
            no = createPanel.getCreateAddressPanel().getNo().getJTextField().getText(),
            place = createPanel.getCreateAddressPanel().getPlace().getJTextField().getText(),
            zip = createPanel.getCreateAddressPanel().getZip().getJTextField().getText();

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
            createPanel.getView().showMessage("Incorrect zip code! (not a number)");

            return;
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
            createPanel.getView().showMessage("Incorrect birthday! (not everywhere numbers)");

            return;
        }

        LocalDate birthdayParsed = null;

        try
        {
            birthdayParsed = LocalDate.of(yearParsed, monthParsed, dayParsed);
        }
        catch (DateTimeException dte)
        {
            createPanel.getView().showMessage("Incorrect birthday! (not a valid date)");

            return;
        }

        IRequestDTOBorrowerCreate requestDTOBorrowerCreate = null;

        try
        {
            requestDTOBorrowerCreate = new RequestDTOBorrowerCreate(firstname, lastname, street, no, place, zipParsed, birthdayParsed);
        }
        catch(NullPointerException npe)
        {
            createPanel.getView().showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        createBorrowerViewHandler.invoke(requestDTOBorrowerCreate);
    }
}