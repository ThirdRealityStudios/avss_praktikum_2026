package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;

import javax.swing.*;
import java.util.Objects;

public class BorrowerBirthdayPanel extends JPanel
{
    private LabelledTextfield day, month, year;

    public BorrowerBirthdayPanel(LabelledTextfield day, LabelledTextfield month, LabelledTextfield year)
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
