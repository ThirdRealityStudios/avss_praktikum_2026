package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IResidentialAddress;

import java.util.Date;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, Date dateOfBirth, IResidentialAddress residentialAddress, double schufaScore);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);
}
