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
    public void invoke(IRequestDTOLoanRead iRequestDTOLoanRead) {
        if(iRequestDTOLoanRead == null)
            return;

        ILoan loan = loanRepository.readLoan(iRequestDTOLoanRead.getLoanID());

        if(loan == null)
            presenter.getView().showMessage("Could not find that loan!");
        else
            presenter.getView().showMessage(loan.toString());
    }
}
