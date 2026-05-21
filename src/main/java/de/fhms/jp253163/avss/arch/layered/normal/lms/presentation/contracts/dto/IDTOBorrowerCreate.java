package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto;

public interface IDTOBorrowerCreate
{
    public String getFirstname();
    public String getLastname();
    public int getDayOfBirth();
    public int getMonthOfBirth();
    public int getYearOfBirth();
    public String[] getResidentialAddress();
    public double getSchufaScore();
    public int[] getLoanIDs();
}
