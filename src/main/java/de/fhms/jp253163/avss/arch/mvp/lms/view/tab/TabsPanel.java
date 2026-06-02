package de.fhms.jp253163.avss.arch.mvp.lms.view.tab;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.components.controls.end.ScrollableTablePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.BorrowerTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.BorrowerControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.center.BorrowerDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.borrower.controls.start.BorrowerCreateUpdatePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.LoanTabPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.LoanControlsPanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.center.LoanDeletePanel;
import de.fhms.jp253163.avss.arch.mvp.lms.view.tab.loan.controls.start.LoanCreateUpdatePanel;

import javax.swing.*;
import java.awt.*;

public class TabsPanel extends JTabbedPane
{
    private JTable borrowerTable, loanTable;
    private ScrollableTablePanel borrowerTablePanel, loanTablePanel;

    private BorrowerControlsPanel borrowerControlsPanel;
    private LoanControlsPanel loanControlsPanel;

    private BorrowerCreateUpdatePanel borrowerCreateUpdatePanel;
    private LoanCreateUpdatePanel loanCreateUpdatePanel;

    private BorrowerDeletePanel borrowerDeletePanel;
    private LoanDeletePanel loanDeletePanel;

    private BorrowerTabPanel borrowerTabPanel;
    private LoanTabPanel loanTabPanel;

    private IView view;

    private JTabbedPane tabbedPane;

    public TabsPanel(IView view)
    {
        this.view = view;

        initBorrowerTab();
        initLoanTab();

        addTab("Borrowers", borrowerTabPanel);
        addTab("Loans", loanTabPanel);
    }

    private void initBorrowerTab()
    {
        borrowerTable = new JTable();
        borrowerTablePanel = new ScrollableTablePanel(borrowerTable);

        borrowerCreateUpdatePanel = new BorrowerCreateUpdatePanel(view, borrowerTablePanel);
        borrowerDeletePanel = new BorrowerDeletePanel(view, this);

        borrowerControlsPanel = new BorrowerControlsPanel(borrowerCreateUpdatePanel, borrowerDeletePanel);
        borrowerControlsPanel.setPreferredSize(new Dimension(800, borrowerControlsPanel.getPreferredSize().height));

        borrowerTabPanel = new BorrowerTabPanel(borrowerTablePanel, borrowerControlsPanel);
    }

    private void initLoanTab()
    {
        loanTable = new JTable();
        loanTablePanel = new ScrollableTablePanel(loanTable);

        loanCreateUpdatePanel = new LoanCreateUpdatePanel(view, loanTable);
        loanDeletePanel = new LoanDeletePanel(view, loanTable);

        loanControlsPanel = new LoanControlsPanel(loanCreateUpdatePanel, loanDeletePanel);
        loanControlsPanel.setPreferredSize(new Dimension(800, loanControlsPanel.getPreferredSize().height));

        loanTabPanel = new LoanTabPanel(loanTablePanel, loanControlsPanel);
    }

    public BorrowerTabPanel getBorrowerTabPanel()
    {
        return borrowerTabPanel;
    }

    public LoanTabPanel getLoanTabPanel()
    {
        return loanTabPanel;
    }
}
