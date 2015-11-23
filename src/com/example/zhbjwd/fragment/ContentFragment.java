package com.example.zhbjwd.fragment;

import java.util.ArrayList;

import com.example.zhbjwd.R;
import com.example.zhbjwd.base.BasePager;
import com.example.zhbjwd.base.implement.GovAffairsPager;
import com.example.zhbjwd.base.implement.HomePager;
import com.example.zhbjwd.base.implement.NewsCenterPager;
import com.example.zhbjwd.base.implement.SettingPager;
import com.example.zhbjwd.base.implement.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**
 * 主页内容
 * */
public class ContentFragment extends BaseFragment {
	
	@ViewInject(R.id.rg_content_group)
	private RadioGroup rg_content_group;
	
	@ViewInject(R.id.vp_content_title)
	private ViewPager mViewPager;
	
	private ArrayList<BasePager> mPagerList;
	
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);	
		//rg_content_group = (RadioGroup) view.findViewById(R.id.rg_content_group);
		ViewUtils.inject(this, view);// 注入view和事件
		return view;
	}
	
	@Override
	public void initData() {
		rg_content_group.check(R.id.rb_content_home);// 默认勾选首页
		
		// 初始化5个子页面
		mPagerList = new ArrayList<BasePager>();
/*		for (int i = 0; i < 5; i++) {
			BasePager pager = new BasePager(mActivity);
			mPagerList.add(pager);
		}*/
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovAffairsPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));
				
		mViewPager.setAdapter(new ContentAdapter());
		
		// 监听RadioGroup的选择事件
		rg_content_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_content_home:
					mViewPager.setCurrentItem(0, false);// 设置当前页面，false表示去掉切换页面的动画
					break;
				case R.id.rb_content_news:
					mViewPager.setCurrentItem(1, false);// 设置当前页面
					break;
				case R.id.rb_content_smart:
					mViewPager.setCurrentItem(2, false);// 设置当前页面
					break;
				case R.id.rb_content_gov:
					mViewPager.setCurrentItem(3, false);// 设置当前页面
					break;
				case R.id.rb_content_setting:
					mViewPager.setCurrentItem(4, false);// 设置当前页面
					break;
				default:
					break;
				}		
			}
		});
		// 监听ViewPager的页面改变事件
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// 为了达到开启关闭侧边栏的目的
			@Override
			public void onPageSelected(int position) {
				mPagerList.get(position).initData();// 获取当前被选中的页面，初始化该页面数据
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		mPagerList.get(0).initData();// 初始化首页数据
	}
	
	class ContentAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			
			container.addView(pager.mRootView);
			//pager.initData();// 初始化数据...不要放在此处初始化数据, 否则会预加载下一个页面（影响侧边栏的关闭）
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}
	/**
	 * 获取新闻中心页面
	 * */
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager) mPagerList.get(1);
	}

}
