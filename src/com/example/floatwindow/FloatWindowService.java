package com.example.floatwindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

public class FloatWindowService extends Service{

	private Timer timer;
	private Handler handler = new Handler();
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//������ʱ����ÿ��0.5��ˢ��һ��
		if(timer == null){
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);  
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
		timer = null;
	}
	
	class RefreshTask extends TimerTask{
		@Override
		public void run() {
			//��ǰ���������棬��û����������ʾ���򴴽�������
			if(isHome() && !MyWindowManager.isWindowShow()){
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.createSmallWindow(getApplicationContext());
					}
				});
			}
			//��ǰ�������棬���������������Ƴ�������
			else if(!isHome() && MyWindowManager.isWindowShow()){
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.removeBigWindow(getApplicationContext());
						MyWindowManager.removeSmallWindow(getApplicationContext());
					}
				});
			}
		}
	}

	 /** 
     * �жϵ�ǰ�����Ƿ������� 
     */  
    private boolean isHome() {  
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);  
        return getHomes().contains(rti.get(0).topActivity.getPackageName());  
    }  
  
    /** 
     * ������������Ӧ�õ�Ӧ�ð����� 
     *  
     * @return ���ذ������а������ַ����б� 
     */  
    private List<String> getHomes() {  
        List<String> names = new ArrayList<String>();  
        PackageManager packageManager = this.getPackageManager();  
        Intent intent = new Intent(Intent.ACTION_MAIN);  
        intent.addCategory(Intent.CATEGORY_HOME);  
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,  
                PackageManager.MATCH_DEFAULT_ONLY);  
        for (ResolveInfo ri : resolveInfo) {  
            names.add(ri.activityInfo.packageName);  
        }  
        return names;  
    }  
}
