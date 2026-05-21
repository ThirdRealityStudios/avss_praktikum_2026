package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto;

/**
 * Returned by view to the presenter for requesting borrower creation with the given data.
 */
public interface IDTOBorrowerCreate
{
    public String getFirstname();
    public String getLastname();
    public int getDayOfBirth();
    public int getMonthOfBirth();
    public int getYearOfBirth();
    public String getStreet();
    public String getNo();
    public String getPlace();
    public int getZip();
    public double getSchufaScore();
    public int[] getLoanIDs();
}