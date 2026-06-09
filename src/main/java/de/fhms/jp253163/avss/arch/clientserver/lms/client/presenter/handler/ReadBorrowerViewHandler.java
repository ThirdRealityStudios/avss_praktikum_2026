package de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.presenter.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.DTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.*;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.*;

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
    public void invoke(IRequestDTOBorrowerRead iRequestDTOBorrowerRead)
    {
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
            IBorrower borrower = borrowerRepository.readBorrower(borrowerID);

            if(borrower == null)
                presenter.getView().showMessage("Could not find that borrower or negative ID!");
            else
            {
                ArrayList<IDTOBorrowerSet> borrowerSets = new ArrayList<IDTOBorrowerSet>();

                IDTOBorrowerSet borrowerSet = new DTOBorrowerSet(borrower.getId(), borrower.getFirstname(), borrower.getLastname(), borrower.getDateOfBirth(), borrower.getResidentialAddress().getStreet(), borrower.getResidentialAddress().getNo(), borrower.getResidentialAddress().getPlace(), borrower.getResidentialAddress().getZip());

                borrowerSets.add(borrowerSet);

                presenter.getView().setBorrowerData(borrowerSets);
            }

            return;
        }
    }
}