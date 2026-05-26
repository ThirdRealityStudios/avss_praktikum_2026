package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerCreate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerCreateActionListener implements ActionListener
{
    private IView view;
    private BorrowerSharedLogic sharedLogic;
    private ICreateBorrowerViewHandler createBorrowerViewHandler;

    public BorrowerCreateActionListener(IView view, ICreateBorrowerViewHandler createBorrowerViewHandler, BorrowerSharedLogic sharedLogic) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(sharedLogic);

        this.view = view;
        this.sharedLogic = sharedLogic;
        this.createBorrowerViewHandler = createBorrowerViewHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BorrowerSharedLogicDTO dto = sharedLogic.prepareUserEntries(true);

        IRequestDTOBorrowerCreate requestDTOBorrowerCreate = null;

        try
        {
            requestDTOBorrowerCreate = new RequestDTOBorrowerCreate(dto.getFirstname(), dto.getLastname(), dto.getStreet(), dto.getNo(), dto.getPlace(), dto.getZip(), dto.getBirthday());
        }
        catch(NullPointerException npe)
        {
            view.showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        if(createBorrowerViewHandler != null)
            createBorrowerViewHandler.invoke(requestDTOBorrowerCreate);
    }
}