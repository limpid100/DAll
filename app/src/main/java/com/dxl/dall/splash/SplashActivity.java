package com.dxl.dall.splash;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dxl.dall.R;
import com.dxl.dall.Util.CommonUtil;
import com.dxl.dall.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author dxl
 * @date 2019/2/13 14:55
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashConstract.ISplashView {
    @BindView(R.id.btn_skip)
    Button mSkipButton;
    @BindView(R.id.iv_splash)
    ImageView mSplashImage;

    public static final int COUNT_DOWN_TIME = 5;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView() {
        mPresenter.getImageURL();
        CommonUtil.countDown(COUNT_DOWN_TIME)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (mSkipButton.getVisibility() == View.GONE) {
                            mSkipButton.setVisibility(View.VISIBLE);
                        }
                        mSkipButton.setText("跳过" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(SplashActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void showImage(String url) {

        //Glide4.x写法和Glide3.x不一样了
        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.mipmap.splash_default)
                        .error(R.mipmap.splash_default);
        Glide.with(this).load(url).apply(requestOptions).into(mSplashImage);
    }

    @Override
    public void onError(Throwable e) {
        Glide.with(this).load(R.mipmap.splash_default).into(mSplashImage);
    }
}
