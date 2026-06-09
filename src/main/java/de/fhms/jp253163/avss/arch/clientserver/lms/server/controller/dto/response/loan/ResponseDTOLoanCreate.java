package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class ResponseDTOLoanCreate implements IResponseDTOLoanCreate
{
    private ILoan loan;
    private int globalResponseCode;

    /**
     * @param loan Can be null if loan creation failed.
     * @param code
     */
    public ResponseDTOLoanCreate(ILoan loan, GlobalResponseCode code)
    {
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
