package de.fh_muenster.jp253163.avss.loanManagementSystem.Datenhaltung;

public class JsonFileService implements IJsonFileService
{
    @Override
    public boolean save(String jsonFilepath, Object obj)
    {
        return false;
    }

    @Override
    public Object read(String jsonFilepath, int id)
    {
        return null;
    }

    @Override
    public boolean delete(String jsonFilepath, int id)
    {
        return false;
    }
}
