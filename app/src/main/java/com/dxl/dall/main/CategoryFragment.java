package com.dxl.dall.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseFragment;
import com.dxl.dall.entity.CategoryResult;

import butterknife.BindView;

import static com.dxl.dall.entity.GlobalConfig.CATEGORY_TITLES;

/**
 * @author dxl
 */
public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.ICategoryView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private CategoryResultAdapter mAdapter;

    public static CategoryFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("index", index);

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected CategoryPresenter createPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected void initView() {
        mAdapter = new CategoryResultAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.loadCategoryResult(true);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadCategoryResult(true);
            }
        });

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_category;
    }

    @Override
    public String getCategoryName() {
        int index = getArguments().getInt("index", 0);
        return CATEGORY_TITLES[index];
    }

    @Override
    public void onSuccess(CategoryResult datas) {
        mAdapter.setDatas(datas);
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean show) {
        mRefreshLayout.setRefreshing(show);
    }
}
