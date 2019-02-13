package com.dxl.dall.splash;

import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dxl.dall.R;
import com.dxl.dall.base.BaseActivity;

import butterknife.BindView;

/**
 * @author dxl
 * @date 2019/2/13 14:55
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashConstract.ISplashView {
    @BindView(R.id.btn_skip)
    Button mSkipButton;
    @BindView(R.id.iv_splash)
    ImageView mSplashImage;

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
    }

    @Override
    public void showImage(String url) {
        Glide.with(this).load(url).into(mSplashImage);
    }

    @Override
    public void onError(Throwable e) {
        Glide.with(this).load(R.mipmap.splash_default).into(mSplashImage);
    }
}
