package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreatePanel extends JPanel
{
    private BorrowerCreateNamePanel createNamePanel;
    private BorrowerCreateAddressPanel createAddressPanel;
    private BorrowerCreateBirthdayPanel createBirthdayPanel;

    private IView view;

    public BorrowerCreatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IView view)
    {
        Objects.requireNonNull(createBorrowerViewHandler);
        Objects.requireNonNull(view);

        this.view = view;

        JButton buttonCreate = new JButton("Create");

        createNamePanel = new BorrowerCreateNamePanel(new LabelledTextfield("Firstname:"), new LabelledTextfield("Lastname:"));
        createBirthdayPanel = new BorrowerCreateBirthdayPanel(new LabelledTextfield("Day of birth:"), new LabelledTextfield("Month of birth:"), new LabelledTextfield("Year of birth:"));
        createAddressPanel = new BorrowerCreateAddressPanel(new LabelledTextfield("Street:"), new LabelledTextfield("No:"), new LabelledTextfield("Place:"), new LabelledTextfield("Zip:"));

        BorrowerCreateActionListener actionListener = new BorrowerCreateActionListener(createBorrowerViewHandler, this);

        buttonCreate.addActionListener(actionListener);

        // TODO this.setLayout();
    }

    public BorrowerCreateNamePanel getCreateNamePanel()
    {
        return createNamePanel;
    }

    public BorrowerCreateBirthdayPanel getCreateBirthdayPanel()
    {
        return createBirthdayPanel;
    }

    public BorrowerCreateAddressPanel getCreateAddressPanel()
    {
        return createAddressPanel;
    }

    public IView getView()
    {
        return view;
    }
}
