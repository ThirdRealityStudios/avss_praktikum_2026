package de.fhms.jp253163.avss.lms.frontend;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage);
    public void showLoanData(ILoan[] loans, String optionalMessage);
}
