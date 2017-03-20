package hu.unideb.smartcampus.main.activity.officehours.handler;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import java.io.IOException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.main.activity.officehours.fragment.OfficeHourFragment;
import hu.unideb.smartcampus.main.activity.officehours.json.MessageTypeUserId;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskInstructorConsultingHoursProcessMessagePojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsProcessMessagePojo;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTOR;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTSPROCESSMESSAGE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTSPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOG_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.INSTRUCTORPOS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.STATUSOFCONSULTINGHOURS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SUBJECTPOS;
import static hu.unideb.smartcampus.xmpp.Connection.adminEntityJID;

/**
 * OfficeHourHandler will be the controller for the lifecycle of the :
 * {@link OfficeHourFragment} ,
 * {@link hu.unideb.smartcampus.main.activity.officehours.fragment.OfficeHourReserveFragment}
 * <p>
 * It will change the status , view , keep up the communication with the "adminuser"
 * <p>
 * Created by Erdei Krisztián on 2017.03.04..
 * <p>
 */

public class OfficeHourHandler implements IncomingChatMessageListener {

    private AskSubjectsProcessMessagePojo askSubjectsProcessMessagePojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;
    private Context context;

    public OfficeHourHandler(FragmentManager fragmentManager, Context context) throws SmackException.NotConnectedException, InterruptedException {
        super();
        this.context = context;
        this.askSubjectsProcessMessagePojo = new AskSubjectsProcessMessagePojo();
        this.objectMapper = new ObjectMapper();
        this.fragmentManager = fragmentManager;
    }

    public void sendDefaultMsg() {
        MessageTypeUserId messageTypeUserId = new MessageTypeUserId(ASKSUBJECTSPROCESSMESSAGE, Connection.getInstance().getUserJID());
        try {
            String request = objectMapper.writeValueAsString(messageTypeUserId);
            Connection.getInstance().createLoadingDialog(request, fragmentManager, new Bundle());
        } catch (JsonProcessingException e) {
            Log.e("OfficeHourHandler()", messageTypeUserId.toString());
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

    @Override
    public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
        if (StringUtils.equals(from.getLocalpart().toString(),
                adminEntityJID.getLocalpart().toString())) {
            String body = message.getBody();
            if (message.getBody().contains(ASKSUBJECTSPROCESSMESSAGERESPONSE)) {
                LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
                try {
                    askSubjectsProcessMessagePojo = objectMapper.readValue(body, AskSubjectsProcessMessagePojo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO, askSubjectsProcessMessagePojo);
                bundle.putString(STATUSOFCONSULTINGHOURS, ASKSUBJECTS);
                changeToOfficeFragmentView(bundle);
                loadingDialogFragment.nDialog.dismiss();
            }

            if (message.getBody().contains(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGERESPONSE)) {
                LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
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
                bundle.putParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO, askSubjectsProcessMessagePojo);
                bundle.putString(STATUSOFCONSULTINGHOURS, ASKINSTRUCTOR);
                changeToOfficeFragmentView(bundle);
                loadingDialogFragment.nDialog.dismiss();
            }

            if (message.getBody().contains(SIGNUPFORCONSULTINGHOURPROCESSMESSAGERESPONSE)) {
                LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
                loadingDialogFragment.nDialog.dismiss();
            }
        }

        Log.d("Adminchat", "ChatInfo: " + message.getBody());
    }
}

