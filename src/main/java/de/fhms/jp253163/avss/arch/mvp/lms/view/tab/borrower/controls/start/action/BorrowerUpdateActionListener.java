package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private IView view;
    private ScrollableTablePanel tablePanel;

    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private BorrowerTextfieldPanel textfieldPanel;

    // view, createBorrowerViewHandler, sharedLogic
    public BorrowerUpdateActionListener(IView view, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tabsPanel);

        this.view = view;

        BorrowerTabPanel tabPanel = tabsPanel.getBorrowerTabPanel();
        Objects.requireNonNull(tabPanel);
        this.tablePanel = tabPanel.getDataTabPanel();
        Objects.requireNonNull(this.tablePanel);

        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
        this.readBorrowerViewHandler = readBorrowerViewHandler;

        BorrowerControlsPanel controlsPanel = tabPanel.getControlsPanel();
        Objects.requireNonNull(controlsPanel);

        BorrowerCreateUpdatePanel createUpdatePanel = controlsPanel.getCreateUpdatePanel();
        Objects.requireNonNull(createUpdatePanel);

        this.textfieldPanel = createUpdatePanel.getTextfieldPanel();
        Objects.requireNonNull(this.textfieldPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing BORROWER UPDATE button CLICKED!");

        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = BorrowerSharedLogic.genUpdateBorrowerDTO(textfieldPanel, tablePanel);
        }
        catch(NullPointerException npe)
        {
            // view.showMessage("BORROWER UPDATE failed!");

            return;
        }

        System.out.println("Created valid request for BORROWER UPDATE button CLICKED!");

        if(updateBorrowerViewHandler != null && readBorrowerViewHandler != null)
        {
            System.out.println("Invoking view handler for BORROWER UPDATE!");
            updateBorrowerViewHandler.invoke(requestDTOBorrowerUpdate);

            // Just show all borrowers (in the table) after creating a new dataset
            System.out.println("Invoking view handler for BORROWER READ!");
            readBorrowerViewHandler.invoke(new RequestDTOBorrowerRead(-1));
        }
    }
}
