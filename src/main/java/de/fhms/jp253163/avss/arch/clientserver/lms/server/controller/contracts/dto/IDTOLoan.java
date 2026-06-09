package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto;

public interface IDTOLoan
{
    public double getAmount();
    public double getInterestRate();
    public int getTerm();
    public int getBorrower();
}
