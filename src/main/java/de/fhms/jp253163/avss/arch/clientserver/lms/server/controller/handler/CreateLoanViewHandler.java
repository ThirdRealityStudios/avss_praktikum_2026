package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.Objects;

public class CreateLoanViewHandler implements ICreateLoanViewHandler
{
    private ILoanRepository loanRepository;

    public CreateLoanViewHandler(ILoanRepository loanRepository) throws NullPointerException
    {
        Objects.requireNonNull(loanRepository);

        this.loanRepository = loanRepository;
    }

    @Override
    public int invoke(IRequestDTOLoanCreate iRequestDTOLoanCreate)
    {
        if(iRequestDTOLoanCreate == null)
        {
            return GlobalResponseCode.CREATE_LOAN_INVALID_CREATION_REQUEST.ordinal();
        }

        int borrowerID = iRequestDTOLoanCreate.getBorrower();
        double interestRate = iRequestDTOLoanCreate.getInterestRate();
        double amount = iRequestDTOLoanCreate.getAmount();
        int term = iRequestDTOLoanCreate.getTerm();

        if(!loanRepository.isValidBorrowerIDForCreation(borrowerID))
        {
            return GlobalResponseCode.CREATE_LOAN_INVALID_BORROWER_ID.ordinal();
        }

        if(!loanRepository.isValidInterestRateForCreation(interestRate))
        {
            return GlobalResponseCode.CREATE_LOAN_INVALID_INTEREST_RATE.ordinal();
        }

        if(!loanRepository.isValidAmountForCreation(amount))
        {
            return GlobalResponseCode.CREATE_LOAN_INVALID_AMOUNT.ordinal();
        }

        if(!loanRepository.isValidTermForCreation(term))
        {
            return GlobalResponseCode.CREATE_LOAN_INVALID_TERM.ordinal();
        }

        ILoan loan = loanRepository.createLoan(borrowerID, amount, interestRate, term);

        if(loan == null)
            return GlobalResponseCode.CREATE_LOAN_CREATION_ERROR.ordinal();
        else
        {
            // Make the loan table show all datasets now.
            new ReadLoanViewHandler(loanRepository).invoke(new RequestDTOLoanRead(-1));

           return GlobalResponseCode.CREATE_LOAN_CREATION_SUCCESS.ordinal();
        }
    }
}
