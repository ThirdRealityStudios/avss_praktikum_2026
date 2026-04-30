package de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung;

import de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data.IBorrower;
import de.fh_muenster.jp253163.avss.loanManagementSystem.Anwendung.data.IResidentialAddress;

public interface IBorrowerRepository
{
    public IBorrower createBorrower(String firstname, String lastname, IResidentialAddress residentialAddress);
    public IBorrower readBorrower(int id);
    public IBorrower[] readAllBorrowers();
    public boolean updateBorrower(IBorrower borrower);
    public boolean deleteBorrower(int id);
}
