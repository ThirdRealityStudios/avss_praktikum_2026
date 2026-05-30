package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;

public class DeleteBorrowerViewHandler implements IDeleteBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private IPresenter presenter;

    public DeleteBorrowerViewHandler(IBorrowerRepository borrowerRepository, IPresenter presenter) throws NullPointerException
    {
        if(borrowerRepository == null || presenter == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOBorrowerDelete iRequestDTOBorrowerDelete)
    {
        System.out.println("Starting invocation of borrower deletion!");

        if(iRequestDTOBorrowerDelete == null)
        {
            presenter.getView().showMessage("Invalid deletion request!");

            return;
        }

        if(borrowerRepository.deleteBorrower(iRequestDTOBorrowerDelete.getBorrowerID()))
            presenter.getView().showMessage("Deleted borrower successfully!");
        else
            presenter.getView().showMessage("Deleting borrower failed!");
    }
}
