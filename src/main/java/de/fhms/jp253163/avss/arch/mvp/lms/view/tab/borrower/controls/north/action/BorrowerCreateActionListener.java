package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerCreateActionListener implements ActionListener
{
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private BorrowerCreateUpdatePanel createUpdatePanel;

    public BorrowerCreateActionListener(ICreateBorrowerViewHandler createBorrowerViewHandler, BorrowerCreateUpdatePanel createUpdatePanel) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(createUpdatePanel);

        this.createBorrowerViewHandler = createBorrowerViewHandler;
        this.createUpdatePanel = createUpdatePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BorrowerSharedLogic sharedLogic = new BorrowerSharedLogic(createUpdatePanel);

        BorrowerSharedLogicDTO dto = sharedLogic.prepareUserEntries(true);

        IRequestDTOBorrowerCreate requestDTOBorrowerCreate = null;

        try
        {
            requestDTOBorrowerCreate = new RequestDTOBorrowerCreate(dto.getFirstname(), dto.getLastname(), dto.getStreet(), dto.getNo(), dto.getPlace(), dto.getZip(), dto.getBirthday());
        }
        catch(NullPointerException npe)
        {
            createUpdatePanel.getView().showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        createBorrowerViewHandler.invoke(requestDTOBorrowerCreate);
    }
}