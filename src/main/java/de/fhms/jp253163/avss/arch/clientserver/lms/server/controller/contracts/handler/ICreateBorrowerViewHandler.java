package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    /**
     * Return codes explained:
     *
     * 0: Invalid creation request!
     * 1: BorrowerFieldErrorMessage.INVALID_FIRSTNAME
     * 2: BorrowerFieldErrorMessage.INVALID_LASTNAME
     * 3: BorrowerFieldErrorMessage.INVALID_DAY_OF_BIRTH
     * 4: BorrowerFieldErrorMessage.INVALID_MONTH_OF_BIRTH
     * 5: BorrowerFieldErrorMessage.INVALID_YEAR_OF_BIRTH
     * 6: BorrowerFieldErrorMessage.INVALID_STREET
     * 7: BorrowerFieldErrorMessage.INVALID_NO
     * 8: BorrowerFieldErrorMessage.INVALID_PLACE
     * 9: BorrowerFieldErrorMessage.INVALID_ZIP
     * 10: "Could not process the provided birthday! Is it really correct?"
     * 11: "Could not process the provided address!"
     * 12: "Could not create borrower!"
     * 13: "Created borrower successfully!"
     *
     * @param createBorrowerDTO
     * @return
     */
    public int invoke(IRequestDTOBorrowerCreate createBorrowerDTO);
}