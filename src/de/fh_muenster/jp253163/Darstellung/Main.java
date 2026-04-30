package de.fh_muenster.jp253163.Darstellung;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        Object o = (Object) new Main();

        System.out.println("Object type is: " + o.getClass());
    }
}