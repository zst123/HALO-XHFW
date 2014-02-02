package com.paranoid.halo;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

@SuppressLint("NewApi")
public class MyService extends Service{

	
	private static final String TAG = "MyService";
	public static final int FLAG_FLOATING_WINDOW = 0x00002000;
	NotificationManager notificationManager;
	String Prev ;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		ActivityManager am = (ActivityManager) MyService.this.getSystemService(ACTIVITY_SERVICE);
		//
		RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
		//
		String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
		Prev=foregroundTaskPackageName;
		//Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		
		

	}

	public void onStart(Intent intent1, int startId) {
		//Toast.makeText(this,Prev, Toast.LENGTH_LONG).show();
		//
		//
		ActivityManager am = (ActivityManager) MyService.this.getSystemService(ACTIVITY_SERVICE);
		//
		RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
		//
		String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
		//
		
		if (!Prev.equals(foregroundTaskPackageName)) {
			
			//
			PackageManager pm = MyService.this.getPackageManager();
			//
			PackageInfo foregroundAppPackageInfo;
			try {
				foregroundAppPackageInfo = pm.getPackageInfo(
						foregroundTaskPackageName, 0);
			} catch (final NameNotFoundException e) {
				foregroundAppPackageInfo = null;
				Log.d(TAG, "foregroundAppPackageInfo");
			}
			//
			String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo
					.loadLabel(pm).toString();
			//
			//
			//
			//
			
			Notification.Builder mBuilder = new Notification.Builder(this)
					.setSmallIcon(R.drawable.ic_status)
					.setLargeIcon(
							Utils.getApplicationIcon(foregroundTaskPackageName,
									this)).setAutoCancel(false)
					.setContentTitle(foregroundTaskAppName)
					.setContentText(this.getString(R.string.tap_to_launch));
			Intent intent = this.getPackageManager().getLaunchIntentForPackage(
					foregroundTaskPackageName);
			intent.addFlags(FLAG_FLOATING_WINDOW);
			intent.setFlags(intent.getFlags()& ~Intent.FLAG_ACTIVITY_TASK_ON_HOME);
			intent.setFlags(intent.getFlags()& ~Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					intent, 0);
			mBuilder.setContentIntent(contentIntent);
			Notification notif = mBuilder.build();
			notif.flags |= Notification.FLAG_ONGOING_EVENT;
			// notif.priority = Notification.PRIORITY_MIN;
			notif.tickerText = foregroundTaskAppName;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(6, notif);
		}
		Prev=foregroundTaskPackageName;
        
        final Context context =this;
        new CountDownTimer(10000, 1000) {
        	
            public void onTick(long millisUntilFinished) {
               
            }

            public void onFinish() {
            	Log.d(TAG, "onRestart");
            	Intent intent2 = new Intent (context, MyService.class);
				startService(intent2);
            }
         }.start();

        
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}


}



