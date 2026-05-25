package de.fhms.jp253163.avss.arch.mvp.lms.presenter.contracts.dto;

public interface IRequestDTOBorrowerUpdate extends IDTOBorrower
{
    public int getBorrowerID();
    public int[] getLoans();
}