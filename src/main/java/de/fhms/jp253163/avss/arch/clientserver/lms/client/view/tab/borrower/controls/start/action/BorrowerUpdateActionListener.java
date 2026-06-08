package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.IView;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private IView view;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private IReadBorrowerViewHandler readBorrowerViewHandler;

    private BorrowerTextfieldPanel textfieldPanel;
    private ScrollableTablePanel scrollableTablePanel;

    // view, createBorrowerViewHandler, sharedLogic
    public BorrowerUpdateActionListener(IView view, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel, ScrollableTablePanel scrollableTablePanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(scrollableTablePanel);

        this.view = view;
        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
        this.readBorrowerViewHandler = readBorrowerViewHandler;
        this.textfieldPanel = textfieldPanel;
        this.scrollableTablePanel = scrollableTablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = BorrowerSharedLogic.genUpdateBorrowerDTO(textfieldPanel, scrollableTablePanel);
        }
        catch(NullPointerException npe)
        {
            // view.showMessage("BORROWER UPDATE failed!");

            return;
        }

        if(updateBorrowerViewHandler != null && readBorrowerViewHandler != null)
        {
            updateBorrowerViewHandler.invoke(requestDTOBorrowerUpdate);
        }
    }
}
