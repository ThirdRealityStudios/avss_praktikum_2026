package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

public interface ITerminalRequestDTO
{
    public int[] getIntegers();
    public double[] getDoubles();
    public String[] getStrings();
    public int getCRUDAction();
}
