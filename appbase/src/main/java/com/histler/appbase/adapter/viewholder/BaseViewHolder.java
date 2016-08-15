package com.histler.appbase.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.histler.appbase.adapter.OnItemClickListener;
import com.histler.appbase.adapter.OnItemLongClickListener;

import butterknife.ButterKnife;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnItemClickListener mOnClickListener;
    private OnItemLongClickListener mOnLongClickListener;

    public BaseViewHolder(View itemView) {
        this(itemView, null);
    }

    public BaseViewHolder(View itemView, OnItemClickListener clickListener) {
        this(itemView, clickListener, null);
    }

    public BaseViewHolder(View itemView, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        super(itemView);
        mOnClickListener = clickListener;
        mOnLongClickListener = longClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        initView(itemView);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    public void setOnItemLongCLickListener(OnItemLongClickListener longClickListener) {
        mOnLongClickListener = longClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(itemView)) {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    protected void initView(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    @Override
    public boolean onLongClick(View view) {
        return view.equals(itemView) && mOnLongClickListener != null && mOnLongClickListener.onItemLongClick(view, getLayoutPosition());
    }
}
