package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.BorrowerDeletePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerDeleteActionListener implements ActionListener
{
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;
    private BorrowerDeletePanel deletePanel;

    /**
     * @param deleteBorrowerViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @throws NullPointerException
     */
    public BorrowerDeleteActionListener(IDeleteBorrowerViewHandler deleteBorrowerViewHandler, BorrowerDeletePanel deletePanel) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);

        this.deleteBorrowerViewHandler = deleteBorrowerViewHandler;
        this.deletePanel = deletePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int borrowerID = -1; // TODO Get the ID from the clicked element / table row and parse it to integer.

        IRequestDTOBorrowerDelete requestDTOBorrowerDelete = null;

        try
        {
            requestDTOBorrowerDelete = new RequestDTOBorrowerDelete(borrowerID);
        }
        catch(NullPointerException npe)
        {
            deletePanel.getView().showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        if(deleteBorrowerViewHandler != null)
            deleteBorrowerViewHandler.invoke(requestDTOBorrowerDelete);
    }
}
