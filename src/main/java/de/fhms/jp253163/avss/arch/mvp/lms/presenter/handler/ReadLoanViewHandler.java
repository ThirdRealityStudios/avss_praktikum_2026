package de.fhms.jp253163.avss.arch.mvp.lms.presenter.handler;

import de.fhms.jp253163.avss.arch.mvp.lms.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.mvp.lms.model.data.ILoan;
import de.fhms.jp253163.avss.arch.mvp.lms.presenter.IPresenter;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;

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
                presenter.getView().showMessage("The following loans do exist:");

                for(ILoan loan : loans)
                    presenter.getView().showMessage(loan.toString());
            }

            return;
        }
        else
        {
            loans = new ILoan[]{ loanRepository.readLoan(iRequestDTOLoanRead.getLoanID()) };

            if(loans[0] == null)
                presenter.getView().showMessage("Could not find that loan!");
            else
                presenter.getView().showMessage(loans[0].toString());

            return;
        }

    }
}
