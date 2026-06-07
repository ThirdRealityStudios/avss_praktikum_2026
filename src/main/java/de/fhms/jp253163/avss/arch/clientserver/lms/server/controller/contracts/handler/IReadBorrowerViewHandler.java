package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerRead;

import java.util.ArrayList;

public interface IReadBorrowerViewHandler
{
    /**
     * Determines internally an array like IDTOBorrowerRead[] when invoked.
     * Then it outputs it in the desired way (depending on whether it is a GUI or console e.g.).
     *
     * @param readBorrowerDTO
     */
    public int invoke(IRequestDTOBorrowerRead readBorrowerDTO);

    /**
     * Delivers you the buffered response data from read borrowers event.
     * You might want to check the response code you got after invocation
     * to know when to call this function.
     * This function will clear the buffer after you got the data.
     *
     * Make sure you don't wait too long because if read event is invoked many times,
     * the buffer will pile up with response data.
     *
     * @return Response data.
     */
    public ArrayList<IDTOBorrowerSet> getResponseData();
}
