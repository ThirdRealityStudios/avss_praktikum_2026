package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data;

public class LogicResponseDTO implements ILogicResponseDTO
{
    private String[] objectOutputData = null;
    private boolean isSuccessful = false;

    /**
     * @param objectOutputData Object output data (never null but can be an empty array!)
     * @param isSuccessful Was the desired action successful? (especially CRUD)
     * @throws NullPointerException
     */
    public LogicResponseDTO(String[] objectOutputData, boolean isSuccessful) throws NullPointerException
    {
        if(!setObjectData(objectOutputData))
        {
            throw new NullPointerException("objectOutputData must not be null! (in constructor)");
        }

        setSuccessful(isSuccessful);
    }

    private boolean setObjectData(String[] objectData)
    {
        if(objectData == null)
        {
            return false;
        }

        this.objectOutputData = objectData;

        return true;
    }

    private void setSuccessful(boolean successful)
    {
        isSuccessful = successful;
    }

    @Override
    public String[] getObjectData()
    {
        return new String[0];
    }

    @Override
    public boolean isSuccessful()
    {
        return false;
    }
}
