package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;

import java.util.Date;

public interface IBorrowerRepository
{
    public ILogicRequestDTO createBorrower(String firstname, String lastname, Date dateOfBirth, IResidentialAddress residentialAddress, double schufaScore);
    public ILogicRequestDTO readBorrower(int id);
    public ILogicRequestDTO[] readAllBorrowers();
    public ILogicRequestDTO updateBorrower(IBorrower borrower);
    public ILogicRequestDTO deleteBorrower(int id);
}
