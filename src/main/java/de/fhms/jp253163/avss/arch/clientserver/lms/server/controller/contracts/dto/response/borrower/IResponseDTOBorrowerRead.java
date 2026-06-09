package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

public interface IResponseDTOBorrowerRead
{
    /**
     * @return null (creation failed) or the read object(s).
     */
    public IBorrower[] getBorrowers();

    /**
     * @return Ordinal of the corresponding enum in GlobalResponseCode.
     */
    public int getGlobalResponseCode();
}
