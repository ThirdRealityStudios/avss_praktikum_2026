package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

public interface IResponseDTOBorrowerCreate
{
    /**
     * @return null (creation failed) or the created object.
     */
    public IBorrower getBorrower();

    /**
     * @return Ordinal of the corresponding enum in GlobalResponseCode.
     */
    public int getGlobalResponseCode();
}
