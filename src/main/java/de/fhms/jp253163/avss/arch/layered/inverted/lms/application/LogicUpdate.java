package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.*;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicResponseDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.LogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.ITerminalUpdate;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.TerminalRequestDTO;

import java.util.Date;

public class LogicUpdate implements ILogicUpdate
{
    private ITerminalUpdate terminalUpdate;
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;
    private ISchufaRepository schufaRepository;

    private ITerminalRequestDTO terminalRequestDTO;

    public LogicUpdate(ITerminalUpdate terminalUpdate, IBorrowerRepository borrowerRepository, ILoanRepository loanRepository, ISchufaRepository schufaRepository) throws NullPointerException
    {
        if(terminalUpdate == null || borrowerRepository == null || loanRepository == null || schufaRepository == null)
            throw new NullPointerException("Expecting an instance (not null) in constructor");

        this.terminalUpdate = terminalUpdate;
        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;
        this.schufaRepository = schufaRepository;
    }

    private int max(int[] arr)
    {
        if(arr == null || arr.length == 0)
            return -1;

        int max = 0;

        for(int i = 0; i < arr.length; i++)
            if(arr[i] > arr[max])
                max = i;

        return max;
    }

    private IBorrower requestBorrowerFrom()
    {

    }

    private ILogicRequestDTO[] processBorrowerRequest(int[] jsonNumbers, long[] longs, double[] doubles, String[] strings, int crudAction)
    {
        ILogicRequestDTO[] logicReqDTOs = null;

        if(crudAction == 0 && strings.length == 7)
        {
            if(longs.length != 1)
                return null;

            long birthday = longs[0]; // in ms or smth since 1970: ofc a problem because you could be older

            if(doubles.length != 1)
                return null;

            String firstname = strings[1],
                    lastname = strings[2],
                    street = strings[3],
                    no = strings[4],
                    place = strings[5],
                    zip = strings[6];

            int zipConverted = -1;

            try
            {
                zipConverted = Integer.parseInt(zip);
            }
            catch(NumberFormatException e)
            {
                return null;
            }

            IResidentialAddress residentialAddress = new ResidentialAddress(street, no, zipConverted, place);

            Date dateOfBirth = new Date(birthday);

            int maxIndex = max(jsonNumbers);
            int maxValue = jsonNumbers[maxIndex];

            logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.createBorrower(maxValue, firstname, lastname, dateOfBirth, residentialAddress, 0.9d)};
        }
        else if(crudAction == 1 && longs.length == 1)
        {
            int id = (int) longs[0];

            logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.readBorrower(jsonNumbers, id)};
        }
        else if(crudAction == 2)
        {
            logicReqDTOs = borrowerRepository.readAllBorrowers(jsonNumbers);
        }
        else if(crudAction == 3 && longs.length >= 1 && strings.length >= 2)
        {
            int borrowerID = (int) longs[0];

            // Make a simulated user terminal request (little "hack" to load the needed borrower dataset for update).
            ITerminalRequestDTO artificialTerminalRequestDTO = new TerminalRequestDTO(new long[]{borrowerID}, null, new String[]{"borrower"}, 1);

            // Save reference to actual real terminal request in order to restore the reference after the artificial call.
            ITerminalRequestDTO backedUpTerminalRequestDTO = terminalRequestDTO;

            // Now do the little "hack" to get the borrower dataset.
            terminalRequestDTO = artificialTerminalRequestDTO;
            ILogicRequestDTO[] artificialRequestData = request(jsonNumbers);
            if(artificialRequestData == null || artificialRequestData.length == 0)
                return null;

            ILogicRequestDTO borrowerRequestData = artificialRequestData[0];

            if(borrowerRequestData == null)
                return null;

            Borrower readBorrowerObj = null;

            Class<Borrower> objectType = borrowerRequestData.getObjectType();

            if(objectType == null)
                return null;

            Object optionalObject = borrowerRequestData.getOptionalObject();

            if(optionalObject == null)
                return null;

            // TODO Check if type comparison causes trouble maybe??!
            if(!objectType.equals(Borrower.class))
                return null;

            readBorrowerObj = (Borrower) optionalObject;

            // Restore the old reference
            terminalRequestDTO = backedUpTerminalRequestDTO;

            String targetField = strings[1];

            // further with case-differentiation (e.g. "is firstname" meant to update?" and so on)
            if(targetField.equalsIgnoreCase("firstname"))
            {
                String firstnameValue = strings[2];

                if(!readBorrowerObj.setFirstname(firstnameValue))
                    return null;
            }
            else if(targetField.equalsIgnoreCase("lastname"))
            {
                String lastnameValue = strings[2];

                if(!readBorrowerObj.setLastname(lastnameValue))
                    return null;
            }
            else if(targetField.equalsIgnoreCase("birthday") && longs.length == 2)
            {
                long birthday = longs[1];
                Date birthdayObj = new Date(birthday);

                if(!readBorrowerObj.setDateOfBirth(birthdayObj))
                    return null;
            }
            else if(targetField.equalsIgnoreCase("address") && strings.length == 6)
            {
                String street = strings[2];
                String no = strings[3];
                String place = strings[4];
                String zip = strings[5];

                int zipParsed = -1;

                try
                {
                    zipParsed = Integer.parseInt(zip);
                }
                catch(NumberFormatException e)
                {
                    return null;
                }

                IResidentialAddress residentialAddress = null;

                try
                {
                    residentialAddress = new ResidentialAddress(street, no, zipParsed, place);
                }
                catch(IllegalArgumentException e)
                {
                    return null;
                }

                if(!readBorrowerObj.setResidentialAddress(residentialAddress))
                    return null;
            }
            else if(targetField.equalsIgnoreCase("schufa"))
            {
                double currentSchufaScore = schufaRepository.readSchufaScore(borrowerID);

                if(!readBorrowerObj.setSchufaScore(currentSchufaScore))
                    return null;
            }
            else if(targetField.equalsIgnoreCase("loans"))
            {
                if(longs.length < 2)
                    return null;

                int[] loanIDs = new int[longs.length-1]; // at least one loan ID exists in that

                for(int i = 0; i < loanIDs.length; i++)
                {
                    loanIDs[i] = (int) longs[i+1];
                }

                if(!readBorrowerObj.setLoans(loanIDs))
                    return null;
            }
            else
            {
                return null;
            }

            logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.updateBorrower(readBorrowerObj)};
        }
        else if(crudAction == 4 && longs.length == 1)
        {
            int borrowerID = (int) longs[0];

            logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.deleteBorrower(borrowerID)};
        }

        return logicReqDTOs;
    }

    private ILogicRequestDTO[] processLoanRequest(int[] jsonNumbers, long[] longs, double[] doubles, String[] strings, int crudAction)
    {
        // TODO Adjust this function for loan management.
        ILogicRequestDTO[] logicReqDTOs = null;

        if(crudAction == 0 && doubles.length == 2 && longs.length == 2)
        {
            int borrowerID = (int) longs[0];
            int term = (int) longs[1];

            double amount = doubles[0];
            double interestRate = doubles[1];

            logicReqDTOs = new ILogicRequestDTO[]{loanRepository.createLoan(borrowerID, amount, interestRate, term)};
        }
        else if(crudAction == 1 && longs.length == 1)
        {
            int borrowerID = (int) longs[0];

            logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.readBorrower(jsonNumbers, borrowerID)};
        }
        else if(crudAction == 2)
        {
            logicReqDTOs = borrowerRepository.readAllBorrowers(jsonNumbers);
        }
        else if(crudAction == 3 && longs.length >= 1 && strings.length >= 2)
        {
            int loanID = (int) longs[0];
            ILoan readLoan = null; // TODO Load with "hack" !!!

            String updateTarget0 = strings[1];

            if(strings.length == 2)
            {
                if(updateTarget0.equalsIgnoreCase("borrowerid"))
                {
                    if(longs.length == 2)
                    {
                        int borrowerIDToAttach = (int) longs[1];

                        if(!readLoan.setBorrower(borrowerIDToAttach))
                            return null;

                        logicReqDTOs = new ILogicRequestDTO[]{loanRepository.updateLoan(readLoan)};
                        // TODO implement
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
            else if(strings.length == 3)
            {
                String updateTarget1 = strings[2];

                if(updateTarget0.equalsIgnoreCase("book"))
                {
                    if(updateTarget1.equalsIgnoreCase("installment") && longs.length == 1)
                    {
                        if(!readLoan.payInstallment())
                            return null;

                        logicReqDTOs = new ILogicRequestDTO[]{loanRepository.updateLoan(readLoan)};
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }

        return logicReqDTOs;
    }

    @Override
    public ILogicRequestDTO[] request(int[] jsonNumbers)
    {
        // Should the terminal be skipped?
        boolean isArtificialCall = terminalRequestDTO != null;

        if(!isArtificialCall) // then not a "hacked" or artificial call, in order to use this function to read a borrower or loan dataset in between a routine.
            terminalRequestDTO = terminalUpdate.request();

        if(terminalRequestDTO == null)
            return null;

        int crudAction = terminalRequestDTO.getCRUDAction();

        String[] strings = terminalRequestDTO.getStrings();
        long[] longs = terminalRequestDTO.getLongs();
        double[] doubles = terminalRequestDTO.getDoubles();

        if(strings == null || strings.length == 0 || longs == null || doubles == null)
            return null;

        ILogicRequestDTO[] logicReqDTOs = null;

        if(strings[0].equalsIgnoreCase("borrower"))
        {
            logicReqDTOs = processBorrowerRequest(jsonNumbers, longs, doubles, strings, crudAction);
        }
        else if(strings[0].equalsIgnoreCase("loan"))
        {
            logicReqDTOs = processLoanRequest(jsonNumbers, longs, doubles, strings, crudAction);
        }

        // Make sure the re-called function will later not think this is an artificial call (in order to ignore the call of the real terminal)
        terminalRequestDTO = null;

        return logicReqDTOs;
    }

    @Override
    public void response(ILogicResponseDTO response)
    {

    }
}
