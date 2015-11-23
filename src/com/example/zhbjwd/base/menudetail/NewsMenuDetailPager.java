package com.example.zhbjwd.base.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhbjwd.MainActivity;
import com.example.zhbjwd.R;
import com.example.zhbjwd.base.BaseMenuDetailPager;
import com.example.zhbjwd.base.TabDetailPager;
import com.example.zhbjwd.domain.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
/**
 * 菜单详情页-新闻
 * */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{

	private ViewPager mViewPager;
	
	private ArrayList<TabDetailPager> mPagerList;
	
	private ArrayList<NewsTabData> mNewsTabData;// 页签网络数据

	private TabPageIndicator mIndicator;
	
	public NewsMenuDetailPager(Activity activity, ArrayList<NewsTabData> children) {
		super(activity);
		
		mNewsTabData = children;

	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_newsmenudetail_root);
		
		ViewUtils.inject(this, view);
		
		mIndicator = (TabPageIndicator) view
				.findViewById(R.id.tpi_newsmenudetail_indicator);	
		
		// mViewPager.setOnPageChangeListener(this);//注意:当viewpager和Indicator绑定时,
		// 滑动监听需要设置给Indicator而不是viewpager
		mIndicator.setOnPageChangeListener(this);
		
		return view;
	}
	
	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		
		// 初始化页签数据
		for (int i = 0; i < mNewsTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,mNewsTabData.get(i));
			
			mPagerList.add(pager);
		}
		
		mViewPager.setAdapter(new MenuDetailAdapter());	
		mIndicator.setViewPager(mViewPager);// 将viewpager和mIndicator关联起来，必须在viewpager设置完adapter后才能调用
			
	}

	// 跳转到下一个页面
	@OnClick(R.id.ibtn_newsmenudetail_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	class MenuDetailAdapter extends PagerAdapter{

		/**
		 * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}
		
		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagerList.get(position);			
			container.addView(pager.mRootView);
			pager.initData();// 初始化数据
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
	}

	@Override
	public void onPageSelected(int position) {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		
		if(position == 0){// 只有在第一个页面（北京），侧边栏才允许出来
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
}
