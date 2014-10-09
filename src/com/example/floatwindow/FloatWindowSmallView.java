package com.example.floatwindow;

import java.lang.reflect.Field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FloatWindowSmallView extends LinearLayout{
	public static int viewWidth; //С�������Ŀ��
	public static int viewHeight;
	
	private WindowManager windowManager;
	private WindowManager.LayoutParams mParams;
	private float xInScreen;  //��ָ������
	private float yInScreen;
	private float xDownInScreen; //��ָ����ʱ����
	private float yDownInScreen;
	private float xInView; //��ָ����ʱ��С������View�ϵ�����
	private float yInView;
	private static int statusBarHeight;  //��¼״̬���߶�
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
	 * ����С��������λ��
	 */
	private void updateViewPosition(){
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}
	
	/**
	 * �򿪴���������ͬʱ�ر�С������
	 */
	private void openBigWindow(){
		MyWindowManager.createBigWindow(getContext());
		MyWindowManager.removeSmallWindow(getContext());
	}
	
	 /** 
     * ���ڻ�ȡ״̬���ĸ߶ȡ� 
     *  
     * @return ����״̬���߶ȵ�����ֵ�� 
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
     * ��С�����Ĳ�������
     * @param params
     */
    public void setParams(WindowManager.LayoutParams params){
    	mParams = params;
    }
}
