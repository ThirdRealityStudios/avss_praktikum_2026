package de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data;

public class ResidentialAddress implements IResidentialAddress
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

    @Override
    public boolean setStreet(String street)
    {
        if(street == null || street.isEmpty())
        {
            return false;
        }

        this.street = street;

        return true;
    }

    @Override
    public String getStreet()
    {
        return street;
    }

    @Override
    public boolean setNo(String no)
    {
        if(no == null || no.isEmpty())
        {
            return false;
        }

        this.no = no;

        return true;
    }

    @Override
    public String getNo()
    {
        return no;
    }

    @Override
    public boolean setZip(int zip)
    {
        if(zip < 0 || zip > 99999)
        {
            return false;
        }

        this.zip = zip;

        return true;
    }

    @Override
    public int getZip()
    {
        return zip;
    }

    @Override
    public boolean setPlace(String place)
    {
        if(place == null || place.isEmpty())
        {
            return false;
        }

        this.place = place;

        return true;
    }

    @Override
    public String getPlace()
    {
        return place;
    }

    @Override
    public String toString()
    {
        return "Street: " + getStreet() + "\nNo: " + getNo() + "\nZip: " + getZip() + "\nPlace: " + getPlace() + "\n";
    }
}
