package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerCreateActionListener implements ActionListener
{
    private IView view;
    private ICreateBorrowerViewHandler createBorrowerViewHandler;
    private BorrowerTextfieldPanel textfieldPanel;

    public BorrowerCreateActionListener(IView view, ICreateBorrowerViewHandler createBorrowerViewHandler, BorrowerTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.createBorrowerViewHandler = createBorrowerViewHandler;
        this.textfieldPanel = textfieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IRequestDTOBorrowerCreate requestDTOBorrowerCreate = null;

        try
        {
            requestDTOBorrowerCreate = BorrowerSharedLogic.genCreateLoanDTO(view, textfieldPanel);
        }
        catch(NullPointerException npe)
        {
            view.showMessage("Borrower creation failed!");

            return;
        }

        if(createBorrowerViewHandler != null)
            createBorrowerViewHandler.invoke(requestDTOBorrowerCreate);
    }
}