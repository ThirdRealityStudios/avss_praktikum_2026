package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.dto;

public interface IDTOLoanCreate
{
    public double getAmount();
    public double getInterestRate();
    public int getTerm();
    public int getBorrower();
}
