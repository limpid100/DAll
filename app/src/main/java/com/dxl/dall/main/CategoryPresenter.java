package com.dxl.dall.main;

import android.database.sqlite.SQLiteDatabase;

import com.dxl.dall.base.BaseApplication;
import com.dxl.dall.base.BasePresenter;
import com.dxl.dall.entity.CategoryResult;
import com.dxl.dall.network.NetWork;
import com.dxl.dall.util.DatabaseHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.dxl.dall.database.SqliteDateBaseHelper.CATEGORY_RESULT_TABLE_NAME;

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
                .observeOn(Schedulers.io())
                //先保存数据库
                .doOnNext(new Consumer<CategoryResult>() {
                    @Override
                    public void accept(CategoryResult categoryResult) {
                        saveDatabase(categoryResult);
                    }
                })
                .onErrorResumeNext(Observable.create(new ObservableOnSubscribe<CategoryResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<CategoryResult> emitter) throws Exception {
                        emitter.onNext(DatabaseHelper.selectCategoryResult(mView.getCategoryName()));
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()))
                .onErrorResumeNext(Observable.<CategoryResult>error(new Throwable("获取本地数据失败")))
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

    /**
     * 在线获取的数据，保存数据库
     * 如果数据库中已经存在，用数据库的数据去更新在线获取的数据（isread）
     *
     * @param categoryResult
     */
    private void saveDatabase(CategoryResult categoryResult) {
        SQLiteDatabase db = BaseApplication.getInstance().getDB();
        for (CategoryResult.ResultsBean result : categoryResult.results) {
            CategoryResult.ResultsBean databaseResult = DatabaseHelper.selectCategoryResultBean("SELECT ID, ISREAD FROM " + CATEGORY_RESULT_TABLE_NAME + " WHERE ID = ? ",
                    new String[]{result._id});
            if (databaseResult == null) {
                db.insert(CATEGORY_RESULT_TABLE_NAME, null, result.getContentValues());
            } else {
                result.isread = databaseResult.isread;
            }
        }
    }
}
