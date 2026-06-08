package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.center.action;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.dto.RequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.center.BorrowerDeletePanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class BorrowerDeleteActionListener implements ActionListener
{
    private IReadBorrowerViewHandler readBorrowerViewHandler;
    private IDeleteBorrowerViewHandler deleteBorrowerViewHandler;
    private BorrowerDeletePanel deletePanel;
    private TabsPanel tabsPanel;

    /**
     * @param readBorrowerViewHandler Or null is allowed too (do run nothing in this case)
     * @param deleteBorrowerViewHandler Or null is allowed too (do run nothing in this case)
     * @param deletePanel
     * @throws NullPointerException
     */
    public BorrowerDeleteActionListener(IReadBorrowerViewHandler readBorrowerViewHandler, IDeleteBorrowerViewHandler deleteBorrowerViewHandler, BorrowerDeletePanel deletePanel, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(deletePanel);
        Objects.requireNonNull(tabsPanel);

        this.readBorrowerViewHandler = readBorrowerViewHandler;
        this.deleteBorrowerViewHandler = deleteBorrowerViewHandler;
        this.deletePanel = deletePanel;
        this.tabsPanel = tabsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        BorrowerTabPanel tabPanel = null;
        ScrollableTablePanel scrollableTablePanel = null;
        JTable table = null;
        TableModel model = null;
        int[] selectedRowsIDs = new int[0];
        int selectedRowID = -1;

        Object borrowerIDObj = null;
        String borrowerIDText = null;
        int borrowerIDParsed = -1;

        tabPanel = tabsPanel.getBorrowerTabPanel();
        if(tabPanel != null) scrollableTablePanel = tabPanel.getDataTabPanel();
        if(scrollableTablePanel != null) table = scrollableTablePanel.getTable();
        if(table != null) model = table.getModel();

        if(table != null) selectedRowsIDs = table.getSelectedRows();
        if(selectedRowsIDs.length > 0) selectedRowID = selectedRowsIDs[0];

        if(model != null && selectedRowID > -1) borrowerIDObj = model.getValueAt(selectedRowID, 0);
        if(borrowerIDObj != null) borrowerIDText = borrowerIDObj.toString();
        if(borrowerIDText != null) try{borrowerIDParsed = Integer.parseInt(borrowerIDText);} catch(NumberFormatException exc){}

        IRequestDTOBorrowerDelete requestDTOBorrowerDelete = new RequestDTOBorrowerDelete(borrowerIDParsed);

        if(readBorrowerViewHandler != null && deleteBorrowerViewHandler != null)
        {
            deleteBorrowerViewHandler.invoke(requestDTOBorrowerDelete);
        }
    }
}
