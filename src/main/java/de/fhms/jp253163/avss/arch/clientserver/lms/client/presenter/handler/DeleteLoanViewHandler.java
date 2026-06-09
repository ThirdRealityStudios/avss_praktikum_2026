package de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;

public class DeleteLoanViewHandler implements IDeleteLoanViewHandler
{
    private ILoanRepository loanRepository;
    private IPresenter presenter;

    public DeleteLoanViewHandler(ILoanRepository loanRepository, IPresenter presenter) throws NullPointerException
    {
        if(loanRepository == null || presenter == null)
            throw new NullPointerException();

        this.loanRepository = loanRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOLoanDelete iRequestDTOLoanDelete)
    {
        if(iRequestDTOLoanDelete == null)
        {
            presenter.getView().showMessage("Invalid deletion request!");

            return;
        }

        if(iRequestDTOLoanDelete.getLoanID() == -1)
            presenter.getView().showMessage("Please select a dataset!");
        else if(loanRepository.deleteLoan(iRequestDTOLoanDelete.getLoanID()))
        {
            // Make the loan table show all datasets now.
            new ReadLoanViewHandler(loanRepository, presenter).invoke(new RequestDTOLoanRead(-1));

            presenter.getView().showMessage("Deleted loan successfully!");
        }
        else
            presenter.getView().showMessage("Borrower needs to pay the loan first before deletion!");
    }
}
