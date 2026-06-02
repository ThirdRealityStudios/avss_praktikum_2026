package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanCreateActionListener implements ActionListener
{
    private IView view;
    private ICreateLoanViewHandler createLoanViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;
    private LoanTextfieldPanel textfieldPanel;

    public LoanCreateActionListener(IView view, ICreateLoanViewHandler createLoanViewHandler, IReadLoanViewHandler readLoanViewHandler, LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.createLoanViewHandler = createLoanViewHandler;
        this.readLoanViewHandler = readLoanViewHandler;
        this.textfieldPanel = textfieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Recognizing LOAN CREATION button CLICKED!");

        IRequestDTOLoanCreate requestDTOLoanCreate = null;

        try
        {
            requestDTOLoanCreate = LoanSharedLogic.genCreateLoanDTO(textfieldPanel);
        }
        catch(NullPointerException npe)
        {
            // view.showMessage("LOAN CREATION failed!");

            return;
        }

        System.out.println("Created valid request for LOAN CREATION button CLICKED!");

        if(createLoanViewHandler != null && readLoanViewHandler != null)
        {
            System.out.println("Invoking view handler for LOAN CREATION!");
            createLoanViewHandler.invoke(requestDTOLoanCreate);
        }
    }
}