package hu.unideb.smartcampus.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.smartcampus.R;


/**
 * Created by Headswitcher on 2017. 03. 07..
 */

public class LoginDialogFragment extends Fragment {

    public ProgressDialog nDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nDialog = new ProgressDialog(getActivity());
        nDialog.setMessage(getString(R.string.login_in_progress));
        nDialog.setIndeterminate(true);
        nDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nDialog.show();
        return inflater.inflate(R.layout.fragment_dialog, container,
                false);
    }
}
