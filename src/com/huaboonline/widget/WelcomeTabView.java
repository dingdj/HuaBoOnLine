/**
 * @author dingdj
 * Date:2014-7-16下午2:34:19
 *
 */
package com.huaboonline.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.huaboonline.R;

/**
 * @author dingdj Date:2014-7-16下午2:34:19
 * 
 */
public class WelcomeTabView extends LinearLayout {

	private final static String TAG = "WelcomeTabView";
	private boolean bStartLoad = false;
	private Context ctx;
	public static final int TAB_ID_1 = 1;
	public static final int TAB_ID_2 = 2;
	private TextView tab_select_tv_1, tab_select_tv_2;
	private WelcomeContentView tab_content_1, tab_content_2;
	int curentViewIndex = 0;
	WelcomeContentView oldContentView = null;
	WelcomeContentView newContentView = null;

	/**
	 * @param context
	 */
	public WelcomeTabView(Context context) {
		super(context);
		init(context);
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public WelcomeTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	/**
	 * 初始化
	 * @author dingdj
	 * Date:2014-7-16下午5:34:59
	 *  @param context
	 */
	private void init(Context context){
		ctx = context;
		LayoutInflater.from(ctx).inflate(
				R.layout.welcome_tab, this);
		tab_select_tv_1 = (TextView) findViewById(R.id.tab_select_tv_1);
		tab_select_tv_2 = (TextView) findViewById(R.id.tab_select_tv_2);
		tab_content_1 = (WelcomeContentView) findViewById(R.id.tab_content_1);
		tab_content_2 = (WelcomeContentView) findViewById(R.id.tab_content_2);
		tab_select_tv_1.setOnClickListener(tabOnClick);
		tab_select_tv_2.setOnClickListener(tabOnClick);
	}



	OnClickListener tabOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			if (v.getId() == tab_select_tv_1.getId()){
				setTabSelect(TAB_ID_1);
			}
			if (v.getId() == tab_select_tv_2.getId()){
				setTabSelect(TAB_ID_2);
			} 
		}
	};
	
	public void setTabSelect(int tabIndex){
		TextView oldTextView = null;
		TextView newTextView = null;
		
		if (tabIndex==TAB_ID_1) {
			oldTextView = tab_select_tv_2;
			newTextView = tab_select_tv_1;
			oldContentView = tab_content_2;
			newContentView = tab_content_1;
		}
		
		if (tabIndex==TAB_ID_2) {
			oldTextView = tab_select_tv_1;
			newTextView = tab_select_tv_2;
			oldContentView = tab_content_1;
			newContentView = tab_content_2;		
		}
		if (oldTextView==null)
			return ;
		
		oldTextView.setBackgroundResource(R.drawable.tab_tv_bg);
		oldTextView.setTextColor(ctx.getResources().getColor(R.color.common_little_text_color));
		oldContentView.setVisibility(View.GONE);
		if (newTextView==tab_select_tv_1) {
			newTextView.setBackgroundResource(R.drawable.tab_left_selected);
			oldTextView.setBackgroundResource(R.drawable.tab_right);
		}
		if (newTextView==tab_select_tv_2) {
			newTextView.setBackgroundResource(R.drawable.tab_right_selected);
			oldTextView.setBackgroundResource(R.drawable.tab_left);
		}
		newTextView.setTextColor(Color.WHITE);
		newContentView.setVisibility(View.VISIBLE);
		//newContentView.startLoadData(initParaMap);

	}

}
