package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.page.c;

import de.fhms.jp253163.avss.arch.mvp.lms.view.component.LabelledTextfield;

import javax.swing.*;
import java.util.Objects;

public class BorrowerCreateAddressPanel extends JPanel
{
    private LabelledTextfield street, no, place, zip;

    public BorrowerCreateAddressPanel(LabelledTextfield street, LabelledTextfield no, LabelledTextfield place, LabelledTextfield zip)
    {
        Objects.requireNonNull(street);
        Objects.requireNonNull(no);
        Objects.requireNonNull(place);
        Objects.requireNonNull(zip);

        this.street = street;
        this.no = no;
        this.place = place;
        this.zip = zip;
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
