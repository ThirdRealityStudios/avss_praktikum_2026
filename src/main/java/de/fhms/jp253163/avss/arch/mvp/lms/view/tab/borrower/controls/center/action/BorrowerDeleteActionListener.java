package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.BorrowerDeletePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerDeleteActionListener implements ActionListener
{
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;
    private BorrowerDeletePanel deletePanel;

    /**
     * @param readBorrowerViewHandler Or null is allowed too (do run nothing in this case)
     * @param deleteBorrowerViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @throws NullPointerException
     */
    public BorrowerDeleteActionListener(IReadBorrowerViewHandler readBorrowerViewHandler, IDeleteBorrowerViewHandler deleteBorrowerViewHandler, BorrowerDeletePanel deletePanel) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);

        this.readBorrowerViewHandler = readBorrowerViewHandler;
        this.deleteBorrowerViewHandler = deleteBorrowerViewHandler;
        this.deletePanel = deletePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing BORROWER DELETION button CLICKED!");

        int borrowerID = -1; // TODO Get the ID from the clicked element / table row and parse it to integer.

        IRequestDTOBorrowerDelete requestDTOBorrowerDelete = null;

        try
        {
            requestDTOBorrowerDelete = new RequestDTOBorrowerDelete(borrowerID);
        }
        catch(NullPointerException npe)
        {
            deletePanel.getView().showMessage("BORROWER DELETION failed!");

            return;
        }

        System.out.println("Created valid request for BORROWER DELETION button CLICKED!");

        if(readBorrowerViewHandler != null && deleteBorrowerViewHandler != null)
        {
            System.out.println("Invoking view handler for BORROWER DELETION!");
            deleteBorrowerViewHandler.invoke(requestDTOBorrowerDelete);

            System.out.println("Invoking view handler for BORROWER READ!");
            readBorrowerViewHandler.invoke(new RequestDTOBorrowerRead(-1));
        }
    }
}
