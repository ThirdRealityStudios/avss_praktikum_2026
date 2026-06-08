package de.fhms.jp253163.avss.arch.clientserver.lms.client.consumer;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.net.http.HttpClient;
import java.util.Objects;

public class LoanConsumer implements ILoanRepository
{
    private HttpClient client;

    public LoanConsumer(HttpClient client)
    {
        Objects.requireNonNull(client);

        this.client = client;
    }

    @Override
    public ILoan createLoan(int i, double v, double v1, int i1) {
        return null;
    }

    @Override
    public ILoan readLoan(int i) {
        return null;
    }

    @Override
    public ILoan[] readAllLoans() {
        return new ILoan[0];
    }

    @Override
    public boolean updateLoan(ILoan iLoan) {
        return false;
    }

    @Override
    public boolean deleteLoan(int i) {
        return false;
    }

    @Override
    public boolean isValidBorrowerIDForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidAmountForCreation(double v) {
        return false;
    }

    @Override
    public boolean isValidInterestRateForCreation(double v) {
        return false;
    }

    @Override
    public boolean isValidTermForCreation(int i) {
        return false;
    }

    @Override
    public boolean isValidBorrowerIDForUpdate(int i) {
        return false;
    }
}
