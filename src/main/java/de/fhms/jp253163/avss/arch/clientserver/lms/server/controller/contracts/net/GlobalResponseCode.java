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

    CREATE_BORROWER_INVALID_CREATION_REQUEST,
    CREATE_BORROWER_INVALID_FIRSTNAME,
    CREATE_BORROWER_INVALID_LASTNAME,
    CREATE_BORROWER_INVALID_DAY_OF_BIRTH,
    CREATE_BORROWER_INVALID_MONTH_OF_BIRTH,
    CREATE_BORROWER_INVALID_YEAR_OF_BIRTH,
    CREATE_BORROWER_INVALID_STREET,
    CREATE_BORROWER_INVALID_NO,
    CREATE_BORROWER_INVALID_PLACE,
    CREATE_BORROWER_INVALID_ZIP,
    CREATE_BORROWER_BIRTHDAY_PARSE_ERROR,
    CREATE_BORROWER_ADDRESS_PARSE_ERROR,
    CREATE_BORROWER_CREATION_ERROR,
    CREATE_BORROWER_CREATION_SUCCESS,

    CREATE_LOAN_INVALID_CREATION_REQUEST,
    CREATE_LOAN_INVALID_BORROWER_ID,
    CREATE_LOAN_INVALID_INTEREST_RATE,
    CREATE_LOAN_INVALID_AMOUNT,
    CREATE_LOAN_INVALID_TERM,
    CREATE_LOAN_CREATION_ERROR,
    CREATE_LOAN_CREATION_SUCCESS,

    READ_BORROWER_INVALID_REQUEST,
    READ_BORROWER_EMPTY_DATABASE, // "Could not find any borrowers!"
    READ_BORROWER_NOT_FOUND, // "Could not find that borrower or negative ID!"
    READ_BORROWER_FOUND_SUCCESS,

    READ_LOAN_INVALID_REQUEST, // "Invalid read request!"
    READ_LOAN_EMPTY_DATABASE, // "Could not find any loans!"
    READ_LOAN_FOUND_SUCCESS,
    READ_LOAN_NOT_FOUND, // "Could not find that loan or negative ID!"

    UPDATE_BORROWER_INVALID_REQUEST,
    UPDATE_BORROWER_NO_SELECTED_DATASET, // "Please select a borrower! (dataset for retrieving borrower ID)"
    UPDATE_BORROWER_INVALID_FIRSTNAME, // BorrowerFieldErrorMessage.INVALID_FIRSTNAME
    UPDATE_BORROWER_INVALID_LASTNAME, // BorrowerFieldErrorMessage.INVALID_LASTNAME
    UPDATE_BORROWER_INVALID_DAY_OF_BIRTH, // BorrowerFieldErrorMessage.INVALID_DAY_OF_BIRTH
    UPDATE_BORROWER_INVALID_MONTH_OF_BIRTH, // BorrowerFieldErrorMessage.INVALID_MONTH_OF_BIRTH
    UPDATE_BORROWER_INVALID_YEAR_OF_BIRTH, // BorrowerFieldErrorMessage.INVALID_YEAR_OF_BIRTH
    UPDATE_BORROWER_INVALID_STREET, // BorrowerFieldErrorMessage.INVALID_STREET
    UPDATE_BORROWER_INVALID_NO, // BorrowerFieldErrorMessage.INVALID_NO
    UPDATE_BORROWER_INVALID_PLACE, // BorrowerFieldErrorMessage.INVALID_PLACE
    UPDATE_BORROWER_INVALID_ZIP, // BorrowerFieldErrorMessage.INVALID_ZIP
    UPDATE_BORROWER_NOT_FOUND, // "Could not find that borrower!"
    UPDATE_BORROWER_ADDRESS_PARSE_ERROR, // "Could not process old address!"
    UPDATE_BORROWER_BIRTHDAY_PARSE_ERROR, // "Could not process new birthday!"
    UPDATE_BORROWER_SUCCESS, // "Updated borrower successfully!"
    UPDATE_BORROWER_ERROR, // "Failed to update borrower!"

    UPDATE_LOAN_INVALID_REQUEST, // "Invalid update request!"
    UPDATE_LOAN_NO_SELECTED_DATASET, // "Please select a loan! (dataset for retrieving loan ID)"
    UPDATE_LOAN_INVALID_BORROWER_ID, // LoanFieldErrorMessage.INVALID_BORROWER_ID
    UPDATE_LOAN_NOT_FOUND, // "Could not find that loan!"
    UPDATE_LOAN_SUCCESS, // "Updated loan successfully!"
    UPDATE_LOAN_ERROR, // "Updating loan failed!"

    DELETE_BORROWER_INVALID_REQUEST, // "Invalid deletion request!"
    DELETE_BORROWER_SUCCESS, // "Deleted borrower successfully!"
    DELETE_BORROWER_ERROR, // "Borrower still has active loans or nothing was selected!"

    DELETE_LOAN_INVALID_REQUEST, // "Invalid deletion request!"
    DELETE_LOAN_NO_SELECTED_DATASET, // "Please select a dataset!"
    DELETE_LOAN_SUCCESS, // "Deleted loan successfully!"
    DELETE_LOAN_NOT_PAID // "Borrower needs to pay the loan first before deletion!"

}
