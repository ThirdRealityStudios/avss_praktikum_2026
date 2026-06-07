package de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.handler;

import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.net.GlobalResponseCode;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.dto.IRequestDTOBorrowerDelete;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.controller.contracts.handler.IDeleteBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.clientserver.lms.server.model.IBorrowerRepository;

import java.util.Objects;

public class DeleteBorrowerViewHandler implements IDeleteBorrowerViewHandler
{
    private IBorrowerRepository borrowerRepository;

    public DeleteBorrowerViewHandler(IBorrowerRepository borrowerRepository) throws NullPointerException
    {
        Objects.requireNonNull(borrowerRepository);

        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public int invoke(IRequestDTOBorrowerDelete iRequestDTOBorrowerDelete)
    {
        if(iRequestDTOBorrowerDelete == null)
        {
            return GlobalResponseCode.DELETE_BORROWER_INVALID_REQUEST.ordinal();
        }

        if(borrowerRepository.deleteBorrower(iRequestDTOBorrowerDelete.getBorrowerID()))
        {
            return GlobalResponseCode.DELETE_BORROWER_SUCCESS.ordinal();
        }
        else
            return GlobalResponseCode.DELETE_BORROWER_ERROR.ordinal();
    }
}
