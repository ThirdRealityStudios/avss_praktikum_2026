package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class ResponseDTOLoanRead implements IResponseDTOLoanRead
{
    private ILoan loan;
    private int globalResponseCode;

    public ResponseDTOLoanRead(ILoan loan, GlobalResponseCode code)
    {
        Objects.requireNonNull(loan);
        Objects.requireNonNull(code);

        this.loan = loan;
        this.globalResponseCode = code.ordinal();
    }

    @Override
    public ILoan getLoan()
    {
        return loan;
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
