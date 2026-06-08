package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto;

public interface IDTOLoan
{
    public double getAmount();
    public double getInterestRate();
    public int getTerm();
    public int getBorrower();
}
