package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto;

public interface IDTOLoan
{
    public double getAmount();
    public double getInterestRate();
    public int getTerm();
    public int getBorrower();
}
