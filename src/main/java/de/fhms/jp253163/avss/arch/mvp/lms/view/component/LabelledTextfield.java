package de.fhms.jp253163.avss.arch.mvp.lms.view.component;

import javax.swing.*;
import java.util.Objects;

/**
 * Mostly just a container to make code cleaner elsewhere.
 */
public class LabelledTextfield
{
    private JLabel jLabel;
    private JTextField jTextField;

    public LabelledTextfield(String label) throws NullPointerException
    {
        Objects.requireNonNull(label);

        jLabel = new JLabel(label);

        JTextField textfield = new JTextField();
        this.jTextField = textfield;
        jLabel.setLabelFor(textfield);
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
