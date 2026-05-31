package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

public interface BorrowerFieldErrorMessage
{
    public static final String
            INVALID_FIRSTNAME = "Please provide a (valid) firstname!",
            INVALID_LASTNAME = "Please provide a (valid) lastname!",
            INVALID_DAY_OF_BIRTH = "Please provide a (valid) day!",
            INVALID_MONTH_OF_BIRTH = "Please provide a (valid) month!",
            INVALID_YEAR_OF_BIRTH = "Please provide a (valid) year! (at least 1910)",
            INVALID_STREET = "Please provide a (valid) street!",
            INVALID_NO = "Please provide a (valid) no!",
            INVALID_PLACE = "Please provide a (valid) place!",
            INVALID_ZIP = "Please provide a (valid 5 digits) zip!";
}
