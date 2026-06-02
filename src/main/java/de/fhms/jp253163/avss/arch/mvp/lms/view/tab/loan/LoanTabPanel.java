package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.LoanControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.LoanCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.LoanConditionsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.LoanUpdateIDPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class LoanTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private LoanControlsPanel controlsPanel;

    public LoanTabPanel(ScrollableTablePanel scrollableTablePanel, LoanControlsPanel controlsPanel)
    {
        Objects.requireNonNull(scrollableTablePanel);
        Objects.requireNonNull(controlsPanel);

        this.scrollableTablePanel = scrollableTablePanel;
        this.controlsPanel = controlsPanel;

        initLayout();

        JTable table = scrollableTablePanel.getTable();

        if(table != null)
            table.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Only the borrower ID is updatable from a loan, so implement the copy logic here (from row to textfield)

                    int[] selectedRows = table.getSelectedRows();

                    if(selectedRows == null || selectedRows.length == 0)
                        return;

                    int selectedRowID = selectedRows[0]; // always select first row clicked on.

                    TableModel model = table.getModel();

                    if(model == null)
                        return;

                    // Now put all data into the user input fields, using that Loan ID.

                    Object borrowerIDObj = model.getValueAt(selectedRowID, 4);
                    String borrowerIDText = borrowerIDObj != null ? borrowerIDObj.toString() : "";

                    LoanCreateUpdatePanel createUpdatePanel = controlsPanel.getCreateUpdatePanel();
                    if(createUpdatePanel == null) return;

                    LoanTextfieldPanel textfieldPanel = createUpdatePanel.getTextfieldPanel();
                    if(textfieldPanel == null) return;

                    LoanUpdateIDPanel updateIdPanel = textfieldPanel.getUpdateIDPanel();
                    if(updateIdPanel == null) return;

                    LabelledTextfield borrowerIDField = updateIdPanel.getBorrowerID();
                    if(borrowerIDField == null) return;

                    borrowerIDField.getJTextField().setText(borrowerIDText);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
    }

    private void initLayout()
    {
        LayoutResultDTO dto = UIFactory.createGridBagLayoutFor(this);

        dto.getConstraints().fill = GridBagConstraints.HORIZONTAL;
        dto.getConstraints().weightx = 1;
        dto.getConstraints().weighty = 1;

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 0;
        dto.getConstraints().anchor = GridBagConstraints.FIRST_LINE_START; // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        add(scrollableTablePanel, dto.getConstraints());

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 0;
        dto.getConstraints().anchor = GridBagConstraints.LINE_START;
        add(controlsPanel, dto.getConstraints());
    }

    public ScrollableTablePanel getDataTabPanel()
    {
        return scrollableTablePanel;
    }

    public LoanControlsPanel getControlsPanel()
    {
        return controlsPanel;
    }
}
