package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOLoanDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IDeleteLoanViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.dto.RequestDTOLoanRead;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.ILoanRepository;

import java.util.Objects;

public class DeleteLoanViewHandler implements IDeleteLoanViewHandler
{
    private ILoanRepository loanRepository;

    public DeleteLoanViewHandler(ILoanRepository loanRepository) throws NullPointerException
    {
        Objects.requireNonNull(loanRepository);

        this.loanRepository = loanRepository;
    }

    @Override
    public int invoke(IRequestDTOLoanDelete iRequestDTOLoanDelete)
    {
        if(iRequestDTOLoanDelete == null)
        {
            return GlobalResponseCode.DELETE_LOAN_INVALID_REQUEST.ordinal();
        }

        if(iRequestDTOLoanDelete.getLoanID() == -1)
            return GlobalResponseCode.DELETE_LOAN_NO_SELECTED_DATASET.ordinal();
        else if(loanRepository.deleteLoan(iRequestDTOLoanDelete.getLoanID()))
        {
            return GlobalResponseCode.DELETE_LOAN_SUCCESS.ordinal();
        }
        else
            return GlobalResponseCode.DELETE_LOAN_NOT_PAID.ordinal();
    }
}
