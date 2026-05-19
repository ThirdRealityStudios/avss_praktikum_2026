package de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data;

public interface ITerminalRequestDTO
{
    public long[] getLongs();
    public double[] getDoubles();
    public String[] getStrings();
    public int getCRUDAction();
}
