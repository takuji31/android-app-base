package com.github.takuji31.appbase.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;

public class PagingListView extends ListView implements
		AbsListView.OnScrollListener {

	private static final int POST_DELAY_TIME = 50;

	public static interface OnPagingListener {
		public void onScrollStart(int page);

		public void onScrollFinish(int page);

		public void onNextListLoad(int page);
	}

	private int mPage;
	private int mScrollDuration = 400;
	private boolean mFlinged;
	private OnPagingListener mListener;
	private OnGlobalLayoutListener mLayoutListener = new OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			if (mViewHeight == 0) {
				postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mViewHeight = getHeight();
						invalidateViews();
					}
				}, POST_DELAY_TIME);
			}
		}
	};
	
	private Handler mHandler = new Handler();
	
	private Runnable mIdleTask = new Runnable() {
		public void run() {
			int savedPosition = getFirstVisiblePosition();
			View firstVisibleView = getChildAt(0);

			if (firstVisibleView.getHeight() / 2.0 < Math.abs(firstVisibleView
					.getTop())) {
				mPage = savedPosition;
				smoothScrollBy(
						mViewHeight
								- Math.abs(firstVisibleView
										.getTop() - 1),
						mScrollDuration);
			} else {
				mPage = savedPosition;
				smoothScrollBy(firstVisibleView.getTop(),
						mScrollDuration);
			}

			if (mListener != null) {
				mListener.onScrollFinish(mPage);
			}
		}
	};
	
	private Runnable mFlingTask = new Runnable() {
		public void run() {
			int savedPosition = getFirstVisiblePosition();
			View firstVisibleView = getChildAt(0);

			Adapter adapter = getAdapter();
			int totalPage = adapter != null ? adapter.getCount() : 0;
			if (savedPosition < mPage) {
				if (mPage + 1 == totalPage) {
					int firstVisibleItem = mPage
							- getFirstVisiblePosition();
					View lastView = getChildAt(firstVisibleItem);
					if (lastView != null && lastView.getTop() > 0) {
						scrollPrevPage();
					}
				} else {
					scrollPrevPage();
				}
			} else if (0 < savedPosition
					|| (0 == savedPosition && 0 > firstVisibleView.getTop())) {
				if (mPage != totalPage - 1) {
					scrollNextPage();
				}
			}

			if (mListener != null) {
				mListener.onScrollStart(mPage);
			}
		}
	};
	
	int mViewHeight;

	public PagingListView(Context context) {
		super(context);
		init();
	}

	public PagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void setOnPagingListener(OnPagingListener listener) {
		mListener = listener;
	}

	public void setPage(int page) {
		mPage = page;
	}

	public int getPage() {
		return mPage;
	}

	public void scrollNextPage() {
		View firstVisibleView = getChildAt(0);
		mPage += 1;
		smoothScrollBy(mViewHeight - Math.abs(firstVisibleView.getTop()) - 1,
				mScrollDuration);
	}

	public void scrollPrevPage() {
		View firstVisibleView = getChildAt(0);
		mPage -= 1;
		smoothScrollBy(firstVisibleView.getTop(), mScrollDuration);
	}

	private void init() {
		setOnScrollListener(this);
		ViewTreeObserver observer = getViewTreeObserver();
		observer.addOnGlobalLayoutListener(mLayoutListener);
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void addOnGlobalLayoutListener(OnGlobalLayoutListener listener) {
		ViewTreeObserver observer = getViewTreeObserver();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			observer.removeOnGlobalLayoutListener(mLayoutListener);
		} else {
			observer.removeGlobalOnLayoutListener(mLayoutListener);
		}
		observer.addOnGlobalLayoutListener(listener);
		observer.addOnGlobalLayoutListener(mLayoutListener);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {

		case OnScrollListener.SCROLL_STATE_IDLE:
			if (mFlinged) {
				Log.d(VIEW_LOG_TAG, "IDLE_FLING");
				mFlinged = false;
				if (mListener != null) {
					mListener.onScrollFinish(mPage);
				}
			} else {
				Log.d(VIEW_LOG_TAG, "IDLE");
				mHandler.removeCallbacks(mIdleTask);
				mHandler.postDelayed(mIdleTask, POST_DELAY_TIME);
			}

			break;

		case OnScrollListener.SCROLL_STATE_FLING:
			Log.d(VIEW_LOG_TAG, "STATE_FLING");
			mFlinged = true;
			mHandler.removeCallbacks(mFlingTask);
			mHandler.postDelayed(mFlingTask, POST_DELAY_TIME);

			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mListener != null) {
			mListener.onNextListLoad(mPage);
		}
	}
}
