package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalRequestDTO;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showObjectData(String[] objOutputs, String optionalMessage);
    public ITerminalRequestDTO getTerminalRequestDTO();
}
