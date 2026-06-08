package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer.data;

public interface IResidentialAddress
{
    public boolean setStreet(String street);
    public String getStreet();

    public boolean setNo(String no);
    public String getNo();

    public boolean setZip(int zip);
    public int getZip();

    public boolean setPlace(String place);
    public String getPlace();
}
