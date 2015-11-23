package com.example.zhbjwd.base.implement;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zhbjwd.base.BasePager;

/**
 * 智慧服务
 * */
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		System.out.println("初始化智慧服务数据....");
		
		tvTitle.setText("智慧服务");// 修改标题
		setSlidingMenuEnable(true);// 开启侧边栏
		
		TextView text = new TextView(mActivity);
		text.setText("生活");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		
		// 向FrameLayout中动态添加布局
		flContent.addView(text);
	}

}
