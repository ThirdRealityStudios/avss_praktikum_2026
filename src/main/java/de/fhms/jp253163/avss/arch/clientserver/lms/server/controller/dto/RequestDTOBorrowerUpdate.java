package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerUpdate;

public class RequestDTOBorrowerUpdate implements IRequestDTOBorrowerUpdate
{
    private int day, month, year;
    private int borrowerID, zip;
    private String firstname, lastname, street, no, place;
    private int[] loans;

    /**
     * Specify what values are used to update the current value.
     * Passing null (or -1 for primitive data types) for one single argument means that it simply does not get updated (ignored).
     * So passing a value will update it.
     * Passing null is also valid for arguments (= don't update me).
     *
     * @param borrowerID
     * @param firstname
     * @param lastname
     * @param day
     * @param month
     * @param year
     * @param street
     * @param no
     * @param place
     * @param zip
     * @param loans
     */
    public RequestDTOBorrowerUpdate(int borrowerID, String firstname, String lastname, int day, int month, int year, String street, String no, String place, int zip, int[] loans)
    {
        this.borrowerID = borrowerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.day = day;
        this.month = month;
        this.year = year;
        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
        this.loans = loans;
    }

    @Override
    public int getBorrowerID() {
        return borrowerID;
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
        return day;
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

    @Override
    public int[] getLoans() {
        return loans;
    }
}
