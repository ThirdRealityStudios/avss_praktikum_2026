package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto.RequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private BorrowerCreateUpdatePanel createUpdatePanel;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;

    public BorrowerUpdateActionListener(IUpdateBorrowerViewHandler updateBorrowerViewHandler, BorrowerCreateUpdatePanel createUpdatePanel)
    {
        Objects.requireNonNull(updateBorrowerViewHandler);
        Objects.requireNonNull(createUpdatePanel);

        this.createUpdatePanel = createUpdatePanel;
        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        BorrowerSharedLogic sharedLogic = new BorrowerSharedLogic(createUpdatePanel);

        BorrowerSharedLogicDTO dto = sharedLogic.prepareUserEntries(false);

        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = new RequestDTOBorrowerUpdate(dto.getBorrowerId(), dto.getFirstname(), dto.getLastname(), dto.getBirthday(), dto.getStreet(), dto.getNo(), dto.getPlace(), dto.getZip(), null);
        }
        catch(NullPointerException npe)
        {
            createUpdatePanel.getView().showMessage("Borrower creation failed! (technical issue)");

            return;
        }

        updateBorrowerViewHandler.invoke(requestDTOBorrowerUpdate);
    }
}
