package hu.unideb.smartcampus.task.officehours;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.old.officehours.converter.OfficeHourConverter;
import hu.unideb.smartcampus.old.officehours.fragment.OfficeHourFragment;
import hu.unideb.smartcampus.old.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.old.officehours.pojo.Instructor;
import hu.unideb.smartcampus.old.officehours.pojo.Subject;
import hu.unideb.smartcampus.old.officehours.task.SubjectsIqRequestTask;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.task.pojo.ReturnPojo;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.old.officehours.task.SubjectsIqRequestTask.PARAM_ACTUAL_USER_JID;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by Headswitcher on 2018. 02. 10..
 */

public class OfficeHoursSubjectsTask extends AsyncTask<String, Long, AskSubjectsPojo> {

    private LoadingDialog loadingDialog;

    private Activity activity;

    public OfficeHoursSubjectsTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), "LOADING");
        super.onPreExecute();
    }

    @Override
    protected AskSubjectsPojo doInBackground(String... strings) {
        final Connection connection = Connection.getInstance();
        HashMap<String, String> param = new HashMap<>();
        param.put(PARAM_ACTUAL_USER_JID, connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
        try {
            SubjectsIqRequest iq = new SubjectsIqRequest();
            iq.setStudent(PARAM_ACTUAL_USER_JID);
            iq.setType(IQ.Type.get);
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final SubjectsIqRequest subjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return OfficeHourConverter.convertToAskSubjectsProcessMessagePojo(subjectsIqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }
        return new AskSubjectsPojo();
    }

    @Override
    protected void onPostExecute(AskSubjectsPojo askSubjectsPojo) {
        super.onPostExecute(askSubjectsPojo);
        loadingDialog.dismiss();
        if (askSubjectsPojo != null) {
            OfficeHourFragment fragment = new OfficeHourFragment();
            Bundle bundle = new Bundle();
            //bundle.putSerializable("SUBJECTS", askSubjectsPojo);

            AskSubjectsPojo askSubjectsPojo1 = new AskSubjectsPojo();
            ArrayList<Subject> subjects = new ArrayList<>();
            ArrayList<Instructor> instructors = new ArrayList<>();
            instructors.add(new Instructor(1L, "Pató Pál", null));
            subjects.add(new Subject("Tárgy1", instructors));
            subjects.add(new Subject("Tárgy2", instructors));
            subjects.add(new Subject("Tárgy3", instructors));
            askSubjectsPojo1.setSubjects(subjects);

            bundle.putSerializable("SUBJECTS", askSubjectsPojo1);

            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment, OFFICE_HOURS_TAG);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
