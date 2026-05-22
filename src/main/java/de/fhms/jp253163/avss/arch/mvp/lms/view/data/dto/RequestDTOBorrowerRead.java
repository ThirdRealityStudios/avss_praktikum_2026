package de.fhms.jp253163.avss.arch.mvp.lms.view.data.dto;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerRead;

public class RequestDTOBorrowerRead implements IRequestDTOBorrowerRead
{


    @Override
    public int[] getBorrowerIDs() {
        return new int[0];
    }

    @Override
    public String getFirstname() {
        return "";
    }

    @Override
    public String getLastname() {
        return "";
    }

    @Override
    public int getDayOfBirth() {
        return 0;
    }

    @Override
    public int getMonthOfBirth() {
        return 0;
    }

    @Override
    public int getYearOfBirth() {
        return 0;
    }

    @Override
    public String getStreet() {
        return "";
    }

    @Override
    public String getNo() {
        return "";
    }

    @Override
    public String getPlace() {
        return "";
    }

    @Override
    public int getZip() {
        return 0;
    }

    @Override
    public double getSchufaScore() {
        return 0;
    }

    @Override
    public int[] getLoanIDs() {
        return new int[0];
    }
}
