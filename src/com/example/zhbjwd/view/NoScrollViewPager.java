package com.example.zhbjwd.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
/**
 * 不能左右划的ViewPager
 * */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	// 表示事件是否拦截，返回false表示不拦截，可以使嵌套在内部的viewpager响应左右划的事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
	
	/**
	 * 重写onTouchEvent 事件，什么都不用做
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;// 返回false，把滑动时间禁掉了
	}
	
	

}
