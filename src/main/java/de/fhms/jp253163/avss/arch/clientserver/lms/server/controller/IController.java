package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.sun.net.httpserver.HttpExchange;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.uri.Action;

public interface IController
{
    public void postBorrower(HttpExchange exchange, Action action, String body);
    public String getBorrower(HttpExchange exchange, Action action);

    public void postLoan(HttpExchange exchange, Action action, String body);
    public String getLoan(HttpExchange exchange, Action action);

    public void start();
}