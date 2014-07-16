/**
 * @author dingdj
 * Date:2014-7-8上午10:40:28
 *
 */
package com.huaboonline.activity.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.huaboonline.widget.common.CommonLayoutContainer;

/**
 * @author dingdj
 * Date:2014-7-8上午10:40:28
 *
 */
public abstract class CommonActivity extends Activity {
	
	private CommonLayoutContainer container;
	protected LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		container = new CommonLayoutContainer(this);
		inflater = LayoutInflater.from(this);
		setContentView(container);
		initContainer();
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
	private void initContainer(){
		container.buildContainer(buildCustomTitle(), buildCustomContentView(), buildMiddleView(), buildCustomTheme());
	}
	
	/**
	 * @author dingdj
	 * Date:2014-7-16下午5:15:10
	 *  @return
	 */
	abstract public View buildMiddleView();

	abstract public View buildCustomContentView();
	
	abstract public String buildCustomTitle();
	
	abstract public int buildCustomTheme();
	
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
	
}
