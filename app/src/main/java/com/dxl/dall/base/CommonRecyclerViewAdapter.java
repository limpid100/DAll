package com.dxl.dall.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dxl
 * @date 2019/2/14 10:10
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    private List<T> mList;
    protected Context mContext;
    private int mLayoutID;
    protected MuiltipleTypeSupport<T> mMuiltipleTypeSupport;

    public CommonRecyclerViewAdapter(Context context, int layoutID) {
        mContext = context;
        mList = new ArrayList<>();
        mLayoutID = layoutID;
    }

    public CommonRecyclerViewAdapter(Context context, MuiltipleTypeSupport<T> muiltipleTypeSupport) {
        mContext = context;
        mList = new ArrayList<>();
        this.mMuiltipleTypeSupport = muiltipleTypeSupport;
    }

    public void addData(T t) {
        mList.add(t);
        notifyItemInserted(mList.size() - 1);
    }

    public void addDatas(List<T> ts) {
        mList.addAll(ts);
        notifyItemRangeInserted(mList.size() - ts.size(), mList.size());
    }

    public void setDatas(List<T> ts) {
        mList.clear();
        mList.addAll(ts);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mList;
    }


    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (mMuiltipleTypeSupport != null) {
            view = LayoutInflater.from(mContext).inflate(mMuiltipleTypeSupport.getLayoutID(viewType), parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
        }
        return new CommonRecyclerViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMuiltipleTypeSupport != null) {
            return mMuiltipleTypeSupport.getItemViewType(position, mList.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder holder, int position) {
        T t = mList.get(position);
        bindData(holder, t, position);
    }

    /**
     * 数据绑定，子类实现
     *
     * @param holder
     * @param t
     */
    protected abstract void bindData(CommonRecyclerViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * 多布局支持
     * https://blog.csdn.net/lmj623565791/article/details/51118836
     *
     * @param <T>
     */
    public interface MuiltipleTypeSupport<T> {
        int getLayoutID(int viewType);

        int getItemViewType(int position, T t);
    }
}
