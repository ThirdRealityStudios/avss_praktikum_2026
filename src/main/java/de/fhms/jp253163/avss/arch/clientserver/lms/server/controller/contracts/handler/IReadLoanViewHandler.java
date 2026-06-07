package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanRead;

import java.util.ArrayList;

public interface IReadLoanViewHandler
{
    /**
     * Determines an array like IDTOLoanRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * Response codes explained:
     *
     * 0:
     * 1:
     * 2:
     * 3:
     * 4:
     * 5:
     * 6:
     * 7:
     *
     * @param readLoanDTO
     */
    public int invoke(IRequestDTOLoanRead readLoanDTO);

    /**
     * Delivers you the buffered response data from read loans event.
     * You might want to check the response code you got after invocation
     * to know when to call this function.
     * This function will clear the buffer after you got the data.
     *
     * Make sure you don't wait too long because if read event is invoked many times,
     * the buffer will pile up with response data.
     *
     * @return Response data.
     */
    public ArrayList<IDTOLoanSet> getResponseData();
}
