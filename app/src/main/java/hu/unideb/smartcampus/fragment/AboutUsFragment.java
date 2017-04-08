package hu.unideb.smartcampus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class AboutUsFragment  extends Fragment implements OnBackPressedListener {

    public AboutUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us_fragment, container, false);
    }

    @Override
    public void onBackPressed() {

    }
}
