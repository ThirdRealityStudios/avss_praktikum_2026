package de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto;

public class LogicResponseDTO implements ILogicResponseDTO
{
    private Object[] objectData;
    private boolean successful;

    public LogicResponseDTO(Object[] objectData, boolean isSuccessful)
    {
        this.objectData = objectData;
        this.successful = isSuccessful;
    }

    @Override
    public Object[] getObjectData()
    {
        return new Object[0];
    }

    @Override
    public boolean isSuccessful()
    {
        return false;
    }
}
