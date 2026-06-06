package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;

public class CreateLoanViewHandler implements ICreateLoanViewHandler
{
    private ILoanRepository loanRepository;
    private IPresenter presenter;

    public CreateLoanViewHandler(ILoanRepository loanRepository, IPresenter presenter) throws NullPointerException
    {
        if(loanRepository == null || presenter == null)
            throw new NullPointerException();

        this.loanRepository = loanRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOLoanCreate iRequestDTOLoanCreate)
    {
        if(iRequestDTOLoanCreate == null)
        {
            presenter.getView().showMessage("Invalid creation request!");

            return;
        }

        int borrowerID = iRequestDTOLoanCreate.getBorrower();
        double interestRate = iRequestDTOLoanCreate.getInterestRate();
        double amount = iRequestDTOLoanCreate.getAmount();
        int term = iRequestDTOLoanCreate.getTerm();

        if(!loanRepository.isValidBorrowerIDForCreation(borrowerID))
        {
            presenter.getView().showMessage(LoanFieldErrorMessage.INVALID_BORROWER_ID);
            return;
        }

        if(!loanRepository.isValidInterestRateForCreation(interestRate))
        {
            presenter.getView().showMessage(LoanFieldErrorMessage.INVALID_INTEREST_RATE);
            return;
        }

        if(!loanRepository.isValidAmountForCreation(amount))
        {
            presenter.getView().showMessage(LoanFieldErrorMessage.INVALID_AMOUNT);
            return;
        }

        if(!loanRepository.isValidTermForCreation(term))
        {
            presenter.getView().showMessage(LoanFieldErrorMessage.INVALID_TERM);
            return;
        }

        ILoan loan = loanRepository.createLoan(borrowerID, amount, interestRate, term);

        if(loan == null)
            presenter.getView().showMessage("Creating loan failed");
        else
        {
            // Make the loan table show all datasets now.
            new ReadLoanViewHandler(loanRepository, presenter).invoke(new RequestDTOLoanRead(-1));

            presenter.getView().showMessage("Created loan successfully");
        }
    }
}
