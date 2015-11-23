package com.example.zhbjwd;

import java.util.ArrayList;

import com.example.zhbjwd.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {

	private static final int[] mImageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	private ViewPager vp_guide_root;

	private LinearLayout ll_guide_pointgroup;// 引导圆点的父控件

	private int mPointWidth;// 圆点间的距离

	private View view_guide_redpoint;// 小红点

	private Button btn_guide_start;// 开始体验按钮

	private ArrayList<ImageView> mImageViewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
		setContentView(R.layout.activity_guide);
		vp_guide_root = (ViewPager) findViewById(R.id.vp_guide_root);
		view_guide_redpoint = findViewById(R.id.view_guide_redpoint);
		ll_guide_pointgroup = (LinearLayout) findViewById(R.id.ll_guide_pointgroup);
		btn_guide_start = (Button) findViewById(R.id.btn_guide_start);

		btn_guide_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 更新sp, 表示已经展示了新手引导
				PrefUtils.setBoolean(GuideActivity.this,
						"is_user_guide_showed", true);

				// 跳转到主页面
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
		initViews();

		vp_guide_root.setAdapter(new GuideAdapter());

		vp_guide_root.setOnPageChangeListener(new GuidePagerListener());
	}

	/**
	 * 初始化界面
	 * */
	private void initViews() {
		mImageViewList = new ArrayList<ImageView>();

		// 初始化引导页的3个页面
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);// 设置引导页背景
			mImageViewList.add(image);
		}

		// 初始化引导页的小圆点
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);// 设置圆点宽高
			if (i > 0) {
				params.leftMargin = 10;// 设置圆点间隔
			}

			point.setLayoutParams(params);// 设置圆点的大小

			ll_guide_pointgroup.addView(point);// 将圆点添加给线性布局
		}

		// 获取视图树， 对layout结束事件进行监听
		ll_guide_pointgroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						System.out.println("layout 结束");
						ll_guide_pointgroup.getViewTreeObserver()
								.removeOnGlobalLayoutListener(this);
						mPointWidth = ll_guide_pointgroup.getChildAt(1)
								.getLeft()
								- ll_guide_pointgroup.getChildAt(0).getLeft();
						System.out.println("圆点距离： " + mPointWidth);
					}
				});

	}

	/**
	 * ViewPager数据适配器
	 * */
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	/**
	 * viewPager 的滑动监听
	 * */
	class GuidePagerListener implements OnPageChangeListener {
		// 滑动事件
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			System.out.println("当前位置:" + position + ";百分比:" + positionOffset
					+ ";移动距离:" + positionOffsetPixels);
			int len = (int) (mPointWidth * positionOffset) + position
					* mPointWidth;
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_guide_redpoint
					.getLayoutParams();// 获取当前红点的布局参数
			params.leftMargin = len;// 设置红点的左边距

			view_guide_redpoint.setLayoutParams(params);// 重新给小红点设置布局参数

		}

		// 某个页面被选中
		@Override
		public void onPageSelected(int position) {
			if (position == mImageIds.length - 1) {// 最后一个页面
				btn_guide_start.setVisibility(View.VISIBLE);// 显示开始体验的按钮
			} else {
				btn_guide_start.setVisibility(View.INVISIBLE);
			}
		}

		// 滑动状态发生改变
		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}
}
