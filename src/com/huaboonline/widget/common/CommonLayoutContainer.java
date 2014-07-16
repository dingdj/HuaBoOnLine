/**
 * @author dingdj
 * Date:2014-7-8上午10:42:48
 *
 */
package com.huaboonline.widget.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.huaboonline.R;
import com.ddj.commonkit.android.system.ScreenUtil;

/**
 * @author dingdj Date:2014-7-8上午10:42:48
 * 
 */
public class CommonLayoutContainer extends LinearLayout {

	private final static int GREEN = 0, RED = 1, BLUE = 3;
	public static final int DEFALUT_THEME = 0, WHITE_THEME = 1;

	// 顶部高度
	private final static int TOP_H = 45;
	// ViewPagerTab高度
	private final static int TAB_H = 40;//
	
	private final static int TEXT_PADDING = 3;

	// 底部高度
	private final static int BOTTOM_H = 60;
	private Context mContext;

	/**
	 * mHead
	 * -------------------------------------------------
	 * |                                               |
	 * |  leftBar     mContainerTitle        rightBar  |
	 * |                                               |
	 * -------------------------------------------------
	 */
	private LinearLayout headWrapper;
	
	private LinearLayout middleWrapper;
	
	// 头部左边栏一般是返回
	private LinearLayout leftBar;
	
	// 标题
	private TextView middleTitle;
	
	// 头部右边栏一般是图标
	private LinearLayout rightBar;
	private ImageView rightBarItem;


	/**
	 * 内容------------------------------------------开始
	 */
	private LinearLayout containerWrapper;
	
	/**
	 * 底部
	 */
	private LinearLayout bottomWrapper;

	/**
	 * 底部 每一个底部操作项中外层都多包了一个RelativeLayout
	 */
	private List<RelativeLayout> bottomItems;
	
	private ViewPagerTab tab;
	private ViewPager pager;

	/**
	 * 初始化CommonLayoutContainer
	 * 
	 * @param context
	 */
	public CommonLayoutContainer(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	/**
	 * 初始化CommonLayoutContainer
	 * 
	 * @param context
	 * @param attrs
	 */
	public CommonLayoutContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		this.setOrientation(VERTICAL);
	}

	/**
	 * 初始化Container
	 * 
	 * @param title
	 * @param view
	 * @param theme
	 */
	public void buildContainer(String title, View contentView, View middleView, int theme) {
		buildHead();
		if (title != null) {
			middleTitle.setText(title);
		}
		//中间layout
		if(middleView != null){
			middleWrapper = new LinearLayout(mContext);
			middleWrapper.setOrientation(HORIZONTAL);
			middleWrapper.setGravity(Gravity.CENTER_VERTICAL);
			middleWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					(int) mContext.getResources().getDimension(R.dimen.welcome_middle_view_height)));
			middleWrapper.setBackgroundResource(R.drawable.common_title_bg);
			middleWrapper.addView(middleView);
			addView(middleWrapper);
		}
		// 内容Layout
		containerWrapper = new LinearLayout(mContext);
		containerWrapper.setOrientation(VERTICAL);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		containerWrapper.setLayoutParams(params);
		containerWrapper.addView(contentView, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));

		RelativeLayout contentWrapper = new RelativeLayout(mContext);
		contentWrapper.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		contentWrapper.setBackgroundResource(R.drawable.common_bg_color);
		this.addView(contentWrapper);
		contentWrapper.addView(containerWrapper);
	}
	
	
	
	/**
	 * 初始化Container
	 * @param customViewPaper 自定义的ViewPaper,为null则默认的ViewPaper
	 * @param title
	 * @param views
	 * @param tabTitles
	 */
	public void buildTabContainer(ViewPager customViewPaper, View middleView, String title, View[] views, String[] tabTitles) {
		buildHead();
		if (title != null) {
			middleTitle.setText(title);
		}
		
		buildViewPager();
		RelativeLayout layout = new RelativeLayout(mContext);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		layout.setBackgroundResource(R.drawable.common_bg_color);
		this.addView(layout);

		// 内容Layout
		containerWrapper = new LinearLayout(mContext);
		containerWrapper.setOrientation(VERTICAL);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		containerWrapper.setLayoutParams(params);
		layout.addView(containerWrapper);

		// ViewPagerTab添加到containerWrapper中
		containerWrapper.addView(tab);
		// 不为null则使用自定义ViewPaper
		if (customViewPaper != null)
			pager = customViewPaper;
		// ViewPager添加到containerWrapper中
		containerWrapper.addView(pager);

		// 添加Tabs
		createTabs(title, views, tabTitles);
	}

	/**
	 * 初始化头部
	 */
	private void buildHead() {
		// 头部Layout
		headWrapper = new LinearLayout(mContext);
		headWrapper.setOrientation(HORIZONTAL);
		headWrapper.setGravity(Gravity.CENTER_VERTICAL);
		headWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				ScreenUtil.dip2px(mContext, TOP_H)));
		headWrapper.setBackgroundResource(R.drawable.common_title_bg);

		// 返回操作的ImageView
		leftBar = new LinearLayout(mContext);
		leftBar.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		leftBar.setGravity(Gravity.CENTER);
		leftBar.setBackgroundResource(R.drawable.common_menu_selector);

		ImageView mGoback = new ImageView(mContext);
		mGoback.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mGoback.setImageResource(R.drawable.common_back);
		leftBar.addView(mGoback);
		headWrapper.addView(leftBar);

		// 标题
		middleTitle = new TextView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 1.0f);
		middleTitle.setLayoutParams(params);
		middleTitle.setGravity(Gravity.CENTER);
		middleTitle.setTextSize(20);
		middleTitle.setTextColor(Color.WHITE);
		headWrapper.addView(middleTitle);

		// fix me
		rightBar = new LinearLayout(mContext);
		rightBar.setLayoutParams(new LayoutParams(ScreenUtil.dip2px(
				mContext, 50), LayoutParams.MATCH_PARENT));
		rightBar.setGravity(Gravity.CENTER);
		rightBar.setBackgroundResource(R.drawable.common_menu_selector);

		rightBarItem = new ImageView(mContext);
		rightBarItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		rightBarItem.setImageResource(R.drawable.common_sort_menu);
		rightBar.setVisibility(View.INVISIBLE);
		rightBar.addView(rightBarItem);
		headWrapper.addView(rightBar);

		addView(headWrapper);
	}
	
	
	/**
	 * 创建底部操作栏
	 * @author dingdj
	 * Date:2014-7-8下午2:13:22
	 */
	public void buildBottom(String[] texts, int[] resId, OnClickListener[] listeners){
		if (texts == null) {
			return;
		}
		if (bottomItems == null) {
			bottomItems = new ArrayList<RelativeLayout>();
		}
		// 自定义底部
		bottomWrapper = new LinearLayout(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(mContext, BOTTOM_H));
		bottomWrapper.setGravity(Gravity.CENTER);
		bottomWrapper.setOrientation(HORIZONTAL);
		bottomWrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(mContext, BOTTOM_H)));
		bottomWrapper.setBackgroundResource(R.drawable.common_btn_layout_bg);
		bottomWrapper.setPadding(ScreenUtil.dip2px(mContext, 6), ScreenUtil.dip2px(mContext, 6), 0 ,ScreenUtil.dip2px(mContext, 6));
		bottomWrapper.setLayoutParams(params);
		
		containerWrapper.addView(bottomWrapper);
		
		int res = -1;
		// 添加底部操作
		for (int i = 0; i < texts.length; i++) {
			if (resId == null) {
				res = -1;
			} else {
				res = resId[i];
			}
			if (listeners == null || listeners[i] == null) {
				bottomWrapper.addView(buildBottomItem(texts[i], res, null, BLUE));
			} else {
				bottomWrapper.addView(buildBottomItem(texts[i], res, listeners[i], BLUE));
			}
		}
	}
	
	
	
	/**
	 * 初始化ViewPaper
	 */
	private void buildViewPager() {
		// mPagerTab
		tab = new ViewPagerTab(mContext);
		LayoutParams lparams = new LayoutParams(300, ScreenUtil.dip2px(mContext, TAB_H));
		tab.setLayoutParams(lparams);
		
		// ViewPager
		pager = new ViewPager(mContext);
		lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		pager.setLayoutParams(lparams);
	}
	
	
	/**
	 * 创建底部Item
	 * @author dingdj
	 * Date:2014-7-8下午2:19:32
	 */
	private RelativeLayout buildBottomItem(String text, int src,
			OnClickListener listener, int style) {
		RelativeLayout layout = new RelativeLayout(mContext);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 1.0f);
		lp.setMargins(0, 0, ScreenUtil.dip2px(mContext, 6), 0);
		layout.setLayoutParams(lp);
		LinearLayout innerLayout = new LinearLayout(mContext);
		innerLayout.setId(199);
		innerLayout.setLayoutParams(new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		if (style == GREEN) {
			innerLayout
					.setBackgroundResource(R.drawable.common_btn_green_selector);
		} else if (style == RED) {
			innerLayout
					.setBackgroundResource(R.drawable.common_btn_red_selector);
		} else {
			innerLayout
					.setBackgroundResource(R.drawable.common_btn_blue_selector);
		}
		innerLayout.setGravity(Gravity.CENTER);

		if (src > 0) {
			ImageView im = new ImageView(mContext);
			im.setBackgroundResource(src);
			innerLayout.addView(im);
		}

		TextView tv = new TextView(mContext);
		tv.setId(299);
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(18);
		tv.setTextColor(Color.WHITE);
		tv.setText(text);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(ScreenUtil.dip2px(mContext, TEXT_PADDING), 0, 0, 0);
		tv.setLayoutParams(params);
		innerLayout.addView(tv);
		innerLayout.setOnClickListener(listener);
		layout.addView(innerLayout);

		bottomItems.add(layout);
		return layout;
	}
	
	
	/**
	 * 添加Tabs
	 * @param title
	 * @param views
	 * @param tabTitles
	 */
	private void createTabs(String title, View[] views, String[] tabTitles) {
		middleTitle.setText(title);
		if (views != null)
			for (View view : views) {
				pager.addView(view);
			}
		tab.addTitle(tabTitles);
		tab.setViewpager(pager);
		pager.setTab(tab);
	}

	

	/**
	 * 菜单是否可见
	 * @param visibility
	 */
	public void setRightBarVisibility(int visibility) {
		rightBar.setVisibility(visibility);
	}
	
	/**
	 * 修改菜单图片
	 * @param resid
	 */
	public void setRightBarItemImageResource(int resid){
		rightBarItem.setImageResource(resid);
	}
	
	/**
	 * 设置点击事件
	 * @param listener
	 */
	public void setMenuListener(OnClickListener listener) {
		rightBar.setOnClickListener(listener);
	}
	
	/**
	 * 设置title
	 * @param title
	 */
	public void setTitle(String title) {
		if (title != null) {
			middleTitle.setText(title);
		}
	}
	
	/**
	 * 左边菜单是否可见
	 * @param visibility
	 */
	public void setLeftBarVisibility(int visibility) {
		leftBar.setVisibility(visibility);
	}
	
	/**
	 * 设置返回监听
	 * @param listener
	 */
	public void setLeftBarListener(OnClickListener listener) {
		if (leftBar != null) {
			leftBar.setOnClickListener(listener);
		}
	}
	
	/**
	 * 头部是否可见
	 * @param visibility
	 */
	public void setHeadVisibility(int visibility) {
		if (headWrapper != null) {
			headWrapper.setVisibility(visibility);
		}
	}
	
	/**
	 * 隐藏底部按钮
	 * @param index
	 * @param visibility
	 */
	public void setBottomVisibility(int index, int visibility) {
		if (index >= 0 && index < bottomItems.size()) {
			bottomItems.get(index).setVisibility(visibility);
		}
	}
	
	public void setInitTab(int iTab){
		try {
			tab.setInitTab(iTab);
			pager.setInitTab(iTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
