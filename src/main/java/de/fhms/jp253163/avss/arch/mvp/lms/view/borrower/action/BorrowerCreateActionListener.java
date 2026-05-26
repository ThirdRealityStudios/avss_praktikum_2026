package de.fhms.jp253163.avss.arch.mvp.lms.view.borrower.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerCreate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class BorrowerCreateActionListener implements ActionListener
{
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private LabelledTextfield firstname, lastname, day, month, year, street, no, place, zip;
    private IView view;

    private boolean hasNullElements(LabelledTextfield[] arr)
    {
        for(LabelledTextfield field : arr)
            if(field == null)
                return true;

        return false;
    }

    public BorrowerCreateActionListener(ICreateBorrowerViewHandler createBorrowerViewHandler, LabelledTextfield[] labelledTextfields, IView view) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(labelledTextfields);
        Objects.requireNonNull(view);

        if(labelledTextfields.length != 9 || hasNullElements(labelledTextfields))
        {
            throw new NullPointerException();
        }

        this.createBorrowerViewHandler = createBorrowerViewHandler;

        firstname = labelledTextfields[0];
        lastname = labelledTextfields[1];
        day = labelledTextfields[2];
        month = labelledTextfields[3];
        year = labelledTextfields[4];
        street = labelledTextfields[5];
        no = labelledTextfields[6];
        place = labelledTextfields[7];
        zip = labelledTextfields[8];

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String firstname = this.firstname.getJTextField().getText(),
            lastname = this.lastname.getJTextField().getText(),
            day = this.day.getJTextField().getText(),
            month = this.month.getJTextField().getText(),
            year = this.year.getJTextField().getText(),
            street = this.year.getJTextField().getText(),
            no = this.no.getJTextField().getText(),
            place = this.place.getJTextField().getText(),
            zip = this.zip.getJTextField().getText();

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
            view.showMessage("Incorrect birthday! (not everywhere numbers)");

            return;
        }

        LocalDate birthdayParsed = null;

        try
        {
            birthdayParsed = LocalDate.of(yearParsed, monthParsed, dayParsed);
        }
        catch (DateTimeException dte)
        {
            view.showMessage("Incorrect birthday! (not a valid date)");

            return;
        }

        IRequestDTOBorrowerCreate requestDTOBorrowerCreate = new RequestDTOBorrowerCreate(firstname, lastname, street, no, place, zipParsed, birthdayParsed);

        createBorrowerViewHandler.invoke(requestDTOBorrowerCreate);
    }
}
