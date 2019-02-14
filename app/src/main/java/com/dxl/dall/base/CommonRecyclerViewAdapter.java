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

    public CommonRecyclerViewAdapter(Context context, int layoutID) {
        mContext = context;
        mList = new ArrayList<>();
        mLayoutID = layoutID;
    }

    public void addData(T t) {
        mList.add(t);
        notifyItemInserted(mList.size() - 1);
    }

    public void addDatas(List<T> ts){
        mList.addAll(ts);
        notifyItemRangeInserted(mList.size()-ts.size(), mList.size());
    }

    public void setDatas(List<T> ts) {
        mList.clear();
        mList.addAll(ts);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
        return new CommonRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder holder, int position) {
        T t = mList.get(position);
        bindData(holder, t);
    }

    /**
     * 数据绑定，子类实现
     * @param holder
     * @param t
     */
    protected abstract void bindData(CommonRecyclerViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
