package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.Action;

public interface IController
{
    public boolean postBorrower(Action action, String body);
    public String getBorrower(Action action);

    public void postLoan(Action action, String body);
    public String getLoan(Action action);

    public void start();
}