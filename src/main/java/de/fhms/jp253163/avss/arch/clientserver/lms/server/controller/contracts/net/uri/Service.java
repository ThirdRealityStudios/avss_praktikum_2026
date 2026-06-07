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

        getName(); // initialize necessary global vars
    }

    /**
     * @return Can be null if there is no service in the given URI. Otherwise, the service name is returned.
     */
    public String getName()
    {
        if(this.serviceName != null)
            return this.serviceName;

        if(this.path.startsWith("/"))
        {
            if(StringUtils.countMatches(path, "/") > 1)
            {
                this.serviceName = StringUtils.substringBetween(path, "/");
                System.out.println("Service (code 0): " + this.serviceName); // TODO Delete debug log

                if(this.serviceName.isEmpty())
                    return null;

                return this.serviceName;
            }

            this.serviceName = StringUtils.substringAfter(path, "/");
            System.out.println("Service (code 1): " + this.serviceName); // TODO Delete debug log

            if(this.serviceName.isEmpty())
                return null; // no service (only entry point called)

            return this.serviceName;
        }
        else
        {
            return null;
        }
    }

    /**
     * @return Not null. Never. Always returns an Action instance.
     */
    public Action getAction()
    {
        return this.action;
    }
}
