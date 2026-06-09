package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;

import java.util.Objects;

public class ResponseDTOBorrowerUpdate implements IResponseDTOBorrowerUpdate
{
    private int globalResponseCode;

    public ResponseDTOBorrowerUpdate(GlobalResponseCode code)
    {
        Objects.requireNonNull(code);

        this.globalResponseCode = code.ordinal();
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
