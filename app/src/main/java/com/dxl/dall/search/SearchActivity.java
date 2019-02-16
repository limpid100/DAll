package com.dxl.dall.search;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.dxl.dall.R;
import com.dxl.dall.base.BaseActivity;
import com.dxl.dall.entity.CategoryResult;
import com.dxl.dall.main.CategoryResultAdapter;
import com.dxl.dall.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dxl
 * @date 2019/2/15 13:36
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.ISearchView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_search)
    AppCompatEditText etSearch;
    @BindView(R.id.iv_clear)
    AppCompatImageView mClearImage;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private CategoryResultAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mClearImage.setVisibility(View.VISIBLE);
                } else {
                    mClearImage.setVisibility(View.GONE);

                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CategoryResultAdapter(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);


    }

    @OnClick(R.id.iv_clear)
    void clearClicked(){
        KeyboardUtils.showSoftInput(this, etSearch);
        etSearch.setText("");
        mAdapter.getDatas().clear();
        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.iv_search)
    void searhImageClicked() {
        search();
    }

    private void search() {
        KeyboardUtils.hideSoftInput(this);
        mPresenter.loadCategoryResult(etSearch.getText().toString(), true);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onSuccess(CategoryResult datas) {
        mAdapter.setDatas(datas.results);
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }
}
