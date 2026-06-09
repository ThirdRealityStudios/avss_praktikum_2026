package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

import java.util.Objects;

public class ResponseDTOBorrowerDelete implements IResponseDTOBorrowerDelete
{
    private IBorrower borrower;
    private int globalResponseCode;

    public ResponseDTOBorrowerDelete(IBorrower borrower, GlobalResponseCode code)
    {
        Objects.requireNonNull(borrower);
        Objects.requireNonNull(code);

        this.borrower = borrower;
        this.globalResponseCode = code.ordinal();
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
