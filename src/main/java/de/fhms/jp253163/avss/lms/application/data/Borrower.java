package de.fhms.jp253163.avss.lms.application.data;

import org.apache.commons.lang3.time.DateUtils;

import javax.swing.text.DateFormatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

public class Borrower implements IBorrower
{
    private int id;
    private String firstname, lastname;
    private Date birthday;
    private IResidentialAddress residentialAddress;
    private double schufaScore;
    private ILoan[] loans;

    public Borrower(int id, String firstname, String lastname, Date birthday, IResidentialAddress residentialAddress, double schufaScore, ILoan[] loans) throws IllegalArgumentException
    {
        IllegalArgumentException defaultException = new IllegalArgumentException("Given argument is invalid, null or has invalid format.");

        if(!setId(id) ||
           !setFirstname(firstname) ||
           !setLastname(lastname) ||
           !setDateOfBirth(birthday) ||
           !setResidentialAddress(residentialAddress) ||
           !setSchufaScore(schufaScore) ||
           !setLoans(loans))
        {
            throw defaultException;
        }
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public boolean setId(int id)
    {
        if(id < 0)
            return false;

        this.id = id;

        return true;
    }

    private boolean isName(String name)
    {
        return name != null && name.matches("^[\\p{L} .'-]+$");
    }

    @Override
    public String getFirstname()
    {
        return firstname;
    }

    @Override
    public boolean setFirstname(String firstname)
    {
        if(isName(firstname))
        {
            this.firstname = firstname;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getLastname()
    {
        return lastname;
    }

    @Override
    public boolean setLastname(String lastname)
    {
        if(isName(lastname))
        {
            this.lastname = lastname;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Date getDateOfBirth()
    {
        return new Date(birthday.getTime());
    }

    @Override
    public boolean setDateOfBirth(Date birthday)
    {
        Date tooOldToBeTrue = null;

        try
        {
            tooOldToBeTrue = DateUtils.parseDate("1910-01-01", new String[] { "yyyy-MM-dd" });

            if(tooOldToBeTrue == null)
                return false;
        }
        catch(ParseException e)
        {
            e.printStackTrace();

            return false;
        }

        if(birthday == null || birthday.before(tooOldToBeTrue))
        {
            return false;
        }
        else
        {
            this.birthday = birthday;

            return true;
        }
    }

    @Override
    public IResidentialAddress getResidentialAddress()
    {
        return residentialAddress;
    }

    @Override
    public boolean setResidentialAddress(IResidentialAddress residentialAddress)
    {
        if(residentialAddress == null)
        {
            return false;
        }
        else
        {
            this.residentialAddress = residentialAddress;

            return true;
        }
    }

    @Override
    public double getSchufaScore()
    {
        return schufaScore;
    }

    @Override
    public boolean setSchufaScore(double schufaScore)
    {
        if(schufaScore < 0 || schufaScore > 1)
        {
            return false;
        }
        else
        {
            this.schufaScore = schufaScore;

            return true;
        }
    }

    @Override
    public ILoan[] getLoans()
    {
        return Arrays.copyOf(loans, loans.length);
    }

    @Override
    public boolean setLoans(ILoan[] loans)
    {
        for(ILoan loan : loans)
        {
            if(loan == null)
                return false;
        }

        this.loans = Arrays.copyOf(loans, loans.length);

        return true;
    }

    @Override
    public String toString()
    {
        String loansActive = "";

        for(ILoan loan : getLoans())
        {
            loansActive += "\n" + loan;
        }

        loansActive += "\n";

        return "ID: " + getId() + "\nFirstname: " + getFirstname() + "\nLastname: " + getLastname() + "\nBirthday: " + getDateOfBirth() + "\bSchufaScore: " + String.format("%2.2f", getSchufaScore()) + "\nResidential Address:\n\n" + getResidentialAddress() + "\nLoans active:\n" + loansActive;
    }
}
