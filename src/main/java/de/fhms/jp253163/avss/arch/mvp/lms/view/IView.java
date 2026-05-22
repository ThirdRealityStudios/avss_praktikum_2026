package de.fhms.jp253163.avss.arch.mvp.lms.view;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOBorrowerCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.dto.IRequestDTOLoanCreate;
import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.handler.*;

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
     * @param handler
     */
    public void setCreateBorrowerViewHandler(ICreateBorrowerViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setReadBorrowerViewHandler(IReadBorrowerViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setUpdateBorrowerViewHandler(IUpdateBorrowerViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setDeleteBorrowerViewHandler(IDeleteBorrowerViewHandler handler);

    /**
     *
     * @param handler
     */
    public void setCreateLoanViewHandler(ICreateLoanViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setReadLoanViewHandler(IReadLoanViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setUpdateLoanViewHandler(IUpdateLoanViewHandler handler);

    /**
     * Similar to setCreateBorrowerViewHandler()
     *
     * @param handler
     */
    public void setDeleteLoanViewHandler(IDeleteLoanViewHandler handler);

    /**
     * Starts the view.
     *
     * GUI: might initialize the window components and their event handlers.
     * Console: starts the actual program loop to 1. read user-input, 2. parse it, 3. interpret it and 3. invoke the corresponding event handlers set.
     */
    public void start();

    public void showMessage(String message);

    /**
     * Sets an amount of borrower data in order to show it directly.
     *
     * in a GUI: updates the list UI components (e.g.) with all the borrowers given
     * in a console: simply prints all borrower DTOs beautified, one after another, for example.
     *
     * Optionally updates the view.
     *
     * @param borrowers
     */
    public void setBorrowerData(List<IRequestDTOBorrowerCreate> borrowers);
    public void setLoanData(List<IRequestDTOLoanCreate> loans);
}
