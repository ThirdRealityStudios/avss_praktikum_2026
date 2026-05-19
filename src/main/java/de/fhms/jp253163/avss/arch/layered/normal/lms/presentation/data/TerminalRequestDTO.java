package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.data;

public class TerminalRequestDTO implements ITerminalRequestDTO
{
    private int[] integers;
    private double[] doubles;
    private String[] strings;
    private int crudAction = -1;

    /**
     * @param integers Can be null or contains the necessary integers.
     * @param doubles Can be null or contains the necessary doubles.
     * @param strings Can be null or contains the necessary strings.
     * @param crudAction 0: create borrower / loan, 1: read single borrower / loan, 2: read all existing borrowers / loans, 3: update a single borrower / loan, 4: delete a single borrower / loan
     */
    public TerminalRequestDTO(int[] integers, double[] doubles, String[] strings, int crudAction) throws IllegalArgumentException
    {
        setIntegers(integers);
        setDoubles(doubles);
        setStrings(strings);

        if(!setCRUDAction(crudAction))
            throw new IllegalArgumentException("the CRUD action should be between 0 and 4");
    }

    private void setIntegers(int[] integers)
    {
        this.integers = integers;
    }

    private void setDoubles(double[] doubles)
    {
        this.doubles = doubles;
    }

    private void setStrings(String[] strings)
    {
        this.strings = strings;
    }

    private boolean setCRUDAction(int crudAction)
    {
        if(crudAction < 0 || crudAction > 4)
            return false;

        this.crudAction = crudAction;

        return true;
    }

    @Override
    public int[] getIntegers()
    {
        return integers;
    }

    @Override
    public double[] getDoubles()
    {
        return doubles;
    }

    @Override
    public String[] getStrings()
    {
        return strings;
    }

    @Override
    public int getCRUDAction()
    {
        return crudAction;
    }
}
