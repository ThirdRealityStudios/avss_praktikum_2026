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

        this.value = value;
        this.next = new ArrayList<>();
    }

    /**
     * @return Never null. But can be an empty or filled array list.
     */
    public ArrayList<TrustedDirectory> getNext()
    {
        return this.next;
    }

    /**
     * @return Never null. Always a string. But could be empty technically.
     */
    public String getValue()
    {
        return this.value;
    }
}
