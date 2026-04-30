package de.fh_muenster.jp253163.Darstellung;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage);
    public void showLoanData(ILoan[] loans, String optionalMessage);
}
