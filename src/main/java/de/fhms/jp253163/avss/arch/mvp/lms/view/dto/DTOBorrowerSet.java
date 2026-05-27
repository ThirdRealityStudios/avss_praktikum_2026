package de.fhms.jp253163.avss.arch.mvp.lms.view.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;

import java.time.LocalDate;

public class DTOBorrowerSet implements IDTOBorrowerSet
{
    private int borrowerID, zip;
    private String firstname, lastname, street, no, place;
    private LocalDate birthday;

    public DTOBorrowerSet(int borrowerID, String firstname, String lastname, LocalDate birthday, String street, String no, String place, int zip) throws NullPointerException
    {
        if(firstname == null || lastname == null || birthday == null || street == null || no == null || place == null)
            throw new NullPointerException();

        this.borrowerID = borrowerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
    }

    @Override
    public int getID() {
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

    @Override
    public String toString(){
        return "ID: " + getID() + "\n"+
                "Firstname: " + getFirstname() + "\n" +
                "Lastname: " + getLastname() + "\n" +
                "Born: " + getDayOfBirth() + "." + getMonthOfBirth() + "." + getYearOfBirth() + "\n" +
                "Address: " + getStreet() + " " + getNo() + " in " + getPlace() + " (" + getZip() + ")";
    }
}
