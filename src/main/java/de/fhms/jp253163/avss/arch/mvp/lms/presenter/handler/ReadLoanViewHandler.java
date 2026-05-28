package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.DTOLoanSet;

import java.util.ArrayList;

public class ReadLoanViewHandler implements IReadLoanViewHandler
{
    private ILoanRepository loanRepository;
    private IPresenter presenter;

    public ReadLoanViewHandler(ILoanRepository loanRepository, IPresenter presenter) throws NullPointerException
    {
        if(loanRepository == null || presenter == null)
            throw new NullPointerException();

        this.loanRepository = loanRepository;
        this.presenter = presenter;
    }

    @Override
    public void invoke(IRequestDTOLoanRead iRequestDTOLoanRead)
    {
        if(iRequestDTOLoanRead == null)
        {
            presenter.getView().showMessage("Invalid read request!");

            return;
        }

        int loanID = iRequestDTOLoanRead.getLoanID();
        ILoan[] loans = null;

        if(loanID == -1) // Read all
        {
            loans = loanRepository.readAllLoans();

            if(loans == null || loans.length == 0)
            {
                presenter.getView().showMessage("Could not find any loans!");
            }
            else
            {
                ArrayList<IDTOLoanSet> loanSets = new ArrayList<IDTOLoanSet>();

                for(ILoan loan : loans)
                {
                    IDTOLoanSet loanSet = new DTOLoanSet(loan.getId(), loan.getAmount(), loan.getRemainingBalance(), loan.getInterestRate(), loan.getTerm(), loan.getRemainingTerm(), loan.getBorrower());

                    loanSets.add(loanSet);
                }

                presenter.getView().setLoanData(loanSets);
            }

            return;
        }
        else
        {
            loans = new ILoan[]{ loanRepository.readLoan(iRequestDTOLoanRead.getLoanID()) };

            if(loans[0] == null)
                presenter.getView().showMessage("Could not find that loan!");
            else
            {
                ArrayList<IDTOLoanSet> loanSets = new ArrayList<IDTOLoanSet>();

                IDTOLoanSet loanSet = new DTOLoanSet(loans[0].getId(), loans[0].getAmount(), loans[0].getRemainingBalance(), loans[0].getInterestRate(), loans[0].getTerm(), loans[0].getRemainingTerm(), loans[0].getBorrower());

                loanSets.add(loanSet);

                presenter.getView().setLoanData(loanSets);
            }

            return;
        }

    }
}
