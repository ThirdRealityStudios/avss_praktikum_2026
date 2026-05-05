package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;

public interface iSchufaRepository
{
    public double readSchufaScore(IBorrower borrower);
}
