package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreateNamePanel extends JPanel
{
    private LabelledTextfield firstname, lastname;

    public BorrowerCreateNamePanel(LabelledTextfield firstname, LabelledTextfield lastname)
    {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastname);

        this.firstname = firstname;
        this.lastname = lastname;
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
