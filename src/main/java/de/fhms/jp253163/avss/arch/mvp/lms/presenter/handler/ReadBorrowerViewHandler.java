package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.IBorrower;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.DTOBorrowerSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.DTOLoanSet;

import java.util.ArrayList;

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
                ArrayList<IDTOBorrowerSet> borrowerSets = new ArrayList<IDTOBorrowerSet>();

                for(IBorrower borrower : borrowers)
                {
                    IDTOBorrowerSet borrowerSet = new DTOBorrowerSet(borrower.getId(), borrower.getFirstname(), borrower.getLastname(), borrower.getDateOfBirth(), borrower.getResidentialAddress().getStreet(), borrower.getResidentialAddress().getNo(), borrower.getResidentialAddress().getPlace(), borrower.getResidentialAddress().getZip());

                    borrowerSets.add(borrowerSet);
                }

                presenter.getView().setBorrowerData(borrowerSets);
            }
        }
        else
        {
            borrowers = new IBorrower[]{ borrowerRepository.readBorrower(iRequestDTOBorrowerRead.getBorrowerID()) };

            if(borrowers[0] == null)
                presenter.getView().showMessage("Could not find that borrower!");
            else
            {
                ArrayList<IDTOBorrowerSet> borrowerSets = new ArrayList<IDTOBorrowerSet>();

                IDTOBorrowerSet borrowerSet = new DTOBorrowerSet(borrowers[0].getId(), borrowers[0].getFirstname(), borrowers[0].getLastname(), borrowers[0].getDateOfBirth(), borrowers[0].getResidentialAddress().getStreet(), borrowers[0].getResidentialAddress().getNo(), borrowers[0].getResidentialAddress().getPlace(), borrowers[0].getResidentialAddress().getZip());

                borrowerSets.add(borrowerSet);

                presenter.getView().setBorrowerData(borrowerSets);
            }

            return;
        }
    }
}