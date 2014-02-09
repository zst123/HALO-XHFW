package com.paranoid.halo;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;



public class MyService extends Service{

	
	public static final int FLAG_FLOATING_WINDOW = 0x00002000;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		
		}

	public void onStart(Intent intent1, int startId) {
		ActivityManager am = (ActivityManager) MyService.this.getSystemService(ACTIVITY_SERVICE);
		RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
		String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
		
		Intent intent =getPackageManager().getLaunchIntentForPackage(foregroundTaskPackageName);
			intent.addFlags(FLAG_FLOATING_WINDOW);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			intent.setFlags(intent.getFlags()& ~Intent.FLAG_ACTIVITY_TASK_ON_HOME);
			intent.setFlags(intent.getFlags()& ~Intent.FLAG_ACTIVITY_SINGLE_TOP);
			
		getApplication().startActivity(intent);
        
	}
	
	@Override
	public void onDestroy() {
		
	}


}



