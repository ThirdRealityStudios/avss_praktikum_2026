package de.fhms.jp253163.avss.arch.mvp.lms.view.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;

import java.time.LocalDate;

public class RequestDTOBorrowerUpdate implements IRequestDTOBorrowerUpdate
{
    private int borrowerID, zip;
    private String firstname, lastname, street, no, place;
    private int[] loans;
    private LocalDate birthday;

    /**
     * Specify what values used to update the current value.
     * Passing null for one single argument means that it simply does not get updated (ignored).
     * So passing a value will update it.
     * Passing null is also valid for arguments (= don't update me).
     *
     * @param borrowerID
     * @param firstname
     * @param lastname
     * @param birthday
     * @param street
     * @param no
     * @param place
     * @param zip
     * @param loans
     */
    public RequestDTOBorrowerUpdate(int borrowerID, String firstname, String lastname, LocalDate birthday, String street, String no, String place, int zip, int[] loans) throws NullPointerException
    {
        this.borrowerID = borrowerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
        this.loans = loans;
    }

    @Override
    public int getBorrowerID() {
        return 0;
    }

    @Override
    public String getFirstname() {
        return "";
    }

    @Override
    public String getLastname() {
        return "";
    }

    @Override
    public int getDayOfBirth() {
        return 0;
    }

    @Override
    public int getMonthOfBirth() {
        return 0;
    }

    @Override
    public int getYearOfBirth() {
        return 0;
    }

    @Override
    public String getStreet() {
        return "";
    }

    @Override
    public String getNo() {
        return "";
    }

    @Override
    public String getPlace() {
        return "";
    }

    @Override
    public int getZip() {
        return 0;
    }

    @Override
    public int[] getLoans() {
        return new int[0];
    }
}
