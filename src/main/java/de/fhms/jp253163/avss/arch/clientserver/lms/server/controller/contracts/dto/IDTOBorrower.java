package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto;

public interface IDTOBorrower
{
    public static String
            EXAMPLE_FIRSTNAME = "William",
            EXAMPLE_LASTNAME = "James";

    public static int
            EXAMPLE_DAY_OF_BIRTH = 1,
            EXAMPLE_MONTH_OF_BIRTH = 2,
            EXAMPLE_YEAR_OF_BIRTH = 1997;

    public static String
            EXAMPLE_STREET = "Sesamstraße",
            EXAMPLE_NO = "123a",
            EXAMPLE_PLACE = "Neustadt";

    public static int EXAMPLE_ZIP = 98765;

    public String getFirstname();
    public String getLastname();
    public int getDayOfBirth();
    public int getMonthOfBirth();
    public int getYearOfBirth();
    public String getStreet();
    public String getNo();
    public String getPlace();
    public int getZip();
}
