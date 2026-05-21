package de.fhms.jp253163.avss.arch.layered.normal.lms.presentation;

import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.IAddBorrowerViewHandler;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.IBorrowerDTO;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.ILoanDTO;
import de.fhms.jp253163.avss.arch.layered.normal.lms.presentation.contracts.IRemoveBorrowerViewHandler;

import java.util.List;

public interface IView
{
    /**
     * Manages the action which should be performed exactly when a borrower is being created.
     * Usually,
     * the action is implemented by a presenter instance which will use the model to create the borrower
     * with the given data from this handler (what data was entered? And so on).
     * The view will anyway know nothing about what happens exactly.
     * Only execute if registered.
     *
     * How this view will invoke the corresponding handlers:
     *
     * in a GUI: it assumed an ActionListener was registered yet for the corresponding component (e.g. "Add" button).
     *           The registered ActionListener will on the other hand use the set handler instance (by the presenter)
     *           in order to invoke it.
     *
     * in a console: the start() function will simply invoke the needed handlers exactly at the times when needed (see its JavaDoc comment)
     *
     * @param addBorrower
     */
    public void setAddBorrowerViewHandler(IAddBorrowerViewHandler addBorrower);
    public void setRemoveBorrowerViewHandler(IRemoveBorrowerViewHandler removeBorrower);

    /**
     * Starts the view.
     *
     * GUI: might initialize the window components and their event handlers.
     * Console: starts the actual program loop to 1. read user-input, 2. parse it and 3. invoke the corresponding event handlers set.
     */
    public void start();

    public void showErrorMessage(String message);

    /**
     * Sets an amount of borrower data in order to show it directly.
     *
     * in a GUI: updates the list UI components (e.g.) with all the borrowers given
     * in a console: simply prints all borrower DTOs beautified, one after another, for example.
     *
     * @param borrowers
     */
    public void setBorrowerData(List<IBorrowerDTO> borrowers);
    public void showLoanData(List<ILoanDTO> loans);
}
