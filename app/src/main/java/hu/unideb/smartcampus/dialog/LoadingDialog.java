package hu.unideb.smartcampus.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import hu.unideb.smartcampus.R;

/**
 * Created by Headswitcher on 2017. 11. 05..
 */

public class LoadingDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        ImageView dialogImageView = (ImageView) dialog.findViewById(R.id.loading_dialog_imageView);
        dialogImageView.setBackgroundResource(R.drawable.dialog_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) dialogImageView.getBackground();
        frameAnimation.start();
        return dialog;
    }
}