package com.example.floatwindow;

import java.lang.reflect.Field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindowSmallView extends LinearLayout{
	public static int viewWidth; //小悬浮窗的宽高
	public static int viewHeight;
	
	private WindowManager windowManager;
	private WindowManager.LayoutParams mParams;
	private float xInScreen;  //手指的坐标
	private float yInScreen;
	private float xDownInScreen; //手指按下时坐标
	private float yDownInScreen;
	private float xInView; //手指按下时在小悬浮窗View上的坐标
	private float yInView;
	private static int statusBarHeight;  //记录状态栏高度
	public FloatWindowSmallView(Context context) {
		super(context);
		windowManager = (WindowManager) 
				context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
		View view = findViewById(R.id.small_window_float_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			float dx = Math.abs(xDownInScreen - xInScreen);
			float dy = Math.abs(yDownInScreen - yInScreen);
			if(dx < 10 && dy < 10){
				openBigWindow();
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 更新小悬浮窗的位置
	 */
	private void updateViewPosition(){
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}
	
	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗
	 */
	private void openBigWindow(){
		MyWindowManager.createBigWindow(getContext());
		MyWindowManager.removeSmallWindow(getContext());
	}
	
	 /** 
     * 用于获取状态栏的高度。 
     *  
     * @return 返回状态栏高度的像素值。 
     */  
    private int getStatusBarHeight() {  
        if (statusBarHeight == 0) {  
            try {  
                Class<?> c = Class.forName("com.android.internal.R$dimen");  
                Object o = c.newInstance();  
                Field field = c.getField("status_bar_height");  
                int x = (Integer) field.get(o);  
                statusBarHeight = getResources().getDimensionPixelSize(x);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return statusBarHeight;  
    }  
    
    /**
     * 将小浮窗的参数传入
     * @param params
     */
    public void setParams(WindowManager.LayoutParams params){
    	mParams = params;
    }
}
