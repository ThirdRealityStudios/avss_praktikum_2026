package de.fhms.jp253163.avss.lms.application.data;

public class ResidentialAddress
{
    private String street, no;
    private int zip;
    private String place;

    public ResidentialAddress(String street, String no, int zip, String place) throws IllegalArgumentException
    {
        if(!setStreet(street) ||
           !setNo(no) ||
           !setZip(zip) ||
           !setPlace(place))
        {
            throw new IllegalArgumentException();
        }
    }

    public boolean setStreet(String street)
    {
        if(street == null || street.isEmpty())
        {
            return false;
        }

        this.street = street;

        return true;
    }

    public String getStreet()
    {
        return street;
    }

    public boolean setNo(String no)
    {
        if(no == null || no.isEmpty())
        {
            return false;
        }

        this.no = no;

        return true;
    }

    public String getNo()
    {
        return no;
    }

    public boolean setZip(int zip)
    {
        if(zip < 0 || zip > 99999)
        {
            return false;
        }

        this.zip = zip;

        return true;
    }

    public int getZip()
    {
        return zip;
    }

    public boolean setPlace(String place)
    {
        if(place == null || place.isEmpty())
        {
            return false;
        }

        this.place = place;

        return true;
    }
}
