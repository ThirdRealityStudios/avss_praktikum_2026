package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class ResponseDTOLoanDelete implements IResponseDTOLoanDelete
{
    private int globalResponseCode;

    public ResponseDTOLoanDelete(GlobalResponseCode code)
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
