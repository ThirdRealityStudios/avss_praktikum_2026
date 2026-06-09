package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

import java.util.Objects;

public class ResponseDTOBorrowerCreate implements IResponseDTOBorrowerCreate
{
    private Borrower borrower;
    private int globalResponseCode;

    /**
     * @param borrower null allowed if creation failed.
     * @param code
     */
    public ResponseDTOBorrowerCreate(IBorrower borrower, GlobalResponseCode code)
    {
        Objects.requireNonNull(code);

        if(borrower != null && !(borrower instanceof Borrower)) throw new IllegalArgumentException("borrower not a supported IBorrower instance");

        this.borrower = (Borrower) borrower;
        this.globalResponseCode = code.ordinal();
    }

    @Override
    public IBorrower getBorrower()
    {
        return borrower;
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
