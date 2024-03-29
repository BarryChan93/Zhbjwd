package com.example.zhbjwd.base;

import com.example.zhbjwd.MainActivity;
import com.example.zhbjwd.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 主页下5个子页面的基类
 * */
public class BasePager {

	public Activity mActivity;

	public View mRootView;// 布局对象
	
	public TextView tvTitle;// 标题对象
	
	public FrameLayout flContent;// 内容
	
	public ImageButton btnMenu;// 菜单按钮
	
	public BasePager(Activity activity) {
		mActivity = activity;
		
		initViews();
	}
	
	/**
	 * 初始化布局
	 * */
	public void initViews()
	{
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		
		tvTitle = (TextView) mRootView.findViewById(R.id.tv_basepager_title);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_basepager_content);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_basepager_menu);
		
		btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}
	
	/**
	 * 切换SlidingMenu的状态
	 * */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();// 切换状态，显示时隐藏，隐藏时显示
	}
	
	/**
	 * 初始化数据
	 * */
	public void initData() {

	}
	/**
	 * 设置侧边栏开启或关闭
	 * */
	public void setSlidingMenuEnable(boolean enable) {
		MainActivity mainUi = (MainActivity) mActivity;
		
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
			
	}
	
}
