package hu.unideb.smartcampus.main.activity.home.fragment;


import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.calendar.handler.TimetableEventHandler;

public class HomeFragment extends Fragment implements OnBackPressedListener {

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        final ImageView im = (ImageView) v.findViewById(R.id.imageView);
//        im.setColorFilter(Color.GRAY);
//
//        im.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                im.setColorFilter(Color.YELLOW);
//            }
//        });
//
//        ImageButton ib = (ImageButton) v.findViewById(R.id.imageButton2);
//
//        ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        return v;
    }

    @Override
    public void onBackPressed() {

    }
}
