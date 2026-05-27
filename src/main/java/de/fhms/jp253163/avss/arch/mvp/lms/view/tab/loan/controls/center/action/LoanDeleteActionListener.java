package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanDeleteActionListener implements ActionListener
{
    private IDeleteLoanViewHandler deleteLoanViewHandler;
    private LoanDeletePanel deletePanel;

    /**
     * @param deleteLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @throws NullPointerException
     */
    public LoanDeleteActionListener(IDeleteLoanViewHandler deleteLoanViewHandler, LoanDeletePanel deletePanel) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);
        Objects.requireNonNull(deleteLoanViewHandler);

        this.deleteLoanViewHandler = deleteLoanViewHandler;
        this.deletePanel = deletePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int loanID = -1; // TODO Get the ID from the clicked element / table row and parse it to integer.

        IRequestDTOLoanDelete requestDTOLoanDelete = new RequestDTOLoanDelete(loanID);

        if(deleteLoanViewHandler != null)
            deleteLoanViewHandler.invoke(requestDTOLoanDelete);
    }
}