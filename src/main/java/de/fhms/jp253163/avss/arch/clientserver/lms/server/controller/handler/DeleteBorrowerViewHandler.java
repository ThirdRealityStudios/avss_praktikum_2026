package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;

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
        if(iRequestDTOBorrowerDelete == null)
        {
            presenter.getView().showMessage("Invalid deletion request!");

            return;
        }

        if(borrowerRepository.deleteBorrower(iRequestDTOBorrowerDelete.getBorrowerID()))
        {
            // Make the borrower table show all datasets now.
            new ReadBorrowerViewHandler(borrowerRepository, presenter).invoke(new RequestDTOBorrowerRead(-1));

            presenter.getView().showMessage("Deleted borrower successfully!");
        }
        else
            presenter.getView().showMessage("Borrower still has active loans or nothing was selected!");
    }
}
