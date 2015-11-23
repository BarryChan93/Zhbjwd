package com.example.zhbjwd.fragment;

import java.util.ArrayList;

import com.example.zhbjwd.MainActivity;
import com.example.zhbjwd.R;
import com.example.zhbjwd.base.implement.NewsCenterPager;
import com.example.zhbjwd.domain.NewsData;
import com.example.zhbjwd.domain.NewsData.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 侧边栏
 * */
public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_leftmenu_list)
	private ListView lv_leftmenu_list;
	
	private ArrayList<NewsMenuData> mMenuList;
	
	private int mCurrentPos;// 当前被点击的菜单项

	private MenuAdapter mAdapter;
	
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);
		
		
		return view;
	}
	
	@Override
	public void initData() {
		lv_leftmenu_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos = position;
				
				mAdapter.notifyDataSetChanged();
				
				setCurrentMenuDetailPager(position);
				
				toggleSlidingMenu();// 隐藏
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
	 * 设置当前菜单详情页
	 * */
	protected void setCurrentMenuDetailPager(int position) {
		MainActivity mainUi = (MainActivity) mActivity;
		ContentFragment fragment = mainUi.getContentFragment();// 获取主页面fragment
		NewsCenterPager pager = fragment.getNewsCenterPager();// 获取新闻中心页面
		pager.setCurrentMenuDetailPager(position);// 设置当前菜单详情页
	}

	// 设置网络数据
	public void setMenuData(NewsData data){
		System.out.println("侧边栏拿到数据啦:" + data);
		mMenuList = data.data;
		mAdapter = new MenuAdapter();
		lv_leftmenu_list.setAdapter(mAdapter);
	}
	/**
	 * 侧边栏数据适配器
	 * */
	class MenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
			TextView tv_listmenu_title = (TextView) view.findViewById(R.id.tv_listmenu_title);		
			NewsMenuData newsMenuData = getItem(position);
			
			tv_listmenu_title.setText(newsMenuData.title);
						
			if(mCurrentPos == position){// 判断当前绘制的View是否被选中
				// 显示红色
				tv_listmenu_title.setEnabled(true);
			}else {
				// 显示白色
				tv_listmenu_title.setEnabled(false);
			}
			return view;
		}		
	}
}
