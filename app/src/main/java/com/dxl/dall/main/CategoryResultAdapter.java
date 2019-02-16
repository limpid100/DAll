package com.dxl.dall.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    /**
     * 多布局
     */
    public static final int TYPE_NO_IMAGE = 0;
    public static final int TYPE_ONE_IMAGE = 1;
    public static final int TYPE_MULTIPLE_IMAGE = 2;

    /**
     * 默认布局，单布局
     * @param context
     */
    public CategoryResultAdapter(Context context) {
        super(context, R.layout.category_result_item_view_one_image);
    }

    /**
     * 多布局支持
     * @param context
     * @param multipleTypeSupport
     */
    public CategoryResultAdapter(Context context, MuiltipleTypeSupport<CategoryResult.ResultsBean> multipleTypeSupport) {
        super(context, multipleTypeSupport);
    }

    @Override
    protected void bindData(CommonRecyclerViewHolder holder, CategoryResult.ResultsBean resultsBean, int position) {
        holder.setTextView(R.id.tv_desc, resultsBean.desc);
        holder.setTextView(R.id.tv_source, resultsBean.source);
        holder.setTextView(R.id.tv_date, resultsBean.createdAt.substring(0, 10));

        int itemViewType = TYPE_ONE_IMAGE;
        if (mMuiltipleTypeSupport != null) {
            itemViewType = mMuiltipleTypeSupport.getItemViewType(position, resultsBean);
        }
        List<String> images = resultsBean.images;
        switch (itemViewType) {
            case TYPE_ONE_IMAGE:
                Glide.with(mContext).load(images.get(0)).into((ImageView) holder.getView(R.id.image1));
                break;
            case TYPE_MULTIPLE_IMAGE:
                Glide.with(mContext).load(images.get(0)).into((ImageView) holder.getView(R.id.image1));
                if (images.size() > 1) {
                    Glide.with(mContext).load(images.get(0)).into(((ImageView) holder.getView(R.id.image2)));
                }
                if (images.size() > 2) {
                    Glide.with(mContext).load(images.get(0)).into(((ImageView) holder.getView(R.id.image3)));
                }
                break;
            default:
        }
        holder.setOnClickListener(position, new CommonRecyclerViewHolder.ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position) {
                //todo 跳转webview
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
