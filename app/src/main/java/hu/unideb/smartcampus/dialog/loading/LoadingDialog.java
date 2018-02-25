package hu.unideb.smartcampus.dialog.loading;

import android.animation.Animator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

import hu.unideb.smartcampus.R;

public class LoadingDialog extends DialogFragment {

    public static String LOADING_DIALOG_TAG = "LOADING_DIALOG_TAG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);

        LottieAnimationView animationView = dialog.findViewById(R.id.animation_view);
        animationView.setAnimation("start.json");
        animationView.setRepeatCount(0);
        animationView.playAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationView.setAnimation("idle.json");
                animationView.setRepeatCount(5);
                animationView.playAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        return dialog;
    }
}