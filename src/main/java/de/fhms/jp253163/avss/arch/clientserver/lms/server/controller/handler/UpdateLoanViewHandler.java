package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class UpdateLoanViewHandler implements IUpdateLoanViewHandler
{
    private ILoanRepository loanRepository;

    public UpdateLoanViewHandler(ILoanRepository loanRepository) throws NullPointerException
    {
        Objects.requireNonNull(loanRepository);

        this.loanRepository = loanRepository;
    }

    @Override
    public int invoke(IRequestDTOLoanUpdate iRequestDTOLoanUpdate)
    {
        if(iRequestDTOLoanUpdate == null)
        {
            return GlobalResponseCode.UPDATE_LOAN_INVALID_REQUEST.ordinal();
        }

        int loanID = iRequestDTOLoanUpdate.getLoanID();

        int borrowerIDAttached = iRequestDTOLoanUpdate.getBorrowerID();
        boolean bookInstallment = iRequestDTOLoanUpdate.isBookInstallmentWanted(); // has always priority (semantically)

        if(loanID < 0)
        {
            return GlobalResponseCode.UPDATE_LOAN_NO_SELECTED_DATASET.ordinal();
        }

        if(!loanRepository.isValidBorrowerIDForUpdate(borrowerIDAttached))
        {
            return GlobalResponseCode.UPDATE_LOAN_INVALID_BORROWER_ID.ordinal();
        }

        ILoan currentLoan = loanRepository.readLoan(loanID);

        if(currentLoan == null)
        {
            return GlobalResponseCode.UPDATE_LOAN_NOT_FOUND.ordinal();
        }

        // Below now: checks if values should stay the same, so use the old values where necessary
        if(borrowerIDAttached == -1) borrowerIDAttached = currentLoan.getBorrower();

        if(bookInstallment)
            currentLoan.payInstallment();
        else
            currentLoan.setBorrower(borrowerIDAttached);

        if(loanRepository.updateLoan(currentLoan))
        {
            return GlobalResponseCode.UPDATE_LOAN_SUCCESS.ordinal();
        }
        else
            return GlobalResponseCode.UPDATE_LOAN_ERROR.ordinal();
    }
}
