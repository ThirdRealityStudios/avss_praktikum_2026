package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

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
            return 0;
        }

        int borrowerID = iRequestDTOLoanCreate.getBorrower();
        double interestRate = iRequestDTOLoanCreate.getInterestRate();
        double amount = iRequestDTOLoanCreate.getAmount();
        int term = iRequestDTOLoanCreate.getTerm();

        if(!loanRepository.isValidBorrowerIDForCreation(borrowerID))
        {
            return 1;
        }

        if(!loanRepository.isValidInterestRateForCreation(interestRate))
        {
            return 2;
        }

        if(!loanRepository.isValidAmountForCreation(amount))
        {
            return 3;
        }

        if(!loanRepository.isValidTermForCreation(term))
        {
            return 4;
        }

        ILoan loan = loanRepository.createLoan(borrowerID, amount, interestRate, term);

        if(loan == null)
            return 5;
        else
        {
            // Make the loan table show all datasets now.
            new ReadLoanViewHandler(loanRepository).invoke(new RequestDTOLoanRead(-1));

           return 6;
        }
    }
}
