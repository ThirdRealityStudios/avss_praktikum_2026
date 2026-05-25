package de.fhms.jp253163.avss.arch.mvp.lms.presenter;

import de.fhms.jp253163.avss.arch.mvp.lms.view.IView;

public class Presenter implements IPresenter
{
    private IView view;

    public Presenter(IView view) throws NullPointerException
    {
        if(!setView(view))
            throw new NullPointerException();
    }

    @Override
    public boolean setView(IView view)
    {
        if(view == null)
            return false;

        this.view = view;

        return true;
    }
}
