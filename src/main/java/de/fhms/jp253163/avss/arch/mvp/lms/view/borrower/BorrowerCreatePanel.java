package de.fhms.jp253163.avss.arch.mvp.lms.view.borrower;

import de.fhms.jp253163.avss.arch.mvp.lms.view.borrower.action.BorrowerCreateActionListener;
import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.ICreateBorrowerViewHandler;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreatePanel extends JPanel
{
    public BorrowerCreatePanel(ICreateBorrowerViewHandler createBorrowerViewHandler)
    {
        Objects.requireNonNull(createBorrowerViewHandler);

        JButton buttonCreate = new JButton("Create");

        LabelledTextfield firstname = new LabelledTextfield("Firstname:"),
                        lastname = new LabelledTextfield("Lastname:"),
                        day = new LabelledTextfield("Day of birth:"),
                        month = new LabelledTextfield("Month of birth:"),
                        year = new LabelledTextfield("Year of birth:"),
                        street = new LabelledTextfield("Street:"),
                        no = new LabelledTextfield("No:"),
                        place = new LabelledTextfield("Place:"),
                        zip = new LabelledTextfield("Zip:");

        BorrowerCreateActionListener actionListener = new BorrowerCreateActionListener(createBorrowerViewHandler);

        buttonCreate.addActionListener(actionListener);
    }
}
