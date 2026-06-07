package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import java.net.URI;
import java.util.Objects;

public class ParsedURI
{
    private Service service;

    public ParsedURI(URI uri)
    {
        Objects.requireNonNull(uri);

        this.service = new Service(uri);
    }

    public Service getService()
    {
        return service;
    }
}
