package hu.unideb.smartcampus.xmpp.listeners;

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
import hu.unideb.smartcampus.fragment.ConsultingHoursFragment;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.xmpp.Connection;
import hu.unideb.smartcampus.xmpp.pojos.CHInstructorJsonPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHInstructorPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHJsonPojo;
import hu.unideb.smartcampus.xmpp.pojos.MessageTypeUserId;

import static hu.unideb.smartcampus.activity.MainActivity_SmartCampus.CURRENT_TAG;

/**
 * Created by Erdei Krisztián on 2017.03.04..
 */

public class ConsultingHoursHandler implements ChatMessageListener {

    public static final String ASKSUBJECTSPROCESSMESSAGE = "AskSubjectsProcessMessage";
    public static final String ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE = "AskInstructorConsultingHoursProcessMessage";
    public static final String SIGNUPFORCONSULTINGHOURPROCESSMESSAGE = "SignUpForConsultingHourProcessMessage";


    public static final String EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO = "EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO";

    public static final String ASKSUBJECTSPROCESSMESSAGERESPONSE = "\"messageType\":\"AskSubjectsProcessMessageResponse\"";
    public static final String ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE = "\"messageType\":\"AskInstructorConsultingHoursProcessMessageResponse\"";
    public static final String SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE = "\"messageType\":\"SignUpForConsultingHourProcessMessageResponse\"";

    private CHJsonPojo chJsonPojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;


    public ConsultingHoursHandler(FragmentManager fragmentManager) throws SmackException.NotConnectedException, InterruptedException {
        super();
        chJsonPojo = new CHJsonPojo();
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
                chJsonPojo = objectMapper.readValue(body, CHJsonPojo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag("DIALOG");
            loadingDialogFragment.nDialog.dismiss();

            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO, chJsonPojo);
            bundle.putString("STATUSOFCONSULTINGHOURS", "ASKSUBJECTS");
            changeToCHFragmentView(bundle);
        }

        if (message.getBody().contains(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE)) {
            LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag("DIALOG");
            loadingDialogFragment.nDialog.dismiss();
            CHInstructorPojo instructor = loadingDialogFragment.getArguments().getParcelable("INSTRUCTOR");
            int instructorPos = loadingDialogFragment.getArguments().getInt("INSTRUCTORPOS");
            int subjectPos = loadingDialogFragment.getArguments().getInt("SUBJECTPOS");
            try {
                CHInstructorJsonPojo chInstructorPojo = objectMapper.readValue(body, CHInstructorJsonPojo.class);
                chJsonPojo.getSubjects().get(subjectPos).getInstructors().get(instructorPos).setConsultingHoursList(chInstructorPojo.getConsultingHours());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putInt("INSTRUCTORPOS", instructorPos);
            bundle.putInt("SUBJECTPOS", subjectPos);
            bundle.putParcelable(EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO, chJsonPojo);
            bundle.putString("STATUSOFCONSULTINGHOURS", "ASKINSTRUCTOR");
            changeToCHFragmentView(bundle);
        }

        if (message.getBody().contains(SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE)) {
            Connection.getInstance().getAdminChat().removeMessageListener(this);
        }

    }

    private void changeToCHFragmentView(Bundle bundle) {
        Fragment fragment = new ConsultingHoursFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
}

