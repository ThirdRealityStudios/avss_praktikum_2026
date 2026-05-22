package de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto;

public interface IDTOBorrower
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
