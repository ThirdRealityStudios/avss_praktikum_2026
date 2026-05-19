package de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto;

public interface ILogicRequestDTO
{
    public String getFilepath();
    public Object getOptionalObject();
    public int getFileAction();
    public <T> Class<T> getObjectType();
}