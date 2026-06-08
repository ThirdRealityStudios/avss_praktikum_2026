package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.Action;

public interface IRequestParser
{
    public void parse(Action action, String body);
}
