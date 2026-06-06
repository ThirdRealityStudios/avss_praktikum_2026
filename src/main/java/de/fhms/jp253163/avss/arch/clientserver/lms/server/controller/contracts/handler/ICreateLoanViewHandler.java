package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    /**
     * Returns the following response codes
     * (from enum GlobalResponseCode):
     *
     * CREATE_LOAN_INVALID_CREATION_REQUEST,
     * CREATE_LOAN_INVALID_BORROWER_ID,
     * CREATE_LOAN_INVALID_INTEREST_RATE,
     * CREATE_LOAN_INVALID_AMOUNT,
     * CREATE_LOAN_INVALID_TERM,
     * CREATE_LOAN_CREATION_ERROR,
     * CREATE_LOAN_CREATION_SUCCESS
     *
     * @param createLoanDTO
     * @return Code for HTTP response
     */
    public int invoke(IRequestDTOLoanCreate createLoanDTO);
}
