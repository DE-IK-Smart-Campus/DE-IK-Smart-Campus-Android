package hu.unideb.smartcampus.main.activity.officehours.handler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.io.IOException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.main.activity.officehours.fragment.OfficeHourFragment;
import hu.unideb.smartcampus.main.activity.officehours.json.MessageTypeUserId;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskInstructorConsultingHoursProcessMessagePojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsProcessMessagePojo;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.activity.MainActivity_SmartCampus.CURRENT_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTOR;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTSPROCESSMESSAGE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTSPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOGTAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.INSTRUCTORPOS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.STATUSOFCONSULTINGHOURS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SUBJECTPOS;

/**
 * OfficeHourHandler will be the controller for the lifecycle of the : {@link OfficeHourFragment} , {@link hu.unideb.smartcampus.main.activity.officehours.activity.ReserveOfficeHourActivity}
 * it will change the status , view , keep up the communication with the "adminuser"
 * <p>
 * Created by Erdei Krisztián on 2017.03.04..
 */

public class OfficeHourHandler implements ChatMessageListener {

    private AskSubjectsProcessMessagePojo askSubjectsProcessMessagePojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;

    public OfficeHourHandler(FragmentManager fragmentManager) throws SmackException.NotConnectedException, InterruptedException {
        super();
        askSubjectsProcessMessagePojo = new AskSubjectsProcessMessagePojo();
        objectMapper = new ObjectMapper();

        this.fragmentManager = fragmentManager;

        Connection.getInstance().getAdminChat().addMessageListener(this);
        MessageTypeUserId messageTypeUserId = new MessageTypeUserId(ASKSUBJECTSPROCESSMESSAGE, Connection.getInstance().getUserJID());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String request = objectMapper.writeValueAsString(messageTypeUserId);
            Connection.getInstance().createLoadingDialog(request, fragmentManager, new Bundle());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        String body = message.getBody();
        if (message.getBody().contains(ASKSUBJECTSPROCESSMESSAGERESPONSE)) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                askSubjectsProcessMessagePojo = objectMapper.readValue(body, AskSubjectsProcessMessagePojo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOGTAG);
            loadingDialogFragment.nDialog.dismiss();

            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO, askSubjectsProcessMessagePojo);
            bundle.putString(STATUSOFCONSULTINGHOURS, ASKSUBJECTS);
            changeToOfficeFragmentView(bundle);
        }

        if (message.getBody().contains(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE)) {
            LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOGTAG);
            loadingDialogFragment.nDialog.dismiss();
            int instructorPos = loadingDialogFragment.getArguments().getInt(INSTRUCTORPOS);
            int subjectPos = loadingDialogFragment.getArguments().getInt(SUBJECTPOS);
            try {
                AskInstructorConsultingHoursProcessMessagePojo chInstructorPojo = objectMapper.readValue(body, AskInstructorConsultingHoursProcessMessagePojo.class);
                askSubjectsProcessMessagePojo.getSubjects().get(subjectPos).getInstructors().get(instructorPos).setConsultingHoursList(chInstructorPojo.getConsultingHours());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putInt(INSTRUCTORPOS, instructorPos);
            bundle.putInt(SUBJECTPOS, subjectPos);
            bundle.putParcelable(EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO, askSubjectsProcessMessagePojo);
            bundle.putString(STATUSOFCONSULTINGHOURS, ASKINSTRUCTOR);
            changeToOfficeFragmentView(bundle);
        }

        if (message.getBody().contains(SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE)) {
            Connection.getInstance().getAdminChat().removeMessageListener(this);
        }

    }

    private void changeToOfficeFragmentView(Bundle bundle) {
        Fragment fragment = new OfficeHourFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
}

