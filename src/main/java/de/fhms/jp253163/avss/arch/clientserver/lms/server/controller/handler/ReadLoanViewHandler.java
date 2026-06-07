package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOLoanSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IReadLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.DTOLoanSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.ILoan;

import java.util.ArrayList;
import java.util.Objects;

public class ReadLoanViewHandler implements IReadLoanViewHandler
{
    private ILoanRepository loanRepository;
    private ArrayList<IDTOLoanSet> loanSets;

    public ReadLoanViewHandler(ILoanRepository loanRepository) throws NullPointerException
    {
        Objects.requireNonNull(loanRepository);

        this.loanRepository = loanRepository;

        this.loanSets = new ArrayList<>();
    }

    @Override
    public int invoke(IRequestDTOLoanRead iRequestDTOLoanRead)
    {
        if(iRequestDTOLoanRead == null)
        {
            return GlobalResponseCode.READ_LOAN_INVALID_REQUEST.ordinal();
        }

        ILoan[] loans = null;
        int loanID = iRequestDTOLoanRead.getLoanID();

        if(loanID == -1) // Read all
        {
            loans = loanRepository.readAllLoans();

            if(loans == null || loans.length == 0)
            {
                return GlobalResponseCode.READ_LOAN_EMPTY_DATABASE.ordinal();
            }
            else
            {
                for(ILoan loan : loans)
                {
                    IDTOLoanSet loanSet = new DTOLoanSet(loan.getId(), loan.getAmount(), loan.getRemainingBalance(), loan.getInterestRate(), loan.getTerm(), loan.getRemainingTerm(), loan.getBorrower());

                    loanSets.add(loanSet);
                }

                return GlobalResponseCode.READ_LOAN_FOUND_SUCCESS.ordinal();
            }
        }
        else
        {
            ILoan loan = loanRepository.readLoan(loanID);

            if(loan == null)
                return GlobalResponseCode.READ_LOAN_NOT_FOUND.ordinal();
            else
            {
                IDTOLoanSet loanSet = new DTOLoanSet(loan.getId(), loan.getAmount(), loan.getRemainingBalance(), loan.getInterestRate(), loan.getTerm(), loan.getRemainingTerm(), loan.getBorrower());

                loanSets.add(loanSet);

                return GlobalResponseCode.READ_LOAN_FOUND_SUCCESS.ordinal();
            }
        }
    }

    @Override
    public ArrayList<IDTOLoanSet> getResponseData() {
        ArrayList<IDTOLoanSet> list = new ArrayList<>();

        for(IDTOLoanSet set : loanSets)
        {
            list.add(new DTOLoanSet(set.getID(), set.getAmount(), set.getRemainingBalance(), set.getInterestRate(), set.getTerm(), set.getRemainingTerm(), set.getBorrower()));
        }

        this.loanSets.clear();

        return list;
    }
}