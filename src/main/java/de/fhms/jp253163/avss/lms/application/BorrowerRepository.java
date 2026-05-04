package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;

public class BorrowerRepository implements IBorrowerRepository
{
    @Override
    public IBorrower createBorrower(String firstname, String lastname, IResidentialAddress residentialAddress)
    {
        return null;
    }

    @Override
    public IBorrower readBorrower(int id)
    {
        return null;
    }

    @Override
    public IBorrower[] readAllBorrowers()
    {
        return new IBorrower[0];
    }

    @Override
    public boolean updateBorrower(IBorrower borrower)
    {
        return false;
    }

    @Override
    public boolean deleteBorrower(int id)
    {
        return false;
    }
}
