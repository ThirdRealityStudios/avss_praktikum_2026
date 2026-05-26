package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerSharedLogic;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerUpdateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerButtonPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerCreateUpdatePanel extends JPanel
{
    private IView view;

    private BorrowerSharedLogic sharedLogic;

    private BorrowerTextfieldPanel textfieldPanel;
    private BorrowerButtonPanel buttonPanel;

    public BorrowerCreateUpdatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IView view) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(updateBorrowerViewHandler);
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        textfieldPanel = createTextfieldPanel();
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());

        buttonPanel = createButtonPanel(createBorrowerViewHandler, updateBorrowerViewHandler);
        UIFactory.addComponentFor(this, buttonPanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());

        // Used for the creation and update event handlers only for borrower (because logic very similar)
        sharedLogic = new BorrowerSharedLogic(this);
    }

    private BorrowerTextfieldPanel createTextfieldPanel() throws NullPointerException
    {
        BorrowerNamePanel namePanel = new BorrowerNamePanel(new LabelledTextfield("Firstname:"), new LabelledTextfield("Lastname:"));
        BorrowerBirthdayPanel birthdayPanel = new BorrowerBirthdayPanel(new LabelledTextfield("Day of birth:"), new LabelledTextfield("Month of birth:"), new LabelledTextfield("Year of birth:"));
        BorrowerAddressPanel addressPanel = new BorrowerAddressPanel(new LabelledTextfield("Street:"), new LabelledTextfield("No:"), new LabelledTextfield("Place:"), new LabelledTextfield("Zip:"));

        return new BorrowerTextfieldPanel(namePanel, birthdayPanel, addressPanel);
    }

    private BorrowerButtonPanel createButtonPanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler) throws NullPointerException
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(updateBorrowerViewHandler);

        JButton buttonCreate = new JButton("Create");
        BorrowerCreateActionListener createActionListener = new BorrowerCreateActionListener(view, createBorrowerViewHandler, sharedLogic);
        buttonCreate.addActionListener(createActionListener);

        JButton buttonUpdate = new JButton("Update");
        BorrowerUpdateActionListener updateActionListener = new BorrowerUpdateActionListener(view, updateBorrowerViewHandler, sharedLogic);
        buttonUpdate.addActionListener(updateActionListener);

        return new BorrowerButtonPanel(view, createBorrowerViewHandler, updateBorrowerViewHandler, sharedLogic);
    }

    public IView getView()
    {
        return view;
    }

    public BorrowerTextfieldPanel getTextfieldPanel()
    {
        return textfieldPanel;
    }

    public BorrowerButtonPanel getButtonPanel()
    {
        return buttonPanel;
    }
}
