package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.action;

import java.time.LocalDate;
import java.util.Objects;

public class LoanSharedLogicDTO
{
    private int id = -1;
    private String firstname, lastname;
    private LocalDate birthday;
    private String street, no, place;
    private int zip;

    public LoanSharedLogicDTO(int id, String firstname, String lastname, LocalDate birthday, String street, String no, String place, int zip) throws NullPointerException
    {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastname);
        Objects.requireNonNull(birthday);
        Objects.requireNonNull(street);
        Objects.requireNonNull(no);
        Objects.requireNonNull(place);

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
    }

    public int getBorrowerId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getStreet() {
        return street;
    }

    public String getNo() {
        return no;
    }

    public String getPlace() {
        return place;
    }

    public int getZip() {
        return zip;
    }
}
