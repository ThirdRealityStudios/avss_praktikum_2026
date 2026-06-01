package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoanUpdateActionListener implements ActionListener
{
    private IView view;
    private IReadLoanViewHandler readLoanViewHandler;
    private IUpdateLoanViewHandler updateLoanViewHandler;
    private LoanTextfieldPanel textfieldPanel;
    private TabsPanel tabsPanel;

    public LoanUpdateActionListener(IView view, IUpdateLoanViewHandler updateLoanViewHandler, IReadLoanViewHandler readLoanViewHandler, LoanTextfieldPanel textfieldPanel, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(tabsPanel);

        Objects.requireNonNull(readLoanViewHandler);

        this.view = view;
        this.updateLoanViewHandler = updateLoanViewHandler;
        this.readLoanViewHandler = readLoanViewHandler;
        this.textfieldPanel = textfieldPanel;
        this.tabsPanel = tabsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Recognizing LOAN UPDATE button CLICKED!");

        IRequestDTOLoanUpdate requestDTOLoanUpdate = null;

        JButton button = null;

        try
        {
            if(e.getSource() instanceof JButton)
            {
                button = (JButton) e.getSource();
            }
            else
            {
                return;
            }

            boolean bookInstallemnt = button.getText().toLowerCase().contains("installment") || button.getText().equalsIgnoreCase("book installment");

            requestDTOLoanUpdate = LoanSharedLogic.genUpdateLoanDTO(textfieldPanel, bookInstallemnt, tabsPanel);
        }
        catch(NullPointerException npe)
        {
            // view.showMessage("LOAN UPDATE failed!");

            return;
        }

        System.out.println("Created valid request for LOAN UPDATE button CLICKED!");

        if(readLoanViewHandler != null && updateLoanViewHandler != null)
        {
            System.out.println("Invoking view handler for LOAN UPDATE!");
            updateLoanViewHandler.invoke(requestDTOLoanUpdate);

            System.out.println("Invoking view handler for LOAN READ!");
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        }
    }
}
