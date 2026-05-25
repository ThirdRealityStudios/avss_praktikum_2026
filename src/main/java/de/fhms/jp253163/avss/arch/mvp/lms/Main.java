package de.fhms.jp253163.avss.arch.mvp.lms;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.IView;
import de.fhms.jp253163.avss.arch.mvp.lms.view.ConsoleView;

public class Main
{
    public static void main(String[] args)
    {
        IView view = new ConsoleView();
    }
}