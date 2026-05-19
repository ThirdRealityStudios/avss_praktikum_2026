package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalResponseDTO;

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
        else
        {
            if(termRespDTO.isSuccessful())
                consoleWindow.showObjectData(null, "Action was successful!");
            else
                consoleWindow.showObjectData(null, "Action failed!");
        }
    }
}
