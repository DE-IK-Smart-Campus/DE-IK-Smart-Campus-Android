package hu.unideb.smartcampus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class ChatFragment extends Fragment implements OnBackPressedListener {

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onBackPressed() {

    }
}
