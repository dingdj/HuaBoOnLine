/**
 * @author dingdj
 * Date:2014-7-10上午10:04:47
 *
 */
package com.huaboonline.activity.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.huaboonline.widget.common.CommonLayoutContainer;
import com.huaboonline.widget.common.ViewPager;

/**
 * @author dingdj
 * Date:2014-7-10上午10:04:47
 *
 */
public abstract class CommonTabActivity extends Activity {

	private CommonLayoutContainer container;
	protected LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		container = new CommonLayoutContainer(this);
		inflater = LayoutInflater.from(this);
		setContentView(container);
		initTabContainer();
		initBottom();
		View.OnClickListener gobackListener = getGobackClickListener();
		if(gobackListener != null){
			container.setLeftBarListener(gobackListener);
		}
	}

	public CommonLayoutContainer getContainer() {
		return container;
	}
	
	/**
	 * 创建container
	 * @author dingdj
	 * Date:2014-7-8下午5:57:54
	 */
	private void initTabContainer(){
		container.buildTabContainer(buildCustomViewPager(), buildMiddleView(), buildTitle(), buildCustomContentViews(), buildTabTitles());
	}
	
	abstract public ViewPager buildCustomViewPager();
	
	abstract public View buildMiddleView();
	
	abstract public View[] buildCustomContentViews();
	
	abstract public String[] buildTabTitles();
	
	abstract public String buildTitle();
	
	/**
	 * 创建bottom
	 * @author dingdj
	 * Date:2014-7-8下午5:57:54
	 */
	private void initBottom(){
		container.buildBottom(buildBottomsText(), buildBottomsResId(), buildBottomsClickListener());
	}
	
	abstract public String[] buildBottomsText();
	
	abstract public int[] buildBottomsResId();
	
	abstract public View.OnClickListener[] buildBottomsClickListener();
	
	abstract public View.OnClickListener getGobackClickListener();
	
	public void setInitTab(int iTab){
		container.setInitTab(iTab);
	}

}
