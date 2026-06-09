package de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data;

import java.time.LocalDate;

public interface IBorrower
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

    public int getId();
    public boolean setId(int id);

    public String getFirstname();
    public boolean setFirstname(String firstname);

    public String getLastname();
    public boolean setLastname(String lastname);

    public LocalDate getDateOfBirth();
    public boolean setDateOfBirth(LocalDate birthday);

    public IResidentialAddress getResidentialAddress();
    public boolean setResidentialAddress(IResidentialAddress residentialAddress);

    public double getSchufaScore();
    public boolean setSchufaScore(double schufaScore);

    public int[] getLoans();
    public boolean setLoans(int[] loans);
}
