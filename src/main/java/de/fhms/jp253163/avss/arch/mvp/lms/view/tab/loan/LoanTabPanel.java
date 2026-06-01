package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan;

import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerNamePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.LoanControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.LoanCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.LoanTextfieldPanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class LoanTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private LoanControlsPanel controlsTabPanel;

    public LoanTabPanel(ScrollableTablePanel scrollableTablePanel, LoanControlsPanel controlsTabPanel)
    {
        Objects.requireNonNull(scrollableTablePanel);
        Objects.requireNonNull(controlsTabPanel);

        this.scrollableTablePanel = scrollableTablePanel;
        this.controlsTabPanel = controlsTabPanel;

        initLayout();

        JTable table = scrollableTablePanel.getTable();

        if(table != null)
            table.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    int[] selectedRows = table.getSelectedRows();

                    if(selectedRows == null || selectedRows.length == 0)
                        return;

                    int selectedRowID = selectedRows[0]; // always select first row clicked on.

                    TableModel model = table.getModel();

                    if(model == null)
                        return;

                    // Now put all data into the user input fields, using that Borrower ID.

                    Object borrowerIDObj = model.getValueAt(selectedRowID, 4);

                    String borrowerIDText = borrowerIDObj != null ? borrowerIDObj.toString() : "";

                    LoanCreateUpdatePanel createUpdatePanel = controlsTabPanel.getCreateUpdatePanel();
                    if(createUpdatePanel == null) return;

                    LoanTextfieldPanel textfieldPanel = createUpdatePanel.getTextfieldPanel();
                    if(textfieldPanel == null) return;

                    LabelledTextfield borrowerIDField = textfieldPanel.getBorrowerID();
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
        GridBagLayout layout = new GridBagLayout();

        GridBagConstraints constraints = new GridBagConstraints();
        layout.setConstraints(this, constraints);
        setLayout(layout);

        Insets insets = new Insets(0, 30, 30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = insets;
        add(scrollableTablePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(controlsTabPanel, constraints);
    }

    public ScrollableTablePanel getDataTabPanel()
    {
        return scrollableTablePanel;
    }

    public LoanControlsPanel getControlsPanel()
    {
        return controlsTabPanel;
    }
}
