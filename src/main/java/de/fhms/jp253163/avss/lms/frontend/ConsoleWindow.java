package de.fhms.jp253163.avss.lms.frontend;

import de.fhms.jp253163.avss.lms.application.IBorrowerRepository;
import de.fhms.jp253163.avss.lms.application.ILoanRepository;
import de.fhms.jp253163.avss.lms.application.data.IBorrower;
import de.fhms.jp253163.avss.lms.application.data.ILoan;

public class ConsoleWindow implements IConsoleWindow
{
    private ILoanRepository loanRepository;
    private IBorrowerRepository borrowerRepository;

    public ConsoleWindow(ILoanRepository loanRepository, IBorrowerRepository borrowerRepository) throws IllegalArgumentException
    {
        if(loanRepository == null || borrowerRepository == null)
            throw new IllegalArgumentException("at least one argument is null!");

        this.loanRepository = loanRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public void showCommandInput()
    {
        
    }

    @Override
    public void showHelp()
    {

    }

    @Override
    public void showBorrowerData(IBorrower[] borrowers, String optionalMessage)
    {

    }

    @Override
    public void showLoanData(ILoan[] loans, String optionalMessage)
    {

    }
}
