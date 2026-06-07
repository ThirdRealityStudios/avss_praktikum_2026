package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net;

public interface EntryPoints
{
    public static String ENTRY_POINT_BORROWER = "/borrower",
            ENTRY_POINT_LOAN = "/loan";

    public static String ENTRY_POINT_BORROWER_CREATE = ENTRY_POINT_BORROWER + "/create",
            ENTRY_POINT_BORROWER_READ = ENTRY_POINT_BORROWER + "/read",
            ENTRY_POINT_BORROWER_UPDATE = ENTRY_POINT_BORROWER + "/update",
            ENTRY_POINT_BORROWER_DELETE = ENTRY_POINT_BORROWER + "/delete";

    public static String ENTRY_POINT_LOAN_CREATE = ENTRY_POINT_LOAN + "/create",
            ENTRY_POINT_LOAN_READ = ENTRY_POINT_LOAN + "/read",
            ENTRY_POINT_LOAN_UPDATE = ENTRY_POINT_LOAN + "/update",
            ENTRY_POINT_LOAN_DELETE = ENTRY_POINT_LOAN + "/delete";
}
