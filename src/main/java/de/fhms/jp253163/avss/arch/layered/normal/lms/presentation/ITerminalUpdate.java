package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalResponseDTO;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data.ITerminalRequestDTO;

public interface ITerminalUpdate
{
    public ITerminalRequestDTO request();
    public void response(ITerminalResponseDTO termRespDTO);
}