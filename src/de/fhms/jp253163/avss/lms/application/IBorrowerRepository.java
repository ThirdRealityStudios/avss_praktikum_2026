package de.fhms.jp253163.avss.lms.application;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.IResidentialAddress;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, IResidentialAddress residentialAddress);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);
}
