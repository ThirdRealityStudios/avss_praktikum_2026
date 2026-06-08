package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.IView;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOLoanUpdate;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IUpdateLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * The LoanUpdateActionListener will know on its own if the event source is a normal update button (to change existing data normally)
 * or if you want to book an installment (then consider to give the button text the name "book installment").
 * Otherwise, it won't detect it!
 */
public class LoanUpdateActionListener implements ActionListener
{
    private IView view;
    private IUpdateLoanViewHandler updateLoanViewHandler;
    private IReadLoanViewHandler readLoanViewHandler;

    private LoanTextfieldPanel textfieldPanel;
    private JTable tableLoan;

    public LoanUpdateActionListener(IView view, IUpdateLoanViewHandler updateLoanViewHandler, IReadLoanViewHandler readLoanViewHandler, LoanTextfieldPanel textfieldPanel, JTable tableLoan) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(textfieldPanel);
        Objects.requireNonNull(tableLoan);

        this.view = view;
        this.updateLoanViewHandler = updateLoanViewHandler;
        this.readLoanViewHandler = readLoanViewHandler;
        this.textfieldPanel = textfieldPanel;
        this.tableLoan = tableLoan;
    }

    /**
     * @param e
     * @return The button from that or null (should actually not happen because the LoanUpdateActionListener is only used on buttons)
     */
    private static JButton getButtonInstance(ActionEvent e)
    {
        Object buttonInstance = e.getSource();

        JButton button = null;

        if(buttonInstance instanceof JButton)
        {
            button = (JButton) buttonInstance;
        }

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton buttonClicked = getButtonInstance(e);
        boolean bookInstallment = false;

        if(buttonClicked != null)
        {
            bookInstallment = buttonClicked.getText().toLowerCase().contains("book installment");
        }

        IRequestDTOLoanUpdate requestDTOLoanUpdate = null;

        try
        {
            requestDTOLoanUpdate = LoanSharedLogic.genUpdateLoanDTO(textfieldPanel, bookInstallment, tableLoan);
        }
        catch(NullPointerException npe)
        {
            return;
        }

        if(updateLoanViewHandler != null && readLoanViewHandler != null)
        {
            updateLoanViewHandler.invoke(requestDTOLoanUpdate);
        }
    }
}
