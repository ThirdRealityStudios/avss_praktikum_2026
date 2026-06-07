package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import java.util.ArrayList;
import java.util.Objects;

public class TrustedDirectory
{
    private ArrayList<TrustedDirectory> next;
    private String value;

    public TrustedDirectory(String value)
    {
        Objects.requireNonNull(value);

        this.next = new ArrayList<>();
    }

    public ArrayList<TrustedDirectory> getNext()
    {
        return this.next;
    }

    public String getValue()
    {
        return this.value;
    }
}
