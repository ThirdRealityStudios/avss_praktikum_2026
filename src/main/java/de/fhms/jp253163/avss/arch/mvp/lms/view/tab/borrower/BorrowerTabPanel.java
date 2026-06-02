package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class BorrowerTabPanel extends JPanel
{
    private ScrollableTablePanel scrollableTablePanel;
    private BorrowerControlsPanel controlsPanel;

    public BorrowerTabPanel(ScrollableTablePanel scrollableTablePanel, BorrowerControlsPanel controlsPanel)
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
                    int[] selectedRows = table.getSelectedRows();

                    if(selectedRows == null || selectedRows.length == 0)
                        return;

                    int selectedRowID = selectedRows[0]; // always select first row clicked on.

                    TableModel model = table.getModel();

                    if(model == null)
                        return;

                    // Now put all data into the user input fields, using that Borrower ID.

                    Object firstnameObj = model.getValueAt(selectedRowID, 1),
                            lastnameObj = model.getValueAt(selectedRowID, 2),
                            dayObj = model.getValueAt(selectedRowID, 3),
                            monthObj = model.getValueAt(selectedRowID, 4),
                            yearObj = model.getValueAt(selectedRowID, 5),
                            streetObj = model.getValueAt(selectedRowID, 6),
                            noObj = model.getValueAt(selectedRowID, 7),
                            placeObj = model.getValueAt(selectedRowID, 8),
                            zipObj = model.getValueAt(selectedRowID, 9);

                    String firstname = firstnameObj != null ? firstnameObj.toString() : "",
                            lastname = lastnameObj != null ? lastnameObj.toString() : "",
                            day = dayObj != null ? dayObj.toString() : "",
                            month = monthObj != null ? monthObj.toString() : "",
                            year = yearObj != null ? yearObj.toString() : "",
                            street = streetObj != null ? streetObj.toString() : "",
                            no = noObj != null ? noObj.toString() : "",
                            place = placeObj != null ? placeObj.toString() : "",
                            zip = zipObj != null ? zipObj.toString() : "";

                    BorrowerCreateUpdatePanel createUpdatePanel = controlsPanel.getCreateUpdatePanel();
                    if(createUpdatePanel == null) return;

                    BorrowerTextfieldPanel textfieldPanel = createUpdatePanel.getTextfieldPanel();
                    if(textfieldPanel == null) return;

                    BorrowerNamePanel namePanel = textfieldPanel.getNamePanel();
                    if(namePanel == null) return;

                    LabelledTextfield firstnameField = namePanel.getFirstname();
                    LabelledTextfield lastnameField = namePanel.getLastname();
                    if(firstnameField == null || lastnameField == null) return;

                    firstnameField.getJTextField().setText(firstname);
                    lastnameField.getJTextField().setText(lastname);

                    BorrowerBirthdayPanel birthdayPanel = textfieldPanel.getBirthdayPanel();
                    if(birthdayPanel == null) return;

                    LabelledTextfield dayField = birthdayPanel.getDay();
                    LabelledTextfield monthField = birthdayPanel.getMonth();
                    LabelledTextfield yearField = birthdayPanel.getYear();

                    dayField.getJTextField().setText(day);
                    monthField.getJTextField().setText(month);
                    yearField.getJTextField().setText(year);

                    BorrowerAddressPanel addressPanel = textfieldPanel.getAddressPanel();
                    if(addressPanel == null) return;

                    LabelledTextfield streetField = addressPanel.getStreet();
                    LabelledTextfield noField = addressPanel.getNo();
                    LabelledTextfield placeField = addressPanel.getPlace();
                    LabelledTextfield zipField = addressPanel.getZip();

                    streetField.getJTextField().setText(street);
                    noField.getJTextField().setText(no);
                    placeField.getJTextField().setText(place);
                    zipField.getJTextField().setText(zip);
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
        dto.getConstraints().anchor = GridBagConstraints.LINE_START;

        Insets insets = new Insets(30, 30, 30, 30);

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 0;
        dto.getConstraints().insets = insets;
        add(scrollableTablePanel, dto.getConstraints());

        dto.getConstraints().gridx = 0;
        dto.getConstraints().gridy = 1;
        dto.getConstraints().insets = new Insets(0, 30, 30, 30);
        add(controlsPanel, dto.getConstraints());
    }

    public ScrollableTablePanel getDataTabPanel()
    {
        return scrollableTablePanel;
    }

    public BorrowerControlsPanel getControlsPanel()
    {
        return controlsPanel;
    }
}
