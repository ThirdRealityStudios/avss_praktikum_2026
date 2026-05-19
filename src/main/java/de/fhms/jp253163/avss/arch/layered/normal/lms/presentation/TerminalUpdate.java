package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalResponseDTO;

public class TerminalUpdate implements ITerminalUpdate
{
    private IConsoleWindow consoleWindow = null;

    public TerminalUpdate(IConsoleWindow consoleWindow) throws NullPointerException
    {
        if(consoleWindow == null)
            throw new NullPointerException("consoleWindow cannot be null!");

        this.consoleWindow = consoleWindow;
    }

    @Override
    public ITerminalRequestDTO request()
    {
        consoleWindow.showCommandInput();

        return consoleWindow.getTerminalRequestDTO();
    }

    @Override
    public void response(ITerminalResponseDTO termRespDTO)
    {
        if(termRespDTO.getObjectData() != null)
        {
            consoleWindow.showObjectData(termRespDTO.getObjectData(), null);
        }
    }
}
