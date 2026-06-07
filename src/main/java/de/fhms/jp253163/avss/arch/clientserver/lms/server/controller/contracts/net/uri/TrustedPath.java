package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import java.util.ArrayList;

public class TrustedPath
{
    public static final ArrayList<TrustedDirectory> directories;

    static
    {
        directories = new ArrayList<>();

        TrustedDirectory borrower = new TrustedDirectory("borrower");
        borrower.getNext().add(new TrustedDirectory("create"));
        borrower.getNext().add(new TrustedDirectory("read"));
        borrower.getNext().add(new TrustedDirectory("update"));
        borrower.getNext().add(new TrustedDirectory("delete"));
        directories.add(borrower);

        TrustedDirectory loan = new TrustedDirectory("loan");
        loan.getNext().add(new TrustedDirectory("create"));
        loan.getNext().add(new TrustedDirectory("read"));
        loan.getNext().add(new TrustedDirectory("update"));
        loan.getNext().add(new TrustedDirectory("delete"));
        directories.add(loan);
    }
}
