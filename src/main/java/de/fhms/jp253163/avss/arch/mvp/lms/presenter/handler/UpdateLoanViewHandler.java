package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;

public class UpdateLoanViewHandler implements IUpdateLoanViewHandler
{
    private ILoanRepository loanRepository;
    private IPresenter presenter;

    public UpdateLoanViewHandler(ILoanRepository loanRepository, IPresenter presenter) throws NullPointerException
    {
        if(loanRepository == null || presenter == null)
            throw new NullPointerException();

        this.loanRepository = loanRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOLoanUpdate iRequestDTOLoanUpdate)
    {
        System.out.println("Starting invocation of loan creation!");

        if(iRequestDTOLoanUpdate == null)
        {
            presenter.getView().showMessage("Invalid update request!");

            return;
        }

        int loanID = iRequestDTOLoanUpdate.getLoanID();
        int borrowerIDAttached = iRequestDTOLoanUpdate.getBorrowerID();
        boolean bookInstallment = iRequestDTOLoanUpdate.isBookInstallmentWanted(); // has always priority (semantically)

        ILoan currentLoan = loanRepository.readLoan(loanID);

        if(currentLoan == null)
        {
            presenter.getView().showMessage("Could not find that loan!");

            return;
        }

        if(bookInstallment)
            currentLoan.payInstallment();
        else
            currentLoan.setBorrower(borrowerIDAttached);

        if(loanRepository.updateLoan(currentLoan))
            presenter.getView().showMessage("Updated loan successfully!");
        else
            presenter.getView().showMessage("Updating loan failed!");
    }
}
