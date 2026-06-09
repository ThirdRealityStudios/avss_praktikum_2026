package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import de.fhms.jp253163.avss.arch.clientserver.lms.client.view.IView;

public interface IPresenter
{
    /**
     * @param view
     * @return True if given view is not null. Otherwise, false.
     */
    public boolean setView(IView view);

    public IView getView();
}
