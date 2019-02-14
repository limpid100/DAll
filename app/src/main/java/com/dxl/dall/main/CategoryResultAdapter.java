package com.dxl.dall.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dxl.dall.R;
import com.dxl.dall.entity.CategoryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dxl
 * @date 2019/2/14 7:53
 */
public class CategoryResultAdapter extends RecyclerView.Adapter<CategoryResultAdapter.CategoryResultViewHolder> {

    private Context mContext;

    private List<CategoryResult.ResultsBean> mDatas;

    public CategoryResultAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }


    public void setDatas(CategoryResult categoryResult) {
        mDatas = categoryResult.results;
        notifyDataSetChanged();
    }

    public void addDatas(CategoryResult categoryResult) {
        mDatas.addAll(categoryResult.results);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_result_item_view, viewGroup, false);
        return new CategoryResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryResultViewHolder categoryResultViewHolder, int i) {
        CategoryResult.ResultsBean resultsBean = mDatas.get(i);
        List<String> images = resultsBean.images;
        if (images != null && images.size() > 0) {
            Glide.with(mContext).load(images.get(0)).into(categoryResultViewHolder.mImageView);
        }
        categoryResultViewHolder.mTvDesc.setText(resultsBean.desc);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CategoryResultViewHolder extends RecyclerView.ViewHolder{

        TextView mTvDesc;
        ImageView mImageView;

        public CategoryResultViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDesc = itemView.findViewById(R.id.tv_desc);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
