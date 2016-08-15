package com.histler.appbase.adapter;

import android.support.v7.widget.RecyclerView;

import com.histler.appbase.adapter.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T, R extends BaseViewHolder> extends RecyclerView.Adapter<R> {
    protected List<T> mData;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
    }

    public void setData(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(T item) {
        int position = mData.indexOf(item);
        if (position >= 0) {
            removeItem(position);
        }
    }

    public void addItem(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void addItem(T item) {
        addItem(getItemCount(), item);
    }

    public void addItems(int position, List<T> items) {
        mData.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public void addItems(List<T> items) {
        addItems(getItemCount(), items);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        mOnItemClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onLongClickListener) {
        mOnItemLongClickListener = onLongClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getItems() {
        return mData;
    }
}
