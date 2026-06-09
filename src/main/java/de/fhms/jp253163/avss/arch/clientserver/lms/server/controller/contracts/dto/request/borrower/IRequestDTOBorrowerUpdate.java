package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.request.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOBorrower;

public interface IRequestDTOBorrowerUpdate extends IDTOBorrower
{
    public int getBorrowerID();
    public int[] getLoans();
}