package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.loan.controls.start.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOLoanRead;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanRefreshActionListener implements ActionListener
{
    private IReadLoanViewHandler readLoanViewHandler;

    public LoanRefreshActionListener(IReadLoanViewHandler readLoanViewHandler) throws NullPointerException
    {
        this.readLoanViewHandler = readLoanViewHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(readLoanViewHandler != null)
        {
            readLoanViewHandler.invoke(new RequestDTOLoanRead(-1));
        }
    }
}
