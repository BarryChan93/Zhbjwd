package com.example.zhbjwd.base.implement;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhbjwd.MainActivity;
import com.example.zhbjwd.base.BaseMenuDetailPager;
import com.example.zhbjwd.base.BasePager;
import com.example.zhbjwd.base.menudetail.InteractMenuDetailPager;
import com.example.zhbjwd.base.menudetail.NewsMenuDetailPager;
import com.example.zhbjwd.base.menudetail.PhotoMenuDetailPager;
import com.example.zhbjwd.base.menudetail.TopicMenuDetailPager;
import com.example.zhbjwd.domain.NewsData;
import com.example.zhbjwd.domain.NewsData.NewsMenuData;
import com.example.zhbjwd.fragment.LeftMenuFragment;
import com.example.zhbjwd.global.GlobalContants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 新闻中心
 * */
public class NewsCenterPager extends BasePager {

	private ArrayList<BaseMenuDetailPager> mpagers;// 4个菜单详情页的集合
	
	private NewsData mNewsData;
	
	public NewsCenterPager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		System.out.println("初始化新闻中心数据....");
		
		setSlidingMenuEnable(true);// 开启侧边栏

		getDataFromServer();
		
		
	}
	
	/**
	 * 从服务器获取数据
	 * */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		
		// 使用xutils发送请求
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {
					// 访问成功,在主线程运行
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println("返回结果：" +result);
						
						parseData(result);
					}
					// 访问失败
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, 1).show();
						error.printStackTrace();
					}
				});
	}

	/**
	 * 解析网络数据
	 * */
	protected void parseData(String result) {
		Gson gson = new Gson();	
		mNewsData = gson.fromJson(result, NewsData.class);
		System.out.println("解析结果:" + mNewsData);
		// 刷新侧边栏数据
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();	
		leftMenuFragment.setMenuData(mNewsData);
		
		// 准备4个菜单详情页
		mpagers = new ArrayList<BaseMenuDetailPager>();
		
		mpagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mpagers.add(new TopicMenuDetailPager(mActivity));
		mpagers.add(new PhotoMenuDetailPager(mActivity));
		mpagers.add(new InteractMenuDetailPager(mActivity));
		
		setCurrentMenuDetailPager(0);// 设置菜单详情页
	}
	/**
	 * 设置当前菜单详情页
	 * */
	public void setCurrentMenuDetailPager(int position){
		BaseMenuDetailPager pager = mpagers.get(position);// 获取当前要显示的菜单详情页
		flContent.removeAllViews();// 清除之前的布局
		flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局(FrameLayout)
		
		// 设置当前页的标题
		NewsMenuData menuData = mNewsData.data.get(position);
		tvTitle.setText(menuData.title);
		
		pager.initData();// 初始化当前页面的数据
	}


}
