package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net;

import java.net.URI;

public interface IRequestParser
{
    public void parse(URI path, String entryPoint);
}
