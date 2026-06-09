package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.loan;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.loan.IResponseDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Loan;

import java.util.Objects;

public class ResponseDTOLoanCreate implements IResponseDTOLoanCreate
{
    private Loan loan;
    private int globalResponseCode;

    /**
     * @param loan Can be null if loan creation failed.
     * @param code
     */
    public ResponseDTOLoanCreate(ILoan loan, GlobalResponseCode code)
    {
        Objects.requireNonNull(code);

        if(loan != null && !(loan instanceof Loan)) throw new IllegalArgumentException("loan not a supported ILoan instance");

        this.loan = (Loan) loan;
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
