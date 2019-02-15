package com.dxl.dall.search;

import android.text.TextUtils;

import com.dxl.dall.base.BasePresenter;
import com.dxl.dall.entity.CategoryResult;
import com.dxl.dall.entity.SearchResult;
import com.dxl.dall.network.NetWork;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dxl
 * @date 2019/2/15 14:22
 */
public class SearchPresenter extends BasePresenter<SearchContract.ISearchView> implements SearchContract.ISearchPresenter {

    private int count = 1;
    private Disposable mDisposable;

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void loadCategoryResult(String search, boolean refresh) {
        if (TextUtils.isEmpty(search)) {
            mView.onError(new Throwable("搜索内容不能为空."));
            return;
        }
        if (refresh) {
            count = 1;
        } else {
            count++;
        }

        NetWork.getGankApi().getSearchResult(search, 10, count)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<SearchResult, CategoryResult>() {
                    @Override
                    public CategoryResult apply(SearchResult searchResult) throws Exception {
                        return searchResult.toCategoryResult();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        mView.onSuccess(categoryResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
