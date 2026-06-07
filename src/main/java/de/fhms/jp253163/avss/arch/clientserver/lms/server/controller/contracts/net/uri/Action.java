package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri;

import org.apache.commons.lang3.StringUtils;

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
        String serviceTruncatedPath = null;
        int serviceNameFromIndex = -1;
        int serviceNameEndIndex = -1;
        int pathTruncationWidth = 0;

        if(name != null)
            return name;

        if(path.startsWith("/") && StringUtils.countMatches(path, "/") >= 2)
        {
            // Indices for service name inclusive both forward slashes
            serviceNameFromIndex = 0;
            serviceNameEndIndex = path.indexOf("/", 1);
            pathTruncationWidth = serviceNameEndIndex - serviceNameFromIndex + 1;

            // E.g. you have "/loan/update/43". It will truncate to "update/43" only.
            serviceTruncatedPath = StringUtils.truncate(path, pathTruncationWidth);

            if(serviceTruncatedPath.isEmpty())
                return null; // No action

            // E.g. "update/43" remaining for serviceTruncatedPath
            if(StringUtils.countMatches(serviceTruncatedPath, "/") > 0)
            {
                this.name = StringUtils.substringBefore(serviceTruncatedPath, "/");

                // So also directly parse the value, e.g. with "update/43" it is "43" (but it could also be non-numeric or contain more slashes!)
                this.value = StringUtils.substringAfter(serviceTruncatedPath, "/");

                return name;
            }
            else
            {
                return name = serviceTruncatedPath;
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
