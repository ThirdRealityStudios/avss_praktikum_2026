package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

public interface IResponseDTOLoanRead
{
    /**
     * @return null (creation failed) or the read object(s).
     */
    public ILoan[] getLoans();

    /**
     * @return Ordinal of the corresponding enum in GlobalResponseCode.
     */
    public int getGlobalResponseCode();
}
