package com.dxl.dall.main;

import com.dxl.dall.base.BasePresenter;
import com.dxl.dall.entity.CategoryResult;
import com.dxl.dall.network.NetWork;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dxl
 * @date 2019/2/15 11:00
 */
public class MainPresenter extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {

    private Disposable mDisposable;

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mView.isLoadingImage(false);
        }
    }

    @Override
    public void getTopImageUrl() {
        mView.isLoadingImage(true);

        NetWork.getGankApi().getRandomBeauties(1)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<CategoryResult, ObservableSource<CategoryResult.ResultsBean>>() {
                    @Override
                    public ObservableSource<CategoryResult.ResultsBean> apply(CategoryResult categoryResult) throws Exception {
                        return Observable.fromIterable(categoryResult.results);
                    }
                }).take(1)
                .map(new Function<CategoryResult.ResultsBean, String>() {
                    @Override
                    public String apply(CategoryResult.ResultsBean resultsBean) throws Exception {
                        return resultsBean.url;
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                mView.showTopImage(s);
            }

            @Override
            public void onError(Throwable e) {
                mView.getImageError(e);
                mView.isLoadingImage(false);
            }

            @Override
            public void onComplete() {
                mView.isLoadingImage(false);
            }
        });
    }
}
