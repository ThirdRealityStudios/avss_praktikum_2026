package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage);
    public void showLoanData(ILoan[] loans, String optionalMessage);
}
