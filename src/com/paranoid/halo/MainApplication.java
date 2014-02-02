package com.paranoid.halo;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class MainApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        startService(new Intent(this, MyService.class));
        
        if (Utils.getStatus(this)) {
            String[] packages = Utils.loadArray("items", this);
            if(packages != null) {
                NotificationManager notificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                for(String packageName : packages){
                    Utils.createNotification(this, notificationManager, packageName);
                }
            }
        }
    }
}
