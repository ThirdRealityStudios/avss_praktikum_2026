package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;

public class ReadBorrowerViewHandler implements IReadBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private IPresenter presenter;

    public ReadBorrowerViewHandler(IBorrowerRepository borrowerRepository, IPresenter presenter) throws NullPointerException
    {
        if(borrowerRepository == null || presenter == null)
            throw new NullPointerException();

        this.borrowerRepository = borrowerRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOBorrowerRead iRequestDTOBorrowerRead) {
        if(iRequestDTOBorrowerRead == null)
        {
            presenter.getView().showMessage("Invalid read request!");

            return;
        }

        IBorrower[] borrowers = null;
        int borrowerID = iRequestDTOBorrowerRead.getBorrowerID();

        if(borrowerID == -1)
        {
            borrowers = borrowerRepository.readAllBorrowers();

            if(borrowers == null || borrowers.length == 0)
            {
                presenter.getView().showMessage("Could not find any borrowers!");
            }
            else
            {
                presenter.getView().showMessage("The following borrowers do exist:");

                for(IBorrower borrower : borrowers)
                    presenter.getView().showMessage(borrower.toString());
            }
        }
        else
        {
            borrowers = new IBorrower[]{ borrowerRepository.readBorrower(iRequestDTOBorrowerRead.getBorrowerID()) };

            if(borrowers[0] == null)
                presenter.getView().showMessage("Could not find that borrower!");
            else
                presenter.getView().showMessage(borrowers[0].toString());

            return;
        }
    }
}