package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private IView view;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private BorrowerTextfieldPanel textfieldPanel;

    // view, createBorrowerViewHandler, sharedLogic
    public BorrowerUpdateActionListener(IView view, IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
        this.textfieldPanel = textfieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = BorrowerSharedLogic.genUpdateLoanDTO(view, textfieldPanel);
        }
        catch(NullPointerException npe)
        {
            view.showMessage("Borrower update failed!");

            return;
        }

        if(updateBorrowerViewHandler != null)
            updateBorrowerViewHandler.invoke(requestDTOBorrowerUpdate);
    }
}
