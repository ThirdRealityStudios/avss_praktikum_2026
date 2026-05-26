package de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls;

import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.north.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.south.BorrowerUpdateDeletePanel;

import javax.swing.*;
import java.util.Objects;

public class BorrowerControlsPanel extends JPanel
{
    private BorrowerCreateUpdatePanel createPanel;
    private BorrowerUpdateDeletePanel updateDeletePanel;

    public BorrowerControlsPanel(BorrowerCreateUpdatePanel createPanel, BorrowerUpdateDeletePanel updateDeletePanel)
    {
        Objects.requireNonNull(createPanel);
        Objects.requireNonNull(updateDeletePanel);

        this.createPanel = createPanel;
        this.updateDeletePanel = updateDeletePanel;
    }
}
