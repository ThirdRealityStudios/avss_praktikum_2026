package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area.textfield;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerBirthdayPanel extends JPanel
{
    private LabelledTextfield day, month, year;

    public BorrowerBirthdayPanel(LabelledTextfield day, LabelledTextfield month, LabelledTextfield year) throws NullPointerException
    {
        Objects.requireNonNull(day);
        Objects.requireNonNull(month);
        Objects.requireNonNull(year);

        this.day = day;
        this.month = month;
        this.year = year;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, day, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, month, 0, 1, GridBagConstraints.CENTER, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, year, 0, 2, GridBagConstraints.PAGE_END, layoutResult.getConstraints());
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
