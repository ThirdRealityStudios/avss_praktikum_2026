package de.fhms.jp253163.avss.arch.mvp.lms.model;

public class SchufaRepository implements ISchufaRepository
{
    @Override
    public double readSchufaScore(int borrower)
    {
        // TODO implemment query, e.g. via JSON file
        return 0.9;
    }
}
