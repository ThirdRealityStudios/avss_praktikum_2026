package de.fhms.jp253163.avss.arch.mvp.lms.view.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.borrower.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreatePanel extends JPanel
{
    public BorrowerCreatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler, IView view)
    {
        Objects.requireNonNull(createBorrowerViewHandler);

        JButton buttonCreate = new JButton("Create");

        LabelledTextfield[] labelledTextfields = new LabelledTextfield[]
        {
            new LabelledTextfield("Firstname:"),
            new LabelledTextfield("Lastname:"),
            new LabelledTextfield("Day of birth:"),
            new LabelledTextfield("Month of birth:"),
            new LabelledTextfield("Year of birth:"),
            new LabelledTextfield("Street:"),
            new LabelledTextfield("No:"),
            new LabelledTextfield("Place:"),
            new LabelledTextfield("Zip:")
        };

        BorrowerCreateActionListener actionListener = new BorrowerCreateActionListener(createBorrowerViewHandler, labelledTextfields, view);

        buttonCreate.addActionListener(actionListener);
    }
}
