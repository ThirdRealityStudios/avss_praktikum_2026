package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreateBirthdayPanel extends JPanel
{
    private LabelledTextfield day, month, year;

    public BorrowerCreateBirthdayPanel(LabelledTextfield day, LabelledTextfield month, LabelledTextfield year)
    {
        Objects.requireNonNull(day);
        Objects.requireNonNull(month);
        Objects.requireNonNull(year);

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public LabelledTextfield getDay()
    {
        return day;
    }

    public LabelledTextfield getMonth()
    {
        return month;
    }

    public LabelledTextfield getYear()
    {
        return year;
    }
}
