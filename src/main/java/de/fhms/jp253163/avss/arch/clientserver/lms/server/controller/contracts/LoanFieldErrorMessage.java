package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts;

public interface LoanFieldErrorMessage
{
    public static final String
            INVALID_BORROWER_ID = "Please provide a (valid) borrower ID! (at least zero)",
            INVALID_INTEREST_RATE = "Please provide a (valid) interest rate! (at least 0.0001 = 0.01 percent)",
            INVALID_AMOUNT = "Please provide a (valid) amount! (at least 0.01)",
            INVALID_TERM = "Please provide a (valid) term! (at least 1 month)";
}
