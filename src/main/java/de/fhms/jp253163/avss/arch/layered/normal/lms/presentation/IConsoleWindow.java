package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalResponseDTO;

public interface IConsoleWindow
{
    public void showCommandInput();
    public void showHelp();
    public void showObjectData(Object[] objectData, String optionalMessage);
    public ITerminalResponseDTO getTerminalResponseDTO();
}
