package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanDeleteActionListener implements ActionListener
{
    private IReadLoanViewHandler readLoanViewHandler;
    private IDeleteLoanViewHandler deleteLoanViewHandler;
    private LoanDeletePanel deletePanel;

    /**
     * @param readLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deleteLoanViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @throws NullPointerException
     */
    public LoanDeleteActionListener(IReadLoanViewHandler readLoanViewHandler, IDeleteLoanViewHandler deleteLoanViewHandler, LoanDeletePanel deletePanel) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);

        this.readLoanViewHandler = readLoanViewHandler;
        this.deleteLoanViewHandler = deleteLoanViewHandler;
        this.deletePanel = deletePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing LOAN DELETION button CLICKED!");

        int loanID = -1; // TODO Get the ID from the clicked element / table row and parse it to integer.

        IRequestDTOLoanDelete requestDTOLoanDelete = new RequestDTOLoanDelete(loanID);

        System.out.println("Created valid request for LOAN DELETION button CLICKED!");

        if(readLoanViewHandler != null && deleteLoanViewHandler != null)
        {
            System.out.println("Invoking view handler for LOAN DELETION!");
            deleteLoanViewHandler.invoke(requestDTOLoanDelete);

            System.out.println("Invoking view handler for LOAN READ!");
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        }
    }
}