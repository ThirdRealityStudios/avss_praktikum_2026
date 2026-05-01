package de.fhms.jp253163.avss.lms.data;

public interface IJsonFileService
{
    public boolean save(String jsonFilepath, Object obj);
    public Object read(String jsonFilepath, int id);
    public boolean delete(String jsonFilepath, int id);
}
