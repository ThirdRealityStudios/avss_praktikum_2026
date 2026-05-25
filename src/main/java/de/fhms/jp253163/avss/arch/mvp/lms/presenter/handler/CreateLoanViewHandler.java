package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;

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
    public void invoke(IRequestDTOLoanCreate iRequestDTOLoanCreate) {
        if(iRequestDTOLoanCreate == null)
            return;

        int borrowerID = iRequestDTOLoanCreate.getBorrower();
        double interestRate = iRequestDTOLoanCreate.getInterestRate();
        double amount = iRequestDTOLoanCreate.getAmount();
        int term = iRequestDTOLoanCreate.getTerm();

        ILoan loan = loanRepository.createLoan(borrowerID, interestRate, amount, term);

        if(loan == null)
            presenter.getView().showMessage("Creating loan failed");
        else
            presenter.getView().showMessage("Created loan successfully");
    }
}
