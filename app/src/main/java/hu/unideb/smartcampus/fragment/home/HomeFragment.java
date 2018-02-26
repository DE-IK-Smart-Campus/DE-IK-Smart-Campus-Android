package hu.unideb.smartcampus.fragment.home;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.application.settings.AppSettings;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(getResources().getString(R.string.home_menu_text));
        Button button = view.findViewById(R.id.b);
        AppSettings appSettings = AppSettings.getSettings(getActivity());
        Toast.makeText(getContext(), appSettings.getSelected_notification_sound(), Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNotification(getContext(), Uri.parse(appSettings.getSelected_notification_sound()));
            }
        });
        return view;
    }


    private void generateNotification(Context mContext, Uri message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        Notification notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker("Ticker").setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(getActivity().getResources().getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Test"))
                .setSound(message)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentText("Text").build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(12, notification);
    }

}
