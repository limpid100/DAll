package com.dxl.dall.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseFragment;
import com.dxl.dall.base.CommonRecyclerViewAdapter;
import com.dxl.dall.entity.CategoryResult;

import java.util.List;

import butterknife.BindView;

import static com.dxl.dall.entity.GlobalConfig.CATEGORY_TITLES;
import static com.dxl.dall.main.CategoryResultAdapter.TYPE_MULTIPLE_IMAGE;
import static com.dxl.dall.main.CategoryResultAdapter.TYPE_NO_IMAGE;
import static com.dxl.dall.main.CategoryResultAdapter.TYPE_ONE_IMAGE;

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
        mAdapter = new CategoryResultAdapter(getContext(), new CommonRecyclerViewAdapter.MuiltipleTypeSupport<CategoryResult.ResultsBean>() {
            @Override
            public int getLayoutID(int viewType) {
                int layoutID;
                switch (viewType) {
                    case TYPE_NO_IMAGE:
                        layoutID = R.layout.category_result_item_view_no_image;
                        break;
                    case TYPE_ONE_IMAGE:
                        layoutID = R.layout.category_result_item_view_one_image;
                        break;
                    case TYPE_MULTIPLE_IMAGE:
                    default:
                        layoutID = R.layout.category_result_item_view_multiple_image;
                        break;
                }
                return layoutID;
            }

            @Override
            public int getItemViewType(int position, CategoryResult.ResultsBean resultsBean) {
                List<String> images = resultsBean.images;
                if (images == null || images.size() == 0) {
                    return TYPE_NO_IMAGE;
                }
                if (images.size() == 1) {
                    return TYPE_ONE_IMAGE;
                }
                return TYPE_MULTIPLE_IMAGE;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == SCROLL_STATE_IDLE) {
//                    //停止滚动
//                    Glide.with(getContext()).resumeRequests();
//                } else {
//                    Glide.with(getContext()).pauseRequests();
//                }
//            }
//        });
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
        mAdapter.setDatas(datas.results);
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
