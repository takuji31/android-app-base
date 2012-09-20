package com.github.takuji31.appbase.widget;

import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.widget.AbsListView.LayoutParams;

public abstract class PagingArrayListAdapter<T> extends SimpleListAdapter<T> {
	
	public PagingArrayListAdapter(Context context, ArrayList<T> list) {
		super(context, list);
	}
	
	@Override
	public View createView(int position, T item, View v, View parent) {
		int height = parent.getHeight();
		View createdView = createNonLayoutedView(position, item, v);
		if (height != 0) {
			LayoutParams params = (LayoutParams) createdView.getLayoutParams();
			if (params == null) {
				params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
			} else {
				params.height = height;
			}
			createdView.setMinimumHeight(height);
		}
		return createdView;
	}
	
	public abstract View createNonLayoutedView(int position, T item, View v);

}
