package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerUpdate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private IView view;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private BorrowerSharedLogic sharedLogic;

    // view, createBorrowerViewHandler, sharedLogic
    public BorrowerUpdateActionListener(IView view, IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerSharedLogic sharedLogic) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(sharedLogic);

        this.view = view;
        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
        this.sharedLogic = sharedLogic;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        BorrowerSharedLogicDTO dto = sharedLogic.prepareUserEntries(false);

        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = new RequestDTOBorrowerUpdate(dto.getBorrowerId(), dto.getFirstname(), dto.getLastname(), dto.getBirthday(), dto.getStreet(), dto.getNo(), dto.getPlace(), dto.getZip(), null);
        }
        catch(NullPointerException npe)
        {
            view.showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        if(updateBorrowerViewHandler != null)
            updateBorrowerViewHandler.invoke(requestDTOBorrowerUpdate);
    }
}
