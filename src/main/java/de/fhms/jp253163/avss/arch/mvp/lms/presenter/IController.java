package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import com.sun.net.httpserver.HttpHandler;

public interface IController
{
    public boolean postBorrower(String action, String data);
    public String[] getBorrower(String id);

    public void postLoan(String json);
    public String[] getLoan(String id);

    public void setPostHandler(HttpHandler handler);
    public void start();
}