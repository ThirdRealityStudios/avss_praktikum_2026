package de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;

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
        if(iRequestDTOLoanUpdate == null)
        {
            presenter.getView().showMessage("Invalid update request!");

            return;
        }

        int loanID = iRequestDTOLoanUpdate.getLoanID();

        int borrowerIDAttached = iRequestDTOLoanUpdate.getBorrowerID();
        boolean bookInstallment = iRequestDTOLoanUpdate.isBookInstallmentWanted(); // has always priority (semantically)

        if(loanID < 0)
        {
            presenter.getView().showMessage("Please select a loan! (dataset for retrieving loan ID)");
            return;
        }

        if(!loanRepository.isValidBorrowerIDForUpdate(borrowerIDAttached))
        {
            presenter.getView().showMessage(LoanFieldErrorMessage.INVALID_BORROWER_ID);
            return;
        }

        ILoan currentLoan = loanRepository.readLoan(loanID);

        if(currentLoan == null)
        {
            presenter.getView().showMessage("Could not find that loan!");

            return;
        }

        // Below now: checks if values should stay the same, so use the old values where necessary
        if(borrowerIDAttached == -1) borrowerIDAttached = currentLoan.getBorrower();

        if(bookInstallment)
            currentLoan.payInstallment();
        else
            currentLoan.setBorrower(borrowerIDAttached);

        if(loanRepository.updateLoan(currentLoan))
        {
            // Make the loan table show all datasets now.
            new ReadLoanViewHandler(loanRepository, presenter).invoke(new RequestDTOLoanRead(-1));

            presenter.getView().showMessage("Updated loan successfully!");
        }
        else
            presenter.getView().showMessage("Updating loan failed!");
    }
}
