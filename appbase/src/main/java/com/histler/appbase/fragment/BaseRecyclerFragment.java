package com.histler.appbase.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.histler.appbase.R;
import com.histler.appbase.adapter.BaseRecyclerAdapter;
import com.histler.appbase.adapter.OnItemClickListener;
import com.histler.appbase.adapter.OnItemLongClickListener;
import com.histler.appbase.adapter.viewholder.BaseViewHolder;
import com.histler.appbase.util.DividerItemDecoration;



public abstract class BaseRecyclerFragment<T, VIEW_HOLDER extends BaseViewHolder> extends BaseFragment {
    final private OnItemClickListener mOnClickListener
            = new OnItemClickListener() {
        public void onItemClick(View v, int position) {
            onRecyclerViewItemClick(v, position);
        }
    };
    final private OnItemLongClickListener mOnLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(View view, int position) {
            return onRecyclerViewItemLongClick(view, position);
        }
    };
    protected View mProgressBarHolder;
    protected SwipeRefreshLayout mListContainer;
    private boolean mIsRefreshing;
    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mIsRefreshing = true;
            BaseRecyclerFragment.this.onRefresh();
        }
    };
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private BaseRecyclerAdapter<T, VIEW_HOLDER> mAdapter;

    private void initList() {
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (root instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) root;
        } else {
            mRecyclerView = (RecyclerView) root.findViewById(android.R.id.list);
        }
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = getLayoutManager(root.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator = getItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        if (isNeedDivider()) {
            RecyclerView.ItemDecoration itemDecoration = getItemDecoration(root.getContext());
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    /**
     * @return true, if you want your list to have dividers, defined in {@link #getItemDecoration(Context)}
     */
    public boolean isNeedDivider() {
        return true;
    }

    /**
     * @return recyclerviews adapter
     */
    public BaseRecyclerAdapter<T, VIEW_HOLDER> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseRecyclerAdapter<T, VIEW_HOLDER> adapter) {
        mAdapter = adapter;
        if (mRecyclerView != null) {
            if (mAdapter != null) {
                mAdapter.setOnItemClickListener(mOnClickListener);
                mAdapter.setOnItemLongClickListener(mOnLongClickListener);
            }
            mRecyclerView.setAdapter(mAdapter);
        }
        checkAdapterForEmpty();
    }

    protected void checkAdapterForEmpty() {
        if (mEmptyView != null) {
            if (mAdapter == null || mAdapter.getItemCount() == 0) {
                mListContainer.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mListContainer.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Called on recyclerViews adapter item click.
     *
     * @param v        - view, that've been clicked
     * @param position position of view in adapter
     */
    public void onRecyclerViewItemClick(View v, int position) {
    }

    /**
     * Called on recyclerViews adapter item long click.
     *
     * @param view     - view, that've been clicked
     * @param position position of view in adapter
     * @return true, if handled.
     */
    public boolean onRecyclerViewItemLongClick(View view, int position) {
        return false;
    }

    /**
     * Called when the swipe gesture for refresh correctly triggers
     */
    public abstract void onRefresh();

    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    /**
     * Hides refreshing Progressbar
     */
    protected void setRefreshed() {
        mIsRefreshing = false;
        mProgressBarHolder.setVisibility(View.GONE);
        checkAdapterForEmpty();
        if (mListContainer != null) {
            mListContainer.setVisibility(View.VISIBLE);
        }
        if (mListContainer != null) {
            mListContainer.setRefreshing(false);
        }
    }

    protected void setHardRefreshing() {
        mIsRefreshing = true;
        if (mListContainer != null) {
            mListContainer.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mProgressBarHolder != null) {
            mProgressBarHolder.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This layout should have {@link RecyclerView} with  id=android.R.id.list
     *
     * @return returns views layout id.
     */

    protected int getLayoutId() {
        return R.layout.base_recycler_fragment;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    protected RecyclerView.ItemDecoration getItemDecoration(Context context) {
        if (getRecyclerView() != null) {
            RecyclerView.LayoutManager layoutManager = getRecyclerView().getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                return new DividerItemDecoration(context, ((LinearLayoutManager) layoutManager).getOrientation());
            }
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        mRecyclerView = (RecyclerView) root.findViewById(android.R.id.list);

        mListContainer = (SwipeRefreshLayout) root.findViewById(R.id.listContainer);
        if (mListContainer != null) {
            initListContainer(root);
        }

        mProgressBarHolder = root.findViewById(R.id.progressbar);

        mEmptyView = root.findViewById(R.id.empty_view);
        return root;
    }

    private void initListContainer(View view) {
        mListContainer.setOnRefreshListener(mOnRefreshListener);
        TypedValue typedValue = new TypedValue();
        TypedArray a = view.getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int colorAccent = a.getColor(0, 0);
        a.recycle();

        mListContainer.setColorSchemeColors(colorAccent);
    }

    /**
     * Attach to list view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        if (mAdapter != null) {
            setAdapter(mAdapter);
        }
    }
}
