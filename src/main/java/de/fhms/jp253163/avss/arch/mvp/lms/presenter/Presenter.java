package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler.*;
import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;

public class Presenter implements IPresenter
{
    private IView view;
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;

    public Presenter(IView view, IBorrowerRepository borrowerRepository, ILoanRepository loanRepository) throws NullPointerException
    {
        if(!setView(view) || borrowerRepository == null || loanRepository == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public boolean setView(IView view)
    {
        if(view == null)
            return false;

        this.view = view;

        ICreateBorrowerViewHandler createBorrowerViewHandler = new CreateBorrowerViewHandler(borrowerRepository, this);
        getView().setCreateBorrowerViewHandler(createBorrowerViewHandler);

        ICreateLoanViewHandler createLoanViewHandler = new CreateLoanViewHandler(loanRepository, this);
        getView().setCreateLoanViewHandler(createLoanViewHandler);

        IReadBorrowerViewHandler readBorrowerViewHandler = new ReadBorrowerViewHandler(borrowerRepository, this);
        getView().setReadBorrowerViewHandler(readBorrowerViewHandler);

        IReadLoanViewHandler readLoanViewHandler = new ReadLoanViewHandler(loanRepository, this);
        getView().setReadLoanViewHandler(readLoanViewHandler);

        IUpdateBorrowerViewHandler updateBorrowerViewHandler = new UpdateBorrowerViewHandler(borrowerRepository, this);
        getView().setUpdateBorrowerViewHandler(updateBorrowerViewHandler);

        IUpdateLoanViewHandler updateLoanViewHandler = new UpdateLoanViewHandler(loanRepository, this);
        getView().setUpdateLoanViewHandler(updateLoanViewHandler);

        IDeleteBorrowerViewHandler deleteBorrowerViewHandler = new DeleteBorrowerViewHandler(borrowerRepository, this);
        getView().setDeleteBorrowerViewHandler(deleteBorrowerViewHandler);

        IDeleteLoanViewHandler deleteLoanViewHandler = new DeleteLoanViewHandler(loanRepository, this);
        getView().setDeleteLoanViewHandler(deleteLoanViewHandler);

        return true;
    }

    @Override
    public IView getView() {
        return view;
    }
}
