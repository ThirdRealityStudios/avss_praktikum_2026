package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data;

public class TerminalResponseDTO implements ITerminalResponseDTO
{
    private Object[] objectOutputData = null;
    private boolean isSuccessful = false;

    /**
     * @param objectOutputData Object output data (never null but can be an empty array!)
     * @param isSuccessful Was the desired action successful? (especially CRUD)
     * @throws NullPointerException
     */
    public TerminalResponseDTO(Object[] objectOutputData, boolean isSuccessful) throws NullPointerException
    {
        if(!setObjectData(objectOutputData))
        {
            throw new NullPointerException("objectOutputData must not be null! (in constructor)");
        }

        setSuccessful(isSuccessful);
    }

    private boolean setObjectData(Object[] objectData)
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
    public Object[] getObjectData()
    {
        return objectOutputData;
    }

    @Override
    public boolean isSuccessful()
    {
        return isSuccessful;
    }
}
