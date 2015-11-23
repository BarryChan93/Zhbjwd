package com.example.zhbjwd.base;

import java.util.ArrayList;

import com.example.zhbjwd.R;
import com.example.zhbjwd.domain.NewsData.NewsTabData;
import com.example.zhbjwd.domain.TabData;
import com.example.zhbjwd.domain.TabData.TabNewsData;
import com.example.zhbjwd.domain.TabData.TabTopNewsData;
import com.example.zhbjwd.global.GlobalContants;
import com.example.zhbjwd.view.RefreshListView;
import com.example.zhbjwd.view.TopNewslViewPager;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 页签详情页
 * 
 * */
public class TabDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{

	private TextView tvText;

	NewsTabData mTabData;

	private String mUrl;

	private TabData mTabDetailData;

	@ViewInject(R.id.vp_tabdetail_news)
	private TopNewslViewPager mViewPager;
	
	@ViewInject(R.id.tv_tabdetail_title)
	private TextView tvTitle;// 头条新闻的标题
	
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator;// 头条新闻的位置指示器
	
	@ViewInject(R.id.lv_tabdetail_list)
	private RefreshListView lvlist;// 新闻列表

	private ArrayList<TabTopNewsData> mTopNewsList;// 头条新闻数据集合

	private ArrayList<TabNewsData> mNewsList;// 新闻数据集合

	private NewsAdaptar mNewsAdaptar;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;

		mUrl = GlobalContants.SERVER_URL + mTabData.url;
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);// 加载头布局
		
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, headerView);
		
		lvlist.addHeaderView(headerView);// // 将头条新闻以头布局的形式加给listview
		
		return view;
	}

	@Override
	public void initData() {
		getDataFromServer();
	}
	
	/**
	 * 从服务器获取数据
	 * */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("页签详情页返回结果：" + result);

				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 1).show();
				error.printStackTrace();
			}
		});
	}

	/**
	 * 解析数据
	 * */
	protected void parseData(String result) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		System.out.println("页签详情解析：" + mTabDetailData);
		
		mTopNewsList = mTabDetailData.data.topnews;

		
		mNewsList = mTabDetailData.data.news;
		if(mTopNewsList !=null){
			mViewPager.setAdapter(new TopNewsAdapter());
			mIndicator.setViewPager(mViewPager);
			mIndicator.setSnap(true);// 支持快照显示
			mIndicator.setOnPageChangeListener(this);
			
			mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点
			
			tvTitle.setText(mTopNewsList.get(0).title);
		}

		
		if(mNewsList != null){
			mNewsAdaptar = new NewsAdaptar();
			lvlist.setAdapter(mNewsAdaptar);
		}

	}

	/**
	 * 
	 * 头条新闻适配器
	 * */
	class TopNewsAdapter extends PagerAdapter {

		private BitmapUtils utils;

		public TopNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {
			return mTabDetailData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			image.setImageResource(R.drawable.topnews_item_default);
			image.setScaleType(ScaleType.FIT_XY);// 基于控件大小去填充图片

			TabTopNewsData tabTopNewsData = mTopNewsList.get(position);
			utils.display(image, tabTopNewsData.topimage);// 传递imageView对象和图片地址

			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	
	/**
	 * 新闻列表的适配器
	 * */
	class NewsAdaptar extends BaseAdapter{

		
		private BitmapUtils utils;

		public  NewsAdaptar() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);// 加载默认图片
		}
		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_news_item, null);
				holder = new ViewHolder();
				holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
				holder.tvDate = (TextView) convertView.findViewById(R.id.tv_news_date);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			TabNewsData item = getItem(position);
			holder.tvTitle.setText(item.title);
			holder.tvDate.setText(item.pubdate);
			
			utils.display(holder.ivPic, item.listimage);// 加载图片
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		public ImageView ivPic;
		public TextView tvTitle;
		public TextView tvDate;
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {		
	}

	@Override
	public void onPageSelected(int position) {
		TabTopNewsData tabTopNewsData = mTopNewsList.get(position);
		tvTitle.setText(tabTopNewsData.title);
	}

	@Override
	public void onPageScrollStateChanged(int state) {		
	}

}
