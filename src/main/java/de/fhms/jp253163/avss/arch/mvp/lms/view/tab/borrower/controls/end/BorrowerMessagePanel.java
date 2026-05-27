package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.end;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.mvp.lms.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BorrowerMessagePanel extends JPanel
{
    private IView view;
    private JLabel messageLabel;

    public BorrowerMessagePanel(IView view)
    {
        Objects.requireNonNull(view);

        this.view = view;

        LayoutResultDTO layoutDTO = UIFactory.createGridBagLayoutFor(this);

        messageLabel = new JLabel();

        UIFactory.addComponentFor(this, messageLabel, 0, 0, GridBagConstraints.CENTER, layoutDTO.getConstraints());
    }

    public IView getView()
    {
        return this.view;
    }

    public JLabel getMessageLabel()
    {
        return messageLabel;
    }
}
