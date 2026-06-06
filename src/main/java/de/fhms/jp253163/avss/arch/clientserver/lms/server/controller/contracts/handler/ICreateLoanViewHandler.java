package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanCreate;

public interface ICreateLoanViewHandler
{
    /**
     * Response codes explained:
     *
     * 0: "Invalid creation request!"
     * 1: LoanFieldErrorMessage.INVALID_BORROWER_ID
     * 2: LoanFieldErrorMessage.INVALID_INTEREST_RATE
     * 3: LoanFieldErrorMessage.INVALID_AMOUNT
     * 4: LoanFieldErrorMessage.INVALID_TERM
     * 5: "Creating loan failed"
     * 6: "Created loan successfully"
     *
     * @param createLoanDTO
     * @return Code for HTTP response
     */
    public int invoke(IRequestDTOLoanCreate createLoanDTO);
}
