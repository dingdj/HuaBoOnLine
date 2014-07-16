package com.huaboonline.widget;

import com.ddj.commonkit.android.system.ScreenUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @author dingdj Date:2014-7-16上午8:21:04
 * 
 */
public class CircleView extends View {

	private int maxProgress = 100;
	private int progress = 30;
	private int progressStrokeWidth = 8;
	private int totalCourse = 20;
	//画圆所在的距形区域
	private RectF oval;
	private Paint mPaint;
	
	private int padding;
	

	public CircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true); // 消除锯齿
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE); // 绘制空心圆
		oval = new RectF();
		padding = ScreenUtil.dip2px(getContext(), 3);
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int width = this.getWidth();
		int height = this.getHeight();
 
		if(width!=height)
		{
			int min=Math.min(width, height);
			width=min;
			height=min;
		}
 
		mPaint.setAntiAlias(true); // 设置画笔为抗锯齿
		mPaint.setColor(Color.WHITE); // 设置画笔颜色
		canvas.drawColor(Color.TRANSPARENT); // 白色背景
		mPaint.setStrokeWidth(progressStrokeWidth); //线宽
		mPaint.setStyle(Style.STROKE);
 
		oval.left = progressStrokeWidth / 2; // 左上角x
		oval.top = progressStrokeWidth / 2; // 左上角y
		oval.right = width - progressStrokeWidth / 2; // 左下角x
		oval.bottom = height - progressStrokeWidth / 2; // 右下角y
		
		mPaint.setColor(Color.rgb(0x57, 0x87, 0xb6));
		canvas.drawArc(oval, -90, 360, false, mPaint); // 绘制白色圆圈，即进度条背景
		mPaint.setColor(Color.rgb(0xff, 0xff, 0xff));
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360, false, mPaint); // 绘制进度圆弧，这里是蓝色
 
		mPaint.setStrokeWidth(1);
		String text = progress + "%";
		int textHeight = height / 4;
		mPaint.setTextSize(textHeight);
		int textWidth = (int) mPaint.measureText(text, 0, text.length());
		mPaint.setStyle(Style.FILL);
		canvas.drawText(text, width / 2 - textWidth / 2, height / 2 - padding, mPaint);
		
		mPaint.setTextSize(textHeight/2);
		String detail = "共"+totalCourse+"门课程";
		textWidth = (int) mPaint.measureText(detail, 0, detail.length());
		canvas.drawText(detail, width / 2 - textWidth / 2, height / 2 + padding + textHeight/2, mPaint);
	}

	public void setProgress(int progress) {
		this.progress = 100-progress;
	}

	public void setTotalCourse(int totalCourse) {
		this.totalCourse = totalCourse;
	}

}
