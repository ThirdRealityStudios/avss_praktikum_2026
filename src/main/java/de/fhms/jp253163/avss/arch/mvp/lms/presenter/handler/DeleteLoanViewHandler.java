package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;

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

        if(loanRepository.deleteLoan(iRequestDTOLoanDelete.getLoanID()))
            presenter.getView().showMessage("Deleted loan successfully!");
        else
            presenter.getView().showMessage("Deleting loan failed!");
    }
}
