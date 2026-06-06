package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

import com.sun.net.httpserver.HttpHandler;

public interface IController
{
    public boolean postBorrower(String action, String id, String data);
    public String[] getBorrower(String id);

    public void postLoan(String action, String id, String json);
    public String[] getLoan(String id);

    public void setPostHandler(HttpHandler handler);
    public void start();
}