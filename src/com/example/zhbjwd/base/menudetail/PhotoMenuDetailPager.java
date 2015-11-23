package com.example.zhbjwd.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbjwd.base.BaseMenuDetailPager;
/**
 * 菜单详情页-组图
 * */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

	public PhotoMenuDetailPager(Activity mActivity) {
		super(mActivity);

	}

	@Override
	public View initViews() {
		
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页-组图");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
