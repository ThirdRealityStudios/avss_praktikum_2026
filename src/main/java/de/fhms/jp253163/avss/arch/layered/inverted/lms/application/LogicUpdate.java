package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicResponseDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.ITerminalUpdate;

public class LogicUpdate implements ILogicUpdate
{
    public LogicUpdate(ITerminalUpdate terminalUpdate)
    {

    }

    @Override
    public ILogicRequestDTO[] request()
    {
        return new ILogicRequestDTO[0];
    }

    @Override
    public void response(ILogicResponseDTO response)
    {

    }
}
