package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class ResponseDTOLoanRead implements IResponseDTOLoanRead
{
    private ILoan[] loans;
    private int globalResponseCode;

    /**
     * @param loans MUST be NOT null! At least an empty array is expected.
     * @param code
     */
    public ResponseDTOLoanRead(ILoan[] loans, GlobalResponseCode code)
    {
        Objects.requireNonNull(loans);
        Objects.requireNonNull(code);

        this.loans = loans;
        this.globalResponseCode = code.ordinal();
    }

    @Override
    public ILoan[] getLoans()
    {
        return loans;
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
