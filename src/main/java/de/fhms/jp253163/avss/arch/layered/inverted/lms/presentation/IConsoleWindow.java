package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showObjectData(Object[] objectData, String optionalMessage);
    public ITerminalRequestDTO getTerminalRequestDTO();
}
