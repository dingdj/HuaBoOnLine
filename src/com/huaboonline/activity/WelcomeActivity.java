/**
 * @author dingdj
 * Date:2014-7-15下午12:52:15
 *
 */
package com.huaboonline.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.huaboonline.R;
import com.huaboonline.activity.common.CommonActivity;
import com.huaboonline.widget.CircleView;
import com.huaboonline.widget.WelcomeTabView;
import com.navdrawer.SimpleSideDrawer;

/**
 * @author dingdj
 * Date:2014-7-15下午12:52:15
 *
 */
public class WelcomeActivity extends CommonActivity {
	
	SimpleSideDrawer mSlidingMenu;
	ListView leftslideMenuListView;
	WelcomeTabView welcomeTabView;
	
	int[] resId = new int[]{R.drawable.ic_communities,  R.drawable.ic_home, R.drawable.ic_pages, R.drawable.ic_people, R.drawable.ic_photos};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSlidingMenu = new SimpleSideDrawer(this);
		mSlidingMenu.setLeftBehindContentView(R.layout.left_slide_bar);
		leftslideMenuListView = (ListView) findViewById(R.id.left_slide_bar);
		slideBarAdapter adapter = new slideBarAdapter();
		leftslideMenuListView.setAdapter(adapter);
	}

	@Override
	public View buildCustomContentView() {
		welcomeTabView = new WelcomeTabView(this);
		welcomeTabView.setTabSelect(WelcomeTabView.TAB_ID_1);
		return welcomeTabView;
	}

	@Override
	public String[] buildBottomsText() {
		return null;
	}

	@Override
	public int[] buildBottomsResId() {
		return null;
	}

	@Override
	public OnClickListener[] buildBottomsClickListener() {
		return null;
	}

	@Override
	public OnClickListener getGobackClickListener() {
		return new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				mSlidingMenu.toggleDrawer();
			}
		};
	}

	
	
	class slideBarAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resId.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageView = (ImageView) inflater.inflate(R.layout.slide_menu_item, null);
			imageView.setImageResource(resId[position]);
			return imageView;
		}
		
	}


	@Override
	public View buildMiddleView() {
		View view = inflater.inflate(R.layout.welcome_middle, null);
		CircleView circleView = (CircleView) view.findViewById(R.id.progress_view);
		circleView.setProgress(30);
		return view;
	}


	@Override
	public String buildCustomTitle() {
		return "欢迎你";
	}

	@Override
	public int buildCustomTheme() {
		return 0;
	}
	

}
