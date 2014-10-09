package com.example.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyWindowManager {
	
	private static WindowManager mWindowManager;
	private static FloatWindowSmallView smallWindow;
	private static FloatWindowBigView bigWindow;
	private static LayoutParams smallWindowParams;
	private static LayoutParams bigWindowParams;
	
	/**
	 * 创建小悬浮窗
	 * @param context
	 */
	public static void createSmallWindow(Context context){
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if(smallWindow == null){
			smallWindow = new FloatWindowSmallView(context);
			if(smallWindowParams == null){
				smallWindowParams = new LayoutParams();
				smallWindowParams.type = LayoutParams.TYPE_PHONE;  
                smallWindowParams.format = PixelFormat.RGBA_8888;  
                smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL  
                        | LayoutParams.FLAG_NOT_FOCUSABLE;  
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;  
                smallWindowParams.width = FloatWindowSmallView.viewWidth;  
                smallWindowParams.height = FloatWindowSmallView.viewHeight;  
                smallWindowParams.x = screenWidth;  
                smallWindowParams.y = screenHeight / 2;  
			}
			smallWindow.setParams(smallWindowParams);  
            windowManager.addView(smallWindow, smallWindowParams);  
		}
	}
	
	/**
	 * 将小悬浮窗从屏幕上移除
	 * @param context
	 */
	public static void removeSmallWindow(Context context){
		if(smallWindow != null){
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			smallWindow = null;
		}
	}
	
	/**
	 * 创建一个大的悬浮窗
	 * @param context
	 */
	public static void createBigWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (bigWindow == null) {
			bigWindow = new FloatWindowBigView(context);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				bigWindowParams.x = screenWidth / 2
						- FloatWindowBigView.viewWidth / 2;
				bigWindowParams.y = screenHeight / 2
						- FloatWindowBigView.viewHeight / 2;
				bigWindowParams.type = LayoutParams.TYPE_PHONE;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = FloatWindowBigView.viewWidth;
				bigWindowParams.height = FloatWindowBigView.viewHeight;
			}
			windowManager.addView(bigWindow, bigWindowParams);
		}
	}
	
	 /** 
     * 将大悬浮窗从屏幕上移除。 
     * @param context 
     */  
    public static void removeBigWindow(Context context) {  
        if (bigWindow != null) {  
            WindowManager windowManager = getWindowManager(context);  
            windowManager.removeView(bigWindow);  
            bigWindow = null;  
        }  
    } 
    
    /**
     * 是否有悬浮窗在屏幕上显示
     * @return
     */
    public static boolean isWindowShow(){
    	return smallWindow != null || bigWindow != null;
    }
	
	private static WindowManager getWindowManager(Context context){
		if(mWindowManager == null){
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}
}
