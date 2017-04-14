package hu.unideb.smartcampus.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hu.unideb.smartcampus.xmpp.Connection;

/**
 * Created by Headswitcher on 2017. 04. 13..
 */

public class LocationSender extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("locationAlarmReceiver", "onReceive: OI" );
        final Connection connection = Connection.getInstance();
        connection.maintainConnection(context);


    }
}