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
 * Created by Headswitcher on 2017. 03. 07.. //TODO
 */

public class LoadingDialogFragment extends Fragment {

    public ProgressDialog nDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nDialog = new ProgressDialog(getActivity());
        nDialog.setMessage("Loading..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nDialog.show();
        View rootView = inflater.inflate(R.layout.fragment_dialog, container,
                false);
        return rootView;
    }
}
