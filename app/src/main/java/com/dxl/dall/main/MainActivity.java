package com.dxl.dall.main;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dxl.dall.R;
import com.dxl.dall.base.BaseActivity;
import com.dxl.dall.entity.GlobalConfig;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dxl.dall.entity.GlobalConfig.CATEGORY_TITLES;

/**
 * @author dxl
 * @date 2019/2/13 14:40
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {

    @BindView(R.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_top)
    ImageView mTopImage;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), CATEGORY_TITLES);

        for (int i = 0; i < CATEGORY_TITLES.length; i++) {
            pagerAdapter.addFragment(CategoryFragment.newInstance(i));
        }
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        mTablayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        fabOnClicked();
    }

    @OnClick(R.id.fab)
    void fabOnClicked(){
        mPresenter.getTopImageUrl();
    }

    @Override
    public void showTopImage(String url) {
        Glide.with(this).load(url)
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .centerCrop())
                .into(mTopImage);
    }

    @Override
    public void getImageError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isLoadingImage(boolean isLoading) {
        if (isLoading) {
            mFab.setEnabled(false);
        }else {
            mFab.setEnabled(true);
        }
    }
}
