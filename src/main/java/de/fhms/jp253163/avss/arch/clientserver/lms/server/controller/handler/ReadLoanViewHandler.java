package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;

import java.util.ArrayList;
import java.util.Objects;

public class ReadLoanViewHandler implements IReadLoanViewHandler
{
    private ILoanRepository loanRepository;

    public ReadLoanViewHandler(ILoanRepository loanRepository) throws NullPointerException
    {
        Objects.requireNonNull(loanRepository);

        this.loanRepository = loanRepository;
    }

    @Override
    public void invoke(IRequestDTOLoanRead iRequestDTOLoanRead)
    {
        if(iRequestDTOLoanRead == null)
        {
            presenter.getView().showMessage("Invalid read request!");

            return;
        }

        ILoan[] loans = null;
        int loanID = iRequestDTOLoanRead.getLoanID();

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
            ILoan loan = loanRepository.readLoan(loanID);

            if(loan == null)
                presenter.getView().showMessage("Could not find that loan or negative ID!");
            else
            {
                ArrayList<IDTOLoanSet> loanSets = new ArrayList<IDTOLoanSet>();

                IDTOLoanSet loanSet = new DTOLoanSet(loan.getId(), loan.getAmount(), loan.getRemainingBalance(), loan.getInterestRate(), loan.getTerm(), loan.getRemainingTerm(), loan.getBorrower());

                loanSets.add(loanSet);

                presenter.getView().setLoanData(loanSets);
            }

            return;
        }

    }
}
