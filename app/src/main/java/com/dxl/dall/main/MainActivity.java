package com.dxl.dall.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseActivity;
import com.dxl.dall.entity.GlobalConfig;

import butterknife.BindView;

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
        String[] titles = new String[]{
                GlobalConfig.CATEGORY_NAME_APP,
                GlobalConfig.CATEGORY_NAME_ANDROID,
                GlobalConfig.CATEGORY_NAME_IOS,
                GlobalConfig.CATEGORY_NAME_FRONT_END,
                GlobalConfig.CATEGORY_NAME_RECOMMEND,
                GlobalConfig.CATEGORY_NAME_RESOURCE
        };
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), titles);

        for (int i = 0; i < 6; i++) {
            pagerAdapter.addFragment(CategoryFragment.newInstance());

        }

        mViewPager.setAdapter(pagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }
}
