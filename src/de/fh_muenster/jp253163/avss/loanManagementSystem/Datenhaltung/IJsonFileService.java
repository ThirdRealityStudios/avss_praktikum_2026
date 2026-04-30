package de.fh_muenster.jp253163.avss.loanManagementSystem.Datenhaltung;

public interface IJsonFileService
{
    public boolean save(String jsonFilepath, Object obj);
    public Object read(String jsonFilepath, int id);
    public boolean delete(String jsonFilepath, int id);
}
