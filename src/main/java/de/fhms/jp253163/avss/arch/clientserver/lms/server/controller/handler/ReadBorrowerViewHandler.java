package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IDTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IReadBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.DTOBorrowerSet;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.data.IBorrower;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ReadBorrowerViewHandler implements IReadBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;
    private ArrayList<IDTOBorrowerSet> borrowerSets;

    public ReadBorrowerViewHandler(IBorrowerRepository borrowerRepository) throws NullPointerException
    {
        Objects.requireNonNull(borrowerRepository);

        this.borrowerRepository = borrowerRepository;

        borrowerSets = new ArrayList<>();
    }

    public ArrayList<IDTOBorrowerSet> getResponseData()
    {
        ArrayList<IDTOBorrowerSet> list = new ArrayList<>();

        for(IDTOBorrowerSet set : borrowerSets)
        {
            LocalDate birthday = LocalDate.of(set.getYearOfBirth(), set.getMonthOfBirth(), set.getDayOfBirth());

            list.add(new DTOBorrowerSet(set.getID(), set.getFirstname(), set.getLastname(), birthday, set.getStreet(), set.getNo(), set.getPlace(), set.getZip()));
        }

        this.borrowerSets.clear();

        return list;
    }

    @Override
    public int invoke(IRequestDTOBorrowerRead iRequestDTOBorrowerRead)
    {
        if(iRequestDTOBorrowerRead == null)
        {
            return GlobalResponseCode.READ_BORROWER_INVALID_REQUEST.ordinal();
        }

        IBorrower[] borrowers = null;
        int borrowerID = iRequestDTOBorrowerRead.getBorrowerID();

        if(borrowerID == -1)
        {
            borrowers = borrowerRepository.readAllBorrowers();

            if(borrowers == null || borrowers.length == 0)
            {
                return GlobalResponseCode.READ_BORROWER_EMPTY_DATABASE.ordinal();
            }
            else
            {
                for(IBorrower borrower : borrowers)
                {
                    IDTOBorrowerSet borrowerSet = new DTOBorrowerSet(borrower.getId(), borrower.getFirstname(), borrower.getLastname(), borrower.getDateOfBirth(), borrower.getResidentialAddress().getStreet(), borrower.getResidentialAddress().getNo(), borrower.getResidentialAddress().getPlace(), borrower.getResidentialAddress().getZip());

                    this.borrowerSets.add(borrowerSet);
                }

                return GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal();
            }
        }
        else
        {
            IBorrower borrower = borrowerRepository.readBorrower(borrowerID);

            if(borrower == null)
                return GlobalResponseCode.READ_BORROWER_NOT_FOUND.ordinal();
            else
            {
                IDTOBorrowerSet borrowerSet = new DTOBorrowerSet(borrower.getId(), borrower.getFirstname(), borrower.getLastname(), borrower.getDateOfBirth(), borrower.getResidentialAddress().getStreet(), borrower.getResidentialAddress().getNo(), borrower.getResidentialAddress().getPlace(), borrower.getResidentialAddress().getZip());

                this.borrowerSets.add(borrowerSet);

                return GlobalResponseCode.READ_BORROWER_FOUND_SUCCESS.ordinal();
            }
        }
    }
}