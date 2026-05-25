package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto;

public interface IDTOLoanSet extends IDTOLoan
{
    public int getID();

    public int getRemainingTerm();

    public double getRemainingBalance();
}
