package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerUpdateActionListener implements ActionListener
{
    private IView view;
    private IUpdateBorrowerViewHandler updateBorrowerViewHandler;
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private BorrowerTextfieldPanel textfieldPanel;

    // view, createBorrowerViewHandler, sharedLogic
    public BorrowerUpdateActionListener(IView view, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.updateBorrowerViewHandler = updateBorrowerViewHandler;
        this.readBorrowerViewHandler = readBorrowerViewHandler;
        this.textfieldPanel = textfieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing BORROWER UPDATE button CLICKED!");

        IRequestDTOBorrowerUpdate requestDTOBorrowerUpdate = null;

        try
        {
            requestDTOBorrowerUpdate = BorrowerSharedLogic.genUpdateBorrowerDTO(view, textfieldPanel);
        }
        catch(NullPointerException npe)
        {
            view.showMessage("BORROWER UPDATE failed!");

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
