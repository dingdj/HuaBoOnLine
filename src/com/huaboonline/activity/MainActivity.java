/**
 * @author dingdj
 * Date:2014-7-15下午12:52:15
 *
 */
package com.huaboonline.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.huaboonline.R;
import com.huaboonline.activity.common.CommonActivity;
import com.huaboonline.widget.CircleView;
import com.huaboonline.widget.WelcomeTabView;
import com.navdrawer.SimpleSideDrawer;

/**
 * @author dingdj Date:2014-7-15下午12:52:15
 *
 */
public class MainActivity extends CommonActivity {

    SimpleSideDrawer mSlidingMenu;
    ListView leftslideMenuListView;
    WelcomeTabView welcomeTabView;
    private PopupWindow popupWindow;
    private int rightBarHandlerheight;
    Button rightBar;

    int[] resId = new int[] { R.drawable.ic_communities, R.drawable.ic_home,
            R.drawable.ic_pages, R.drawable.ic_people, R.drawable.ic_photos };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlidingMenu = new SimpleSideDrawer(this);
        mSlidingMenu.setLeftBehindContentView(R.layout.left_slide_bar);
        leftslideMenuListView = (ListView) findViewById(R.id.left_slide_bar);
        slideBarAdapter adapter = new slideBarAdapter();
        leftslideMenuListView.setAdapter(adapter);
        rightBar = (Button) findViewById(R.id.right_bar);
        rightBar.setOnClickListener(popClick);
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

    class slideBarAdapter extends BaseAdapter {

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
            ImageView imageView = (ImageView) inflater.inflate(
                    R.layout.slide_menu_item, null);
            imageView.setImageResource(resId[position]);
            return imageView;
        }

    }

    @Override
    public View buildMiddleView() {
        View view = inflater.inflate(R.layout.welcome_middle, null);
        CircleView circleView = (CircleView) view
                .findViewById(R.id.progress_view);
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

    // 点击弹出左侧菜单的显示方式
    OnClickListener popClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            getPopupWindow();
            // 这里是位置显示方式,在屏幕的左侧  
            popupWindow.showAtLocation(v, Gravity.RIGHT, 0, 0);
        }
    };

    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.welcome_right_bar, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, (int) (480*0.7),
                LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
        int [] locations = new int[2];
        rightBar.getLocationInWindow(locations);
        rightBarHandlerheight = locations[1];
        Button welcome_back_right_bar = (Button) popupWindow_view.findViewById(R.id.back_welcome_right_bar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(0, rightBarHandlerheight, -5, 0);
        welcome_back_right_bar.setLayoutParams(lp);
        welcome_back_right_bar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }


}
