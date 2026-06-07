package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.Objects;

public class Service
{
    private String path;
    private String serviceName;

    private Action action;

    public Service(URI uri)
    {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(uri.getPath());

        this.path = uri.getPath();
        this.action = new Action(uri);
    }

    public String getName()
    {
        if(serviceName != null)
            return serviceName;

        if(path.startsWith("/"))
        {
            if(StringUtils.countMatches(path, "/") > 1)
                return this.serviceName = StringUtils.substringBetween(path, "/");

            return this.serviceName = StringUtils.substringAfter(path, "/");
        }
        else
        {
            return null;
        }
    }

    public Action getAction()
    {
        return action;
    }
}
