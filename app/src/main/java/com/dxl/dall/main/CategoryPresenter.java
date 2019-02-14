package com.dxl.dall.main;

import com.dxl.dall.base.BasePresenter;
import com.dxl.dall.entity.CategoryResult;
import com.dxl.dall.network.NetWork;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dxl
 */
public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryView> implements CategoryContract.ICategoryPresenter {


    private int mPage = 1;

    @Override
    public void loadCategoryResult(boolean refresh) {
        if (refresh) {
            mView.showLoading(true);
            mPage = 1;
        } else {
            mPage++;
        }
        NetWork.getGankApi().getCategoryDate(mView.getCategoryName(), 10, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        mView.onSuccess(categoryResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                        mView.showLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        mView.showLoading(false);
                    }
                });
    }
}
