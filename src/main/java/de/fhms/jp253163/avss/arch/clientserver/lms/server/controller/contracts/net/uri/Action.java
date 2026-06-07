package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import java.net.URI;
import java.util.Objects;

public class Action
{
    private String path = null;
    private String name = null;
    private String value = null;

    public Action(URI uri)
    {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(uri.getPath());

        this.path = uri.getPath();

        getName(); // initializes global vars automatically
    }

    /**
     * @return Can be null if there is no action in the URI (e.g. if there is also no service yet). Otherwise, returns the corresponding action name, e.g. "create".
     */
    public String getName()
    {
        if(name != null)
            return name;

        if(path.startsWith("/"))
        {
            String[] strs = path.split("/");

            if(strs.length >= 3 && !strs[2].isEmpty())
            {
                this.name = strs[2];

                if(strs.length >= 4 && !strs[3].isEmpty())
                {
                    this.value = strs[3];
                }

                return this.name;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * @return Can be null if there is no value in the given URI. Otherwise returns the corresponding value.
     */
    public String getValue()
    {
        return value;
    }
}
