package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import com.sun.net.httpserver.HttpHandler;

public interface IController
{
    public void postBorrower(String action, String data);
    public [] getBorrower(String id);

    public void postLoan(String json);
    public void getLoan(String id);

    public void setPostHandler(HttpHandler handler);
    public void start();
}