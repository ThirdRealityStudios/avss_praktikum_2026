package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ISchufaRepository;

public class SchufaConsumer implements ISchufaRepository
{
    @Override
    public double readSchufaScore(int borrower)
    {
        // TODO implemment query, e.g. via JSON file
        return 0.9;
    }
}
