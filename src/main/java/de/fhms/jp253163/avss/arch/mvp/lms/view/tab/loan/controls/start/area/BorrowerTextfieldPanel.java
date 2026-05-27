package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerTextfieldPanel extends JPanel
{
    private BorrowerNamePanel namePanel;
    private BorrowerBirthdayPanel birthdayPanel;
    private BorrowerAddressPanel addressPanel;

    public BorrowerTextfieldPanel(BorrowerNamePanel namePanel, BorrowerBirthdayPanel birthdayPanel, BorrowerAddressPanel addressPanel) throws NullPointerException
    {
        Objects.requireNonNull(namePanel);
        Objects.requireNonNull(birthdayPanel);
        Objects.requireNonNull(addressPanel);

        this.namePanel = namePanel;
        this.birthdayPanel = birthdayPanel;
        this.addressPanel = addressPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, namePanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, birthdayPanel, 0, 1, GridBagConstraints.CENTER, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, addressPanel, 0, 2, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    public BorrowerNamePanel getNamePanel() {
        return namePanel;
    }

    public BorrowerBirthdayPanel getBirthdayPanel() {
        return birthdayPanel;
    }

    public BorrowerAddressPanel getAddressPanel() {
        return addressPanel;
    }
}
