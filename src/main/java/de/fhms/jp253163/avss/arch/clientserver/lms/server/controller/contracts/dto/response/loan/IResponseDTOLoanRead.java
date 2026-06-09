package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

public interface IResponseDTOLoanRead
{
    /**
     * @return null (creation failed) or the created object.
     */
    public ILoan getLoan();

    /**
     * @return Ordinal of the corresponding enum in GlobalResponseCode.
     */
    public int getGlobalResponseCode();
}
