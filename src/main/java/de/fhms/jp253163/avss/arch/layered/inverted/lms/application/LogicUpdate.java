package de.fhms.jp253163.avss.arch.layered.inverted.lms.application;

import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.Borrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.IResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.ResidentialAddress;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.ILogicResponseDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.LogicRequestDTO;
import de.fhms.jp253163.avss.arch.layered.inverted.lms.application.data.dto.LogicResponseDTO;
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

    @Override
    public ILogicRequestDTO[] request()
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
            else if(crudAction == 1 && longs.length == 1)
            {
                int id = (int) longs[0];

                logicReqDTOs = new ILogicRequestDTO[]{borrowerRepository.readBorrower(id)};
            }
            else if(crudAction == 2)
            {
                logicReqDTOs = borrowerRepository.readAllBorrowers();
            }
            else if(crudAction == 3 && longs.length == 1 && strings.length >= 2)
            {
                int borrowerID = (int) longs[0];

                ILogicRequestDTO logicRequestDTO = new LogicRequestDTO(borrowerID+".json", null, 2, Borrower.class);

                // Make a simulated user terminal request (little "hack" to load the needed borrower dataset for update).
                ITerminalRequestDTO artificialTerminalRequestDTO = new TerminalRequestDTO(new long[]{borrowerID}, null, new String[]{"borrower"}, 1);

                // Save reference to actual real terminal request in order to restore the reference after the artificial call.
                ITerminalRequestDTO backedUpTerminalRequestDTO = terminalRequestDTO;

                // Now do the little "hack" to get the borrower dataset.
                terminalRequestDTO = artificialTerminalRequestDTO;
                ILogicRequestDTO[] readBorrower = request();
                if(readBorrower == null || readBorrower.length == 0)
                    return null;

                Borrower readBorrowerObj = null;
                if(readBorrower[0].getObjectType().equals(Borrower.class)) // TODO Check if causes trouble maybe??!
                    readBorrowerObj = (Borrower) readBorrower[0].getOptionalObject();
                else
                    return null;

                // Restore the old reference
                terminalRequestDTO = backedUpTerminalRequestDTO;

                // TODO Implement further with case-differentiation (is "firstname" and so on) ?
                IBorrower loadedBorrower = borrowerRepository.readBorrower(borrowerID).getOptionalObject();

                String targetField = strings[1];

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

        // Make sure the re-called function will later not think this is an artificial call (in order to ignore the call of the real terminal)
        terminalRequestDTO = null;

        return logicReqDTOs;
    }

    @Override
    public void response(ILogicResponseDTO response)
    {

    }
}
