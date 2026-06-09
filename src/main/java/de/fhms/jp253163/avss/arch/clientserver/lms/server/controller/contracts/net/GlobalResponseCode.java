package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net;

public enum GlobalResponseCode
{
    /**
     * DO NEVER CHANGE THE ORDER OR DELETE ENUMS!
     * ONLY ADDING NEW LINES / ENUMS IS ALLOWED AFTER
     * THE LAST ENUM AT THE BOTTOM!
     * OTHERWISE CLIENT / SERVER COMMUNICATION WILL FAIL
     * DUE TO CHANGED ORDINAL VALUES!!!
     */

    CREATE_BORROWER_INVALID_CREATION_REQUEST (0),
    CREATE_BORROWER_INVALID_FIRSTNAME (1),
    CREATE_BORROWER_INVALID_LASTNAME (2),
    CREATE_BORROWER_INVALID_DAY_OF_BIRTH (3),
    CREATE_BORROWER_INVALID_MONTH_OF_BIRTH (4),
    CREATE_BORROWER_INVALID_YEAR_OF_BIRTH (5),
    CREATE_BORROWER_INVALID_STREET (6),
    CREATE_BORROWER_INVALID_NO (7),
    CREATE_BORROWER_INVALID_PLACE (8),
    CREATE_BORROWER_INVALID_ZIP (9),
    CREATE_BORROWER_BIRTHDAY_PARSE_ERROR (10),
    CREATE_BORROWER_ADDRESS_PARSE_ERROR (11),
    CREATE_BORROWER_CREATION_ERROR (12),
    CREATE_BORROWER_CREATION_SUCCESS (13),

    CREATE_LOAN_INVALID_CREATION_REQUEST (14),
    CREATE_LOAN_INVALID_BORROWER_ID (15),
    CREATE_LOAN_INVALID_INTEREST_RATE (16),
    CREATE_LOAN_INVALID_AMOUNT (17),
    CREATE_LOAN_INVALID_TERM (18),
    CREATE_LOAN_CREATION_ERROR (19),
    CREATE_LOAN_CREATION_SUCCESS (20),

    READ_BORROWER_INVALID_REQUEST (21),
    READ_BORROWER_EMPTY_DATABASE (22), // "Could not find any borrowers!"
    READ_BORROWER_NOT_FOUND (23), // "Could not find that borrower or negative ID!"
    READ_BORROWER_FOUND_SUCCESS (24),

    READ_LOAN_INVALID_REQUEST (25), // "Invalid read request!"
    READ_LOAN_EMPTY_DATABASE (26), // "Could not find any loans!"
    READ_LOAN_FOUND_SUCCESS (27),
    READ_LOAN_NOT_FOUND (28), // "Could not find that loan or negative ID!"

    UPDATE_BORROWER_INVALID_REQUEST (29),
    UPDATE_BORROWER_NO_SELECTED_DATASET (30), // "Please select a borrower! (dataset for retrieving borrower ID)"
    UPDATE_BORROWER_INVALID_FIRSTNAME (31), // BorrowerFieldErrorMessage.INVALID_FIRSTNAME
    UPDATE_BORROWER_INVALID_LASTNAME (32), // BorrowerFieldErrorMessage.INVALID_LASTNAME
    UPDATE_BORROWER_INVALID_DAY_OF_BIRTH (33), // BorrowerFieldErrorMessage.INVALID_DAY_OF_BIRTH
    UPDATE_BORROWER_INVALID_MONTH_OF_BIRTH (34), // BorrowerFieldErrorMessage.INVALID_MONTH_OF_BIRTH
    UPDATE_BORROWER_INVALID_YEAR_OF_BIRTH (35), // BorrowerFieldErrorMessage.INVALID_YEAR_OF_BIRTH
    UPDATE_BORROWER_INVALID_STREET (36), // BorrowerFieldErrorMessage.INVALID_STREET
    UPDATE_BORROWER_INVALID_NO (37), // BorrowerFieldErrorMessage.INVALID_NO
    UPDATE_BORROWER_INVALID_PLACE (38), // BorrowerFieldErrorMessage.INVALID_PLACE
    UPDATE_BORROWER_INVALID_ZIP (39), // BorrowerFieldErrorMessage.INVALID_ZIP
    UPDATE_BORROWER_NOT_FOUND (40), // "Could not find that borrower!"
    UPDATE_BORROWER_ADDRESS_PARSE_ERROR (41), // "Could not process old address!"
    UPDATE_BORROWER_BIRTHDAY_PARSE_ERROR (42), // "Could not process new birthday!"
    UPDATE_BORROWER_SUCCESS (43), // "Updated borrower successfully!"
    UPDATE_BORROWER_ERROR (44), // "Failed to update borrower!"

    UPDATE_LOAN_INVALID_REQUEST (45), // "Invalid update request!"
    UPDATE_LOAN_NO_SELECTED_DATASET (46), // "Please select a loan! (dataset for retrieving loan ID)"
    UPDATE_LOAN_INVALID_BORROWER_ID (47), // LoanFieldErrorMessage.INVALID_BORROWER_ID
    UPDATE_LOAN_NOT_FOUND (48), // "Could not find that loan!"
    UPDATE_LOAN_SUCCESS (49), // "Updated loan successfully!"
    UPDATE_LOAN_ERROR (50), // "Updating loan failed!"

    DELETE_BORROWER_INVALID_REQUEST (51), // "Invalid deletion request!"
    DELETE_BORROWER_SUCCESS (52), // "Deleted borrower successfully!"
    DELETE_BORROWER_ERROR (53), // "Borrower still has active loans or nothing was selected!"

    DELETE_LOAN_INVALID_REQUEST (54), // "Invalid deletion request!"
    DELETE_LOAN_NO_SELECTED_DATASET (55), // "Please select a dataset!"
    DELETE_LOAN_SUCCESS (56), // "Deleted loan successfully!"
    DELETE_LOAN_NOT_PAID (57); // "Borrower needs to pay the loan first before deletion!"

    private int code;

    private GlobalResponseCode(int code)
    {
        this.code = code;
    }

    private int code()
    {
        return code;
    }
}
