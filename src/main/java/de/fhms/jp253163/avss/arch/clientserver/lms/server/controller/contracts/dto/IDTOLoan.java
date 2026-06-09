package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto;

public interface IDTOLoan
{
    public static final double
            EXAMPLE_AMOUNT = 110,
            EXAMPLE_INTEREST_RATE = 0.01;

    public static final int
            EXAMPLE_TERM = 12,
            EXAMPLE_BORROWER = 0;

    public double getAmount();
    public double getInterestRate();
    public int getTerm();
    public int getBorrower();
}
