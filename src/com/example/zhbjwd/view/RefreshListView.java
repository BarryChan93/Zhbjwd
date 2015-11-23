package com.example.zhbjwd.view;

import com.example.zhbjwd.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
/**
 * 
 * 下拉刷新的ListView
 * */
public class RefreshListView extends ListView {

	private View mHeaderView;
	
	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initHeaderViews();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderViews();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderViews();
	}
	
	/**
	 * 初始化头布局
	 * */
	private void initHeaderViews() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);
		
		mHeaderView.measure(0, 0);
		int mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
	}
	
	
}
