package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller;

public interface IController
{
    public boolean postBorrower(String action, String id, String data);
    public String[] getBorrower(String id);

    public void postLoan(String action, String id, String json);
    public String[] getLoan(String id);

    public void start();
}