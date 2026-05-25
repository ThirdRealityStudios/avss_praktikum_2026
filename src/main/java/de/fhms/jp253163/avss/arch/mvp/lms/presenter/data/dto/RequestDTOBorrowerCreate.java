package de.fhms.jp253163.avss.arch.mvp.lms.presenter.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto.IRequestDTOBorrowerCreate;

import java.time.LocalDate;

public class RequestDTOBorrowerCreate implements IRequestDTOBorrowerCreate
{
    private String firstname, lastname;
    private String street, no, place;
    private int zip;

    private LocalDate birthday;

    public RequestDTOBorrowerCreate(String firstname, String lastname, String street, String no, String place, int zip, LocalDate birthday) throws NullPointerException
    {
        if(firstname == null || lastname == null || street == null || no == null || place == null || birthday == null)
            throw new NullPointerException();

        this.firstname = firstname;
        this.lastname = lastname;

        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;

        this.birthday = birthday;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public int getDayOfBirth() {
        return birthday.getDayOfMonth();
    }

    @Override
    public int getMonthOfBirth() {
        return birthday.getMonthValue();
    }

    @Override
    public int getYearOfBirth() {
        return birthday.getYear();
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getNo() {
        return no;
    }

    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public int getZip() {
        return zip;
    }
}
