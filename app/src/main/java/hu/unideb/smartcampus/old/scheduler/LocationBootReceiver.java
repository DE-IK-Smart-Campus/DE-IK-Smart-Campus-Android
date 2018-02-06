package hu.unideb.smartcampus.old.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// BEGIN_INCLUDE(autostart)
public class LocationBootReceiver extends BroadcastReceiver {
    LocationAlarmReceiver alarm = new LocationAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarm.setAlarm(context);
        }
    }
}
//END_INCLUDE(autostart)
