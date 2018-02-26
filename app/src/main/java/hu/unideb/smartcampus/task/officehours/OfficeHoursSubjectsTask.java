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

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.converter.OfficeHourConverter;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment;
import hu.unideb.smartcampus.pojo.officehours.AskSubjectsPojo;
import hu.unideb.smartcampus.pojo.officehours.Instructor;
import hu.unideb.smartcampus.pojo.officehours.Subject;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.dialog.loading.LoadingDialog.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment.OFFICE_HOUR_SUBJECT_KEY;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2018. 02. 10..
 */

public class OfficeHoursSubjectsTask extends AsyncTask<String, Long, AskSubjectsPojo> {

    public static String OFFICE_HOUR_LIST_FRAGMENT = "OFFICE_HOUR_LIST_FRAGMENT";

    private LoadingDialog loadingDialog;

    private Activity activity;

    public OfficeHoursSubjectsTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
        super.onPreExecute();
    }

    @Override
    protected AskSubjectsPojo doInBackground(String... strings) {
        final Connection connection = Connection.getInstance();
        try {
            SubjectsIqRequest iq = new SubjectsIqRequest();
            iq.setStudent(connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
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
//        if (askSubjectsPojo != null) {
//            OfficeHourFragment fragment = new OfficeHourFragment();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(OFFICE_HOUR_SUBJECT_KEY, askSubjectsPojo);
//
//            /* TEST ADAT
//            AskSubjectsPojo askSubjectsPojo1 = new AskSubjectsPojo();
//            ArrayList<Subject> subjects = new ArrayList<>();
//            ArrayList<Instructor> instructors = new ArrayList<>();
//            instructors.add(new Instructor(1L, "Pató Pál", null));
//
//            subjects.add(new Subject(1, "Tárgy1", instructors));
//            subjects.add(new Subject(2, "Tárgy2", instructors));
//            subjects.add(new Subject(3, "Tárgy3", instructors));
//            askSubjectsPojo1.setSubjects(subjects);
//
//            //bundle.putSerializable("OFFICE_HOUR_SUBJECT_KEY", askSubjectsPojo1);
//            */
//
//            fragment.setArguments(bundle);
//            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.drawer_layout, fragment, OFFICE_HOUR_LIST_FRAGMENT);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
    }
}
