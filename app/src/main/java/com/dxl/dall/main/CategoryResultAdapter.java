package com.dxl.dall.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dxl.dall.R;
import com.dxl.dall.base.BaseApplication;
import com.dxl.dall.base.CommonRecyclerViewAdapter;
import com.dxl.dall.base.CommonRecyclerViewHolder;
import com.dxl.dall.entity.CategoryResult;

import java.util.List;

import static com.dxl.dall.database.SqliteDateBaseHelper.CATEGORY_RESULT_TABLE_NAME;

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
     *
     * @param context
     */
    public CategoryResultAdapter(Context context) {
        super(context, R.layout.category_result_item_view_one_image);
    }

    /**
     * 多布局支持
     *
     * @param context
     * @param multipleTypeSupport
     */
    public CategoryResultAdapter(Context context, MuiltipleTypeSupport<CategoryResult.ResultsBean> multipleTypeSupport) {
        super(context, multipleTypeSupport);
    }

    @Override
    protected void bindData(final CommonRecyclerViewHolder holder, CategoryResult.ResultsBean resultsBean, int position) {
        holder.setTextView(R.id.tv_desc, resultsBean.desc);
        holder.setTextView(R.id.tv_source, resultsBean.source);
        holder.setTextView(R.id.tv_date, resultsBean.createdAt.substring(0, 10));
        boolean isRead = resultsBean.isread == 1;
        TextView tvDesc = holder.getView(R.id.tv_desc);
        if (isRead) {
            //文章已读
            tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.main_gray));
        }else {
            tvDesc.setTextColor(ContextCompat.getColor(mContext, R.color.main_black));
        }

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
        //点击删除按钮
        holder.setOnClickListener(position, R.id.delete_view, new CommonRecyclerViewHolder.ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position) {
                deleteItemAtPosition(position);
            }
        });
        holder.setOnClickListener(position, new CommonRecyclerViewHolder.ListenerWithPosition.OnClickWithPositionListener() {
            @Override
            public void onClick(View v, int position) {
                //todo 跳转webview
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                saveItemRead(position);
            }
        });
    }

    /**
     * item被点击了，标记为已读，更新界面，并保存数据库
     *
     * @param position
     */
    private void saveItemRead(int position) {
        final CategoryResult.ResultsBean resultsBean = getDatas().get(position);
        resultsBean.isread = 1;
        notifyItemChanged(position);
        BaseApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                BaseApplication.getInstance().getDB()
                        .execSQL("UPDATE " + CATEGORY_RESULT_TABLE_NAME + " SET ISREAD = 1 WHERE ID = ? ",
                                new String[]{resultsBean._id});
            }
        });
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    private void deleteItemAtPosition(int position) {
        List<CategoryResult.ResultsBean> datas = getDatas();
        final CategoryResult.ResultsBean originData = datas.get(position);
        datas.remove(position);
        notifyItemRemoved(position);
        //需要刷新一下，https://www.jianshu.com/p/9806917d3d99，https://blog.csdn.net/qq_14962891/article/details/54583814
        notifyItemRangeChanged(position, datas.size() - position);
        Toast.makeText(mContext, "删除成功~~", Toast.LENGTH_SHORT).show();
        BaseApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = BaseApplication.getInstance().getDB();
                db.execSQL("UPDATE " + CATEGORY_RESULT_TABLE_NAME + " SET ISDELETE = 1 WHERE ID = ? ", new String[]{originData._id});
            }
        });

    }

}
