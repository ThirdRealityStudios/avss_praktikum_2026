package de.fhms.jp253163.avss.arch.mvp.lms.model;

import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;

import java.time.LocalDate;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, LocalDate dateOfBirth, IResidentialAddress residentialAddress, double schufaScore);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);

    /**
     * @param firstname
     * @return true if firstname is 'null' (= do not update firstname) or if firstname is not empty. Otherwise, false.
     */
    public boolean isValidFirstname(String firstname);

    /**
     * @param lastname
     * @return true if lastname is 'null' (= do not update lastname) or if lastname is not empty. Otherwise, false.
     */
    public boolean isValidLastname(String lastname);

    /**
     * @param day
     * @return true if day is -1 (= do not update day of birth) or if day ranges between 1 and 31. Otherwise, false.
     */
    public boolean isValidDayOfBirth(int day);

    /**
     * @param month
     * @return true if month is -1 (= do not update month of birth) or if month ranges between 1 and 12. Otherwise, false.
     */
    public boolean isValidMonthOfBirth(int month);

    /**
     * @param year
     * @return true if year is -1 (= do not update year of birth) or if year ranges between 1910 and highest technically possible integer value. Otherwise, false.
     */
    public boolean isValidYearOfBirth(int year);

    /**
     * @param street
     * @return true if street is 'null' (= do not update street) or if street is not empty. Otherwise, false.
     */
    public boolean isValidStreet(String street);

    /**
     * @param no
     * @return true if no is 'null' (= do not update no) or if no is not empty. Otherwise, false.
     */
    public boolean isValidNo(String no);

    /**
     * @param place
     * @return true if place is 'null' (= do not update place) or if place is not empty. Otherwise, false.
     */
    public boolean isValidPlace(String place);

    /**
     * @param zip
     * @return true if zip is -1 (= do not update zip) or if zip ranges between 00000 and 99999. Otherwise, false.
     */
    public boolean isValidZip(int zip);

    /**
     * @param schufaScore
     * @return true if schufaScore is -1 (= do not update schufaScore) or if schufaScore ranges between 0 and 1 (100 %). Otherwise, false.
     */
    public boolean isValidSchufaScore(double schufaScore);
}
