package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;

public class RequestDTOBorrowerCreate implements IRequestDTOBorrowerCreate
{
    private String firstname, lastname;
    private String street, no, place;
    private int zip;

    private int day, month, year;

    public RequestDTOBorrowerCreate(String firstname, String lastname, int day, int month, int year, String street, String no, String place, int zip)
    {
        this.firstname = firstname;
        this.lastname = lastname;

        this.day = day;
        this.month = month;
        this.year = year;

        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
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
        return day; // if null = don't update (-1)
    }

    @Override
    public int getMonthOfBirth() {
        return month;
    }

    @Override
    public int getYearOfBirth() {
        return year;
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
