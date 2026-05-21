package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts;

public interface IBorrowerDTO
{
    public int getId();
    public String getFirstname();
    public String getLastname();
    public int getDayOfBirth();
    public int getMonthOfBirth();
    public int getYearOfBirth();
    public String[] getResidentialAddress();
    public double getSchufaScore();
    public int[] getLoanIDs();
}
