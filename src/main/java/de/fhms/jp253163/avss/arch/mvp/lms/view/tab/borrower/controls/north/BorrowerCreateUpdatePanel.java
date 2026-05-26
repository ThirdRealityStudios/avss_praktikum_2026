package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.IUpdateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.action.BorrowerUpdateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerAddressPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerBirthdayPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.BorrowerNamePanel;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreateUpdatePanel extends JPanel
{
    private BorrowerNamePanel namePanel;
    private BorrowerAddressPanel addressPanel;
    private BorrowerBirthdayPanel birthdayPanel;

    private IView view;

    public BorrowerCreateUpdatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IUpdateBorrowerViewHandler updateBorrowerViewHandler, IView view)
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(view);

        this.view = view;

        namePanel = new BorrowerNamePanel(new LabelledTextfield("Firstname:"), new LabelledTextfield("Lastname:"));
        birthdayPanel = new BorrowerBirthdayPanel(new LabelledTextfield("Day of birth:"), new LabelledTextfield("Month of birth:"), new LabelledTextfield("Year of birth:"));
        addressPanel = new BorrowerAddressPanel(new LabelledTextfield("Street:"), new LabelledTextfield("No:"), new LabelledTextfield("Place:"), new LabelledTextfield("Zip:"));

        JButton buttonCreate = new JButton("Create");
        BorrowerCreateActionListener createActionListener = new BorrowerCreateActionListener(createBorrowerViewHandler, this);
        buttonCreate.addActionListener(createActionListener);

        JButton buttonUpdate = new JButton("Update");
        BorrowerUpdateActionListener updateActionListener = new BorrowerUpdateActionListener(updateBorrowerViewHandler, this);
        buttonUpdate.addActionListener(updateActionListener);

        // TODO this.setLayout();
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
