package com.dxl.dall.main;

import android.os.Bundle;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseFragment;

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.ICategoryView {

    public static CategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_category;
    }
}
