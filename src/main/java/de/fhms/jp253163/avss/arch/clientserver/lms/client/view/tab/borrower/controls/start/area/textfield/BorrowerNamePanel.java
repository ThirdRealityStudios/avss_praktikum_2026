package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.tab.borrower.controls.start.area.textfield;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerNamePanel extends JPanel
{
    private LabelledTextfield firstname, lastname;

    public BorrowerNamePanel(LabelledTextfield firstname, LabelledTextfield lastname) throws NullPointerException
    {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastname);

        this.firstname = firstname;
        this.lastname = lastname;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, firstname, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, lastname, 0, 1, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
    }

    public LabelledTextfield getFirstname()
    {
        return firstname;
    }

    public LabelledTextfield getLastname()
    {
        return lastname;
    }
}
