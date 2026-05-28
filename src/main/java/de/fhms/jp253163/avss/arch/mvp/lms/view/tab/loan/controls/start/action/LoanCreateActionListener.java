package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanCreateActionListener implements ActionListener
{
    private IView view;
    private ICreateLoanViewHandler createViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;
    private LoanTextfieldPanel textfieldPanel;

    public LoanCreateActionListener(IView view, ICreateLoanViewHandler createViewHandler, IReadLoanViewHandler readLoanViewHandler, LoanTextfieldPanel textfieldPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);

        this.view = view;
        this.createViewHandler = createViewHandler;
        this.readLoanViewHandler = readLoanViewHandler;
        this.textfieldPanel = textfieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IRequestDTOLoanCreate requestDTOLoanCreate = null;

        try
        {
            requestDTOLoanCreate = LoanSharedLogic.genCreateLoanDTO(textfieldPanel);
        }
        catch(NullPointerException npe)
        {
            view.showMessage("Loan creation failed! (code: 0)");

            return;
        }

        if(createViewHandler != null && readLoanViewHandler != null)
        {
            createViewHandler.invoke(requestDTOLoanCreate);

            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        }
    }
}