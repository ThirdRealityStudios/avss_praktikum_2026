package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.area.textfield;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerAddressPanel extends JPanel
{
    private LabelledTextfield street, no, place, zip;

    public BorrowerAddressPanel(LabelledTextfield street, LabelledTextfield no, LabelledTextfield place, LabelledTextfield zip) throws NullPointerException
    {
        Objects.requireNonNull(street);
        Objects.requireNonNull(no);
        Objects.requireNonNull(place);
        Objects.requireNonNull(zip);

        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, street, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, no, 0, 1, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, place, 0, 2, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, zip, 0, 3, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public LabelledTextfield getStreet()
    {
        return street;
    }

    public LabelledTextfield getNo()
    {
        return no;
    }

    public LabelledTextfield getPlace()
    {
        return place;
    }

    public LabelledTextfield getZip()
    {
        return zip;
    }
}
