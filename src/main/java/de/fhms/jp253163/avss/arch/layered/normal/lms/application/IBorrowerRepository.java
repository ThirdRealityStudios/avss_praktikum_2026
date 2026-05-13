package de.fhms.jp253163.avss.arch.layered.normal.lms.application;

import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.IResidentialAddress;

import java.util.Date;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, Date dateOfBirth, IResidentialAddress residentialAddress, double schufaScore);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);
}
