package de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data;

import java.util.Date;

public interface IBorrower
{
    public int getId();
    public boolean setId(int id);

    public String getFirstname();
    public boolean setFirstname(String firstname);

    public String getLastname();
    public boolean setLastname(String lastname);

    public Date getDateOfBirth();
    public boolean setDateOfBirth(Date birthday);

    public IResidentialAddress getResidentialAddress();
    public boolean setResidentialAddress(IResidentialAddress residentialAddress);

    public double getSchufaScore();
    public boolean setSchufaScore(double schufaScore);

    public ILoan[] getLoans();
    public boolean setLoans(ILoan[] loans);
}
