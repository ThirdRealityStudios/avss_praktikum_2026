package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;

public interface ISchufaRepository
{
    /**
     * Reads the schufa score of the given borrower,
     * using the corresponding ID.
     *
     * @param borrower ID of the corresponding borrower in order to ask for the Schufa score.
     * @return Schufa score in percentage, between 0 and 1.
     */
    public double readSchufaScore(int borrower);
}
