package com.dxl.dall.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseActivity;
import com.dxl.dall.entity.GlobalConfig;

import butterknife.BindView;

import static com.dxl.dall.entity.GlobalConfig.CATEGORY_TITLES;

/**
 * @author dxl
 * @date 2019/2/13 14:40
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), CATEGORY_TITLES);

        for (int i = 0; i < 6; i++) {
            pagerAdapter.addFragment(CategoryFragment.newInstance(i));
        }
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        mTablayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }
}
