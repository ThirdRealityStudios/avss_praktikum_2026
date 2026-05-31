package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.TabsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.action.BorrowerSharedLogic;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerTextfieldPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.BorrowerButtonPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield.BorrowerNamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerCreateUpdatePanel extends JPanel
{
    private IView view;
    private TabsPanel tabsPanel;

    private BorrowerTextfieldPanel textfieldPanel;
    private BorrowerButtonPanel buttonPanel;

    public BorrowerCreateUpdatePanel(IView view, TabsPanel tabsPanel) throws NullPointerException
    {
        Objects.requireNonNull(view);
        Objects.requireNonNull(tabsPanel);

        this.view = view;
        this.tabsPanel = tabsPanel;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        textfieldPanel = createTextfieldPanel();
        UIFactory.addComponentFor(this, textfieldPanel, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());

        buttonPanel = new BorrowerButtonPanel(view, null, null, null, tabsPanel);
        UIFactory.addComponentFor(this, buttonPanel, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    private BorrowerTextfieldPanel createTextfieldPanel() throws NullPointerException
    {
        BorrowerNamePanel namePanel = new BorrowerNamePanel(new LabelledTextfield("Firstname:"), new LabelledTextfield("Lastname:"));
        BorrowerBirthdayPanel birthdayPanel = new BorrowerBirthdayPanel(new LabelledTextfield("Day of birth:"), new LabelledTextfield("Month of birth:"), new LabelledTextfield("Year of birth:"));
        BorrowerAddressPanel addressPanel = new BorrowerAddressPanel(new LabelledTextfield("Street:"), new LabelledTextfield("No:"), new LabelledTextfield("Place:"), new LabelledTextfield("Zip:"));

        return new BorrowerTextfieldPanel(namePanel, birthdayPanel, addressPanel);
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

    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler createBorrowerViewHandler, IReadBorrowerViewHandler readBorrowerViewHandler)
    {
        buttonPanel.setCreateBorrowerViewHandler(createBorrowerViewHandler, readBorrowerViewHandler);
    }

    public void setUpdateBorrowerViewHandler(IReadBorrowerViewHandler readBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler)
    {
        buttonPanel.setUpdateBorrowerViewHandler(readBorrowerViewHandler, updateBorrowerViewHandler);
    }
}
