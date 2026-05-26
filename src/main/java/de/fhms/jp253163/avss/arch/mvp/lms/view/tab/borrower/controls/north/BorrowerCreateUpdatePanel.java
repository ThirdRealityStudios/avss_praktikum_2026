package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerUpdateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerButtonPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreateUpdatePanel extends JPanel
{
    private BorrowerNamePanel namePanel;
    private BorrowerAddressPanel addressPanel;
    private BorrowerBirthdayPanel birthdayPanel;

    private IView view;

    public BorrowerCreateUpdatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IView view) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        BorrowerTextfieldPanel textfieldPanel = createTextfieldPanel();
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, layoutResult.getConstraints());

        BorrowerButtonPanel buttonPanel = createButtonPanel(createBorrowerViewHandler, updateBorrowerViewHandler);
        UIFactory.addComponentFor(this, buttonPanel, 0, 1, layoutResult.getConstraints());
    }

    private BorrowerTextfieldPanel createTextfieldPanel() throws NullPointerException
    {
        namePanel = new BorrowerNamePanel(new LabelledTextfield("Firstname:"), new LabelledTextfield("Lastname:"));
        birthdayPanel = new BorrowerBirthdayPanel(new LabelledTextfield("Day of birth:"), new LabelledTextfield("Month of birth:"), new LabelledTextfield("Year of birth:"));
        addressPanel = new BorrowerAddressPanel(new LabelledTextfield("Street:"), new LabelledTextfield("No:"), new LabelledTextfield("Place:"), new LabelledTextfield("Zip:"));

        return new BorrowerTextfieldPanel(namePanel, birthdayPanel, addressPanel);
    }

    private BorrowerButtonPanel createButtonPanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(updateBorrowerViewHandler);

        JButton buttonCreate = new JButton("Create");
        BorrowerCreateActionListener createActionListener = new BorrowerCreateActionListener(createBorrowerViewHandler, this);
        buttonCreate.addActionListener(createActionListener);

        JButton buttonUpdate = new JButton("Update");
        BorrowerUpdateActionListener updateActionListener = new BorrowerUpdateActionListener(updateBorrowerViewHandler, this);
        buttonUpdate.addActionListener(updateActionListener);

        return new BorrowerButtonPanel(buttonCreate, buttonUpdate);
    }

    public BorrowerNamePanel getNamePanel()
    {
        return namePanel;
    }

    public BorrowerBirthdayPanel getBirthdayPanel()
    {
        return birthdayPanel;
    }

    public BorrowerAddressPanel getAddressPanel()
    {
        return addressPanel;
    }

    public IView getView()
    {
        return view;
    }
}
