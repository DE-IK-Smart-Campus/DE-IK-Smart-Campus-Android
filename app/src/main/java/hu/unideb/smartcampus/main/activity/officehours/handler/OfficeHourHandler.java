package hu.unideb.smartcampus.main.activity.officehours.handler;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.officehours.fragment.OfficeHourFragment;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.main.activity.officehours.task.SubjectsIqRequestTask;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.STATUSOFCONSULTINGHOURS;

/**
 * TODO REFACTOR
 */

public class OfficeHourHandler {

    private AskSubjectsPojo askSubjectsPojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;
    private Context context;

    public OfficeHourHandler(FragmentManager fragmentManager, Context context) throws SmackException.NotConnectedException, InterruptedException {
        super();
        this.context = context;
        this.askSubjectsPojo = new AskSubjectsPojo();
        this.objectMapper = new ObjectMapper();
        this.fragmentManager = fragmentManager;
    }

    public void sendDefaultMsg() {
        try {
         //   fragmentManager = Connection.getInstance().createLoadingDialog(fragmentManager, new Bundle());
            HashMap<String, String> param = new HashMap<>();
            AskSubjectsPojo askSubjectsPojo = Connection.getInstance().createLoadingDialog(new SubjectsIqRequestTask(), fragmentManager, param);

            Bundle bundle = new Bundle();
            bundle.putString(STATUSOFCONSULTINGHOURS, ASKSUBJECTS);
            bundle.putParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO, askSubjectsPojo);
            changeToOfficeFragmentView(bundle);

            //TODO LOADING DIALOG
            //LoadingDialogFragment fragmentByTag = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
            //fragmentByTag.nDialog.dismiss();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void changeToOfficeFragmentView(Bundle bundle) {
        Fragment fragment = new OfficeHourFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

}

