package com.dxl.dall.base;

/**
 * @author dxl
 * @date 2019/2/13 14:57
 */
public class BasePresenter<T extends IView> implements IPresenter<T>  {

    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
