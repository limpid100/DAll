package com.dxl.dall.splash;

import com.dxl.dall.base.BasePresenter;
import com.dxl.dall.entity.BingDailyPic;
import com.dxl.dall.network.NetWork;

import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dxl
 * @date 2019/2/13 14:56
 */
public class SplashPresenter extends BasePresenter<SplashConstract.ISplashView>
        implements SplashConstract.ISplashPresenter {

    private Disposable mDisposable = null;

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void getImageURL() {
        NetWork.getBingApi().getBingDailyPic("js", 0, 8, "zh-CN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BingDailyPic>() {


                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(BingDailyPic bingDailyPic) {
                        List<BingDailyPic.Images> images = bingDailyPic.getImages();
                        String url = "https://cn.bing.com" + images.get(new Random().nextInt(images.size())).getUrl();
                        mView.showImage(url);
                        if (!mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
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
