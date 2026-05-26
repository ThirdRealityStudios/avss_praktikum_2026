package de.fhms.jp253163.avss.arch.mvp.lms.view.component;

import javax.swing.*;

/**
 * Mostly just a container to make code cleaner elsewhere.
 */
public class LabelledTextfield
{
    private JLabel jLabel;
    private JTextField jTextField;

    public LabelledTextfield(String label)
    {
        jLabel = new JLabel(label);
        JTextField textfield = new JTextField();
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
