package de.fhms.jp253163.avss.arch.mvp.lms.model;

import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IResidentialAddress;

import java.time.LocalDate;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, LocalDate dateOfBirth, IResidentialAddress residentialAddress, double schufaScore);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);
}
