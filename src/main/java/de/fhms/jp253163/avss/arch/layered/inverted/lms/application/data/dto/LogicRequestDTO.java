package de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto;

public class LogicRequestDTO<T> implements ILogicRequestDTO
{
    private String filepath;
    private Object optionalObject;
    private int fileAction;
    private Class<T> objectType;

    public LogicRequestDTO(String filepath, Object optionalObject, int fileAction, Class<T> objectType)
    {
        this.filepath = filepath;
        this.optionalObject = optionalObject;
        this.fileAction = fileAction;
        this.objectType = objectType;
    }

    @Override
    public String getFilepath()
    {
        return filepath;
    }

    @Override
    public Object getOptionalObject() {
        return optionalObject;
    }

    @Override
    public int getFileAction() {
        return fileAction;
    }

    @Override
    public Class<T> getObjectType() {
        return objectType;
    }
}
