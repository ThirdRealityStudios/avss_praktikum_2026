package de.fhms.jp253163.avss.arch.mvp.lms.view.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;

import java.time.LocalDate;

public class RequestDTOBorrowerCreate implements IRequestDTOBorrowerCreate
{
    private String firstname, lastname;
    private String street, no, place;
    private int zip;

    private LocalDate birthday;

    public RequestDTOBorrowerCreate(String firstname, String lastname, String street, String no, String place, int zip, LocalDate birthday)
    {
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
        return birthday == null ? -1 : birthday.getDayOfMonth(); // if null = don't update (-1)
    }

    @Override
    public int getMonthOfBirth() {
        return birthday == null ? -1 : birthday.getMonthValue();
    }

    @Override
    public int getYearOfBirth() {
        return birthday == null ? -1 : birthday.getYear();
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
