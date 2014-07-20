/**
 * @author dingdj
 *
 */
package com.huaboonline.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.huaboonline.R;
import com.ddj.commonkit.android.ActivityUtil;
import com.ddj.commonkit.android.system.ColorUtil;
import com.viewpagerindicator.CirclePageIndicator;

public class ReadMeActivity extends Activity {

    private ViewPager mViewPager;
    private LayoutInflater inflater;


    private int[] resId = new int[]{R.drawable.read_me_one,
            R.drawable.read_me_two, R.drawable.read_me_three};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_me_activity);
        inflater = getLayoutInflater();
        setupViews();
    }

    /**
     * 创建引导页面
     *
     * @author dingdj Date:2014-7-15上午8:47:24
     */
    private void setupViews() {
        // 初始化JSonArray,给ViewPageAdapter提供数据源用.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.read_me_item, null);
                itemView.setBackgroundResource(resId[position]);
                ((ViewPager) container).addView(itemView);

                if (position == 2) {
                    Button enter_main = (Button) itemView.findViewById(R.id.enter_main);
                    enter_main.setVisibility(View.VISIBLE);
                    enter_main.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          ActivityUtil.startActivityByClassNameSafe(ReadMeActivity.this,
                                                                  MainActivity.class.getName());
                                                          ReadMeActivity.this.finish();
                                                      }
                                                  }
                    );
                }
                return itemView;
            }

            @Override
            public int getCount() {
                return resId.length;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {

            }


        });


        //Bind the title indicator to the adapter
        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circleIndicator.setRadius(10f);
        circleIndicator.setFillColor(ColorUtil.parseColor("#630598ce"));
        circleIndicator.setViewPager(mViewPager);
    }
}
