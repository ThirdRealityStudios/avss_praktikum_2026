package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.ResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicResponseDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.ITerminalUpdate;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.presentation.data.ITerminalRequestDTO;

import java.util.Date;

public class LogicUpdate implements ILogicUpdate
{
    private ITerminalUpdate terminalUpdate;
    private IBorrowerRepository borrowerRepository;
    private ILoanRepository loanRepository;
    private ISchufaRepository schufaRepository;

    public LogicUpdate(ITerminalUpdate terminalUpdate, IBorrowerRepository borrowerRepository, ILoanRepository loanRepository, ISchufaRepository schufaRepository) throws NullPointerException
    {
        if(terminalUpdate == null || borrowerRepository == null || loanRepository == null || schufaRepository == null)
            throw new NullPointerException("Expecting an instance (not null) in constructor");

        this.terminalUpdate = terminalUpdate;
        this.borrowerRepository = borrowerRepository;
        this.loanRepository = loanRepository;
        this.schufaRepository = schufaRepository;
    }

    @Override
    public ILogicRequestDTO[] request()
    {
        ITerminalRequestDTO terminalRequestDTO = terminalUpdate.request();

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

                logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.createBorrower(firstname, lastname, dateOfBirth, residentialAddress, 0.9d)};
            }
            else if(crudAction == 1)
            {
                logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.readBorrower()};
            }
            else if(crudAction == 2)
            {
                logicReqDTOs = borrowerRepository.readAllBorrowers();
            }
            else if(crudAction == 3)
            {
                logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.updateBorrower()};
            }
            else if(crudAction == 4)
            {
                logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.deleteBorrower()};
            }
        }
        else if(strings[0].equalsIgnoreCase("loan"))
        {

        }

        return logicReqDTOs;
    }

    @Override
    public void response(ILogicResponseDTO response)
    {

    }
}
