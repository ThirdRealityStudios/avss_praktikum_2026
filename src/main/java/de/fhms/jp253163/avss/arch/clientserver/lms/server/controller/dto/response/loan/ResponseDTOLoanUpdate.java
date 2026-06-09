package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;

import java.util.Objects;

public class ResponseDTOLoanUpdate implements IResponseDTOLoanUpdate
{
    private int globalResponseCode;

    public ResponseDTOLoanUpdate(GlobalResponseCode code)
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
