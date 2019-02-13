package com.dxl.dall.base;

/**
 * @author dxl
 * @date 2019/2/13 14:43
 */
public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView();

    boolean isViewAttached();

}
