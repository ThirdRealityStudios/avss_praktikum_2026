package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import java.net.URI;
import java.util.Objects;

public class ParsedURI
{
    private Service service;
    private String path;

    public ParsedURI(URI uri)
    {
        Objects.requireNonNull(uri, "URI must not be null!");
        Objects.requireNonNull(uri.getPath(), "URI path must not be null!");

        this.path = uri.getPath();

        this.service = new Service(uri);
    }

    public Service getService()
    {
        return this.service;
    }

    public String getPath()
    {
        return this.path;
    }
}
