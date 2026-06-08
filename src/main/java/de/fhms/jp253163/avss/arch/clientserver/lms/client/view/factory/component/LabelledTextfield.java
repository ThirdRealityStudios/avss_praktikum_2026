package de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.component;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.LayoutResultDTO;
import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Mostly just a container to make code cleaner elsewhere.
 */
public class LabelledTextfield extends JPanel
{
    private JLabel jLabel;
    private JTextField jTextField;

    public LabelledTextfield(String label) throws NullPointerException
    {
        Objects.requireNonNull(label);

        this.jLabel = new JLabel(label);

        this.jTextField = new JTextField();
        jLabel.setLabelFor(jTextField);

        LayoutResultDTO layoutResultDTO = UIFactory.createGridBagLayoutFor(this);

        UIFactory.addComponentFor(this, jLabel, 0, 0, GridBagConstraints.LINE_START, layoutResultDTO.getConstraints());
        UIFactory.addComponentFor(this, jTextField, 1, 0, GridBagConstraints.LINE_END, layoutResultDTO.getConstraints());
    }

    public JLabel getJLabel()
    {
        return jLabel;
    }

    public JTextField getJTextField()
    {
        return jTextField;
    }
}
