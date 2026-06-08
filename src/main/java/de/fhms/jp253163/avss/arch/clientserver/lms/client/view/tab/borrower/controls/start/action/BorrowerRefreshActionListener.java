package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.start.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOBorrowerRead;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowerRefreshActionListener implements ActionListener
{
    private IReadBorrowerViewHandler readBorrowerViewHandler;

    public BorrowerRefreshActionListener(IReadBorrowerViewHandler readBorrowerViewHandler) throws NullPointerException
    {
        this.readBorrowerViewHandler = readBorrowerViewHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(readBorrowerViewHandler != null)
        {
            readBorrowerViewHandler.invoke(new RequestDTOBorrowerRead(-1));
        }
    }
}
