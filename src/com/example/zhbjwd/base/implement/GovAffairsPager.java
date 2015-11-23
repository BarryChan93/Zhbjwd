package com.example.zhbjwd.base.implement;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zhbjwd.base.BasePager;

/**
 * 政务
 * */
public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {	
		System.out.println("初始化政务数据....");
		
		tvTitle.setText("人口管理");// 修改标题
		setSlidingMenuEnable(true);// 开启侧边栏
		
		TextView text = new TextView(mActivity);
		text.setText("政务");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		
		// 向FrameLayout中动态添加布局
		flContent.addView(text);
	}

}
