package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.normal.lms.application.data.ILoan;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showObjectData(String[] objOutputs, String optionalMessage);
}
