package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.area;

import javax.swing.*;
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
