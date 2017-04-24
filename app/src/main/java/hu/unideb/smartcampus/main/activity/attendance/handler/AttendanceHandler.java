package hu.unideb.smartcampus.main.activity.attendance.handler;

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
import hu.unideb.smartcampus.main.activity.attendance.fragment.AttendanceFragment;
import hu.unideb.smartcampus.main.activity.attendance.pojo.AskSubjectPojo;
import hu.unideb.smartcampus.main.activity.attendance.task.SubjectIqRequestTask;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.ASKSUBJECTSNAME;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.STATUSATTENDANCE;

public class AttendanceHandler {
    private AskSubjectPojo askSubjectPojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;
    private Context context;

    public AttendanceHandler(FragmentManager fragmentManager, Context context) throws SmackException.NotConnectedException, InterruptedException {
        super();
        this.context = context;
        this.askSubjectPojo = new AskSubjectPojo();
        this.objectMapper = new ObjectMapper();
        this.fragmentManager = fragmentManager;
    }

//    public void sendDefaultMsg() {
//        try {
//            HashMap<String, String> param = new HashMap<>();
//            AskSubjectPojo askSubjectsPojo = Connection.getInstance().createLoadingDialog(new SubjectIqRequestTask(), fragmentManager, param);

//            askSubjectsPojo.getSubjectList();
//
//            Bundle bundle = new Bundle();
//            bundle.putString(STATUSATTENDANCE, ASKSUBJECTSNAME);
//            bundle.putParcelable(ASK_SUBJECTS_PROCESS_MESSAGE_POJO, askSubjectsPojo);
//            changeToOfficeFragmentView(bundle);
//
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


    private void changeToOfficeFragmentView(Bundle bundle) {
        Fragment fragment = new AttendanceFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "Attendance");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
