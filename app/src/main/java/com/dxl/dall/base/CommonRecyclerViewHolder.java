package com.dxl.dall.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * @author dxl
 * @date 2019/2/14 10:10
 */
public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public CommonRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setTextView(@IdRes int viewId, String text) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setOnClickListener(int position, @IdRes int viewId, ListenerWithPosition.OnClickWithPositionListener listener) {
        ListenerWithPosition listenerWithPosition = new ListenerWithPosition(position, listener);
        getView(viewId).setOnClickListener(listenerWithPosition);
    }

    public void setOnClickListener(int position, ListenerWithPosition.OnClickWithPositionListener listener) {
        ListenerWithPosition listenerWithPosition = new ListenerWithPosition(position, listener);
        itemView.setOnClickListener(listenerWithPosition);
    }


    static class ListenerWithPosition implements View.OnClickListener {

        public ListenerWithPosition(int position, OnClickWithPositionListener clickWithPositionListener) {
            mPosition = position;
            mClickWithPositionListener = clickWithPositionListener;
        }

        int mPosition;

        public void setClickWithPositionListener(OnClickWithPositionListener clickWithPositionListener) {
            mClickWithPositionListener = clickWithPositionListener;
        }

        OnClickWithPositionListener mClickWithPositionListener;

        interface OnClickWithPositionListener {
            void onClick(View v, int position);
        }

        @Override
        public void onClick(View v) {
            if (mClickWithPositionListener != null) {
                mClickWithPositionListener.onClick(v, mPosition);
            }
        }
    }


}
