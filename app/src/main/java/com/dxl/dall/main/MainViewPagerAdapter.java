package com.dxl.dall.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dxl.dall.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTtitles;
    private List<Fragment> mFragments;

    public MainViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTtitles = titles;
        mFragments = new ArrayList<>();
    }

    public void addFragment(BaseFragment fragment){
        mFragments.add(fragment);

    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTtitles[position];
    }
}
