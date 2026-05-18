package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ILogicResponseDTO;

public interface ITerminalUpdate
{
    public ITerminalRequestDTO request();
    public void response(ILogicResponseDTO logicRespDTO);
}