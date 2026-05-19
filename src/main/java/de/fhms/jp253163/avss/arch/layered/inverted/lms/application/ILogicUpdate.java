package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicResponseDTO;

public interface ILogicUpdate
{
    public ILogicRequestDTO[] request();
    public void response(ILogicResponseDTO response);
}
