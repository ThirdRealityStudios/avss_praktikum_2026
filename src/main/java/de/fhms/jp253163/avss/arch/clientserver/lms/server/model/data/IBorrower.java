package de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data;

import java.time.LocalDate;

public interface IBorrower
{
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
