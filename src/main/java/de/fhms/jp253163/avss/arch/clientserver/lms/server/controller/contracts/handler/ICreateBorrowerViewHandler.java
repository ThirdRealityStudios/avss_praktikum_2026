package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerCreate;

public interface ICreateBorrowerViewHandler
{
    /**
     * Returns the following response codes
     * (from enum GlobalResponseCode):
     *
     * BORROWER_INVALID_CREATION_REQUEST,
     * BORROWER_INVALID_FIRSTNAME,
     * BORROWER_INVALID_LASTNAME,
     * BORROWER_INVALID_DAY_OF_BIRTH,
     * BORROWER_INVALID_MONTH_OF_BIRTH,
     * BORROWER_INVALID_YEAR_OF_BIRTH,
     * BORROWER_INVALID_STREET,
     * BORROWER_INVALID_NO,
     * BORROWER_INVALID_PLACE,
     * BORROWER_INVALID_ZIP,
     * BORROWER_BIRTHDAY_PARSE_ERROR,
     * BORROWER_ADDRESS_PARSE_ERROR,
     * BORROWER_CREATION_ERROR,
     * BORROWER_CREATION_SUCCESS
     *
     * @param createBorrowerDTO
     * @return Code for HTTP response
     */
    public int invoke(IRequestDTOBorrowerCreate createBorrowerDTO);
}