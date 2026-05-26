package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area;

import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerButtonPanel extends JPanel
{
    JButton createButton, updateButton;

    public BorrowerButtonPanel(JButton createButton, JButton updateButton) throws NullPointerException
    {
        Objects.requireNonNull(createButton);
        Objects.requireNonNull(updateButton);

        this.createButton = createButton;
        this.updateButton = updateButton;

        LayoutResultDTO layoutResult = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, createButton, 0, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
        UIFactory.addComponentFor(this, updateButton, 1, 0, GridBagConstraints.PAGE_START, layoutResult.getConstraints());
    }

    public JButton getCreateButton()
    {
        return createButton;
    }

    public JButton getUpdateButton()
    {
        return updateButton;
    }
}
