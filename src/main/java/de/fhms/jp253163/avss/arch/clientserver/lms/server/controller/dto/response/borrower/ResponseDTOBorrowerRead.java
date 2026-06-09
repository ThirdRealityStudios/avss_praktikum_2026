package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.response.borrower;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.response.borrower.IResponseDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.Borrower;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

import java.util.Objects;

public class ResponseDTOBorrowerRead implements IResponseDTOBorrowerRead
{
    private Borrower[] borrowers;
    private int globalResponseCode;

    /**
     * @param borrowers MUST be NOT null! At least an empty array is expected.
     * @param code
     */
    public ResponseDTOBorrowerRead(IBorrower[] borrowers, GlobalResponseCode code)
    {
        Objects.requireNonNull(borrowers);
        Objects.requireNonNull(code);

        if(!(borrowers instanceof Borrower[])) throw new IllegalArgumentException("borrowers not a supported IBorrower[] instance");

        this.borrowers = (Borrower[]) borrowers;
        this.globalResponseCode = code.ordinal();
    }

    @Override
    public IBorrower[] getBorrowers()
    {
        return borrowers;
    }

    @Override
    public int getGlobalResponseCode()
    {
        return globalResponseCode;
    }
}
