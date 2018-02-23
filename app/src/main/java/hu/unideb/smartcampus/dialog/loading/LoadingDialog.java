package hu.unideb.smartcampus.dialog.loading;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

import hu.unideb.smartcampus.R;

public class LoadingDialog extends DialogFragment {

    public static  String LOADING_DIALOG_TAG = "LOADING_DIALOG_TAG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        return dialog;
    }
}