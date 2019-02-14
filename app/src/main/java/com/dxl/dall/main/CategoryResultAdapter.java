package com.dxl.dall.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dxl.dall.R;
import com.dxl.dall.base.CommonRecyclerViewAdapter;
import com.dxl.dall.base.CommonRecyclerViewHolder;
import com.dxl.dall.entity.CategoryResult;

import java.util.List;

/**
 * @author dxl
 * @date 2019/2/14 7:53
 */
public class CategoryResultAdapter extends CommonRecyclerViewAdapter<CategoryResult.ResultsBean> {


    public CategoryResultAdapter(Context context, int layoutID) {
        super(context, layoutID);
    }

    @Override
    protected void bindData(CommonRecyclerViewHolder holder, CategoryResult.ResultsBean resultsBean) {
        holder.setTextView(R.id.tv_desc, resultsBean.desc);
        List<String> images = resultsBean.images;
        ImageView imageView = holder.getView(R.id.image_view);
        if (images != null && images.size() > 0) {
            if (imageView.getVisibility() != View.VISIBLE) {
                imageView.setVisibility(View.VISIBLE);
            }
            Glide.with(mContext).load(images.get(0)).into(imageView);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

}
