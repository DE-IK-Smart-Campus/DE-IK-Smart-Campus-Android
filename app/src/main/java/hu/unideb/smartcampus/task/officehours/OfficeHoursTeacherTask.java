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
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.converter.OfficeHourConverter;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment;
import hu.unideb.smartcampus.pojo.officehours.FromToDatesInLong;
import hu.unideb.smartcampus.pojo.officehours.Instructor;
import hu.unideb.smartcampus.pojo.officehours.OfficeHour;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.dialog.loading.LoadingDialog.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment.OFFICE_HOUR_INSTRUCTOR_KEY;
import static hu.unideb.smartcampus.task.officehours.OfficeHoursSubjectsTask.OFFICE_HOUR_LIST_FRAGMENT;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2018. 02. 10..
 */

public class OfficeHoursTeacherTask extends AsyncTask<Instructor, Long, Instructor> {

    private LoadingDialog loadingDialog;

    private Activity activity;

    public OfficeHoursTeacherTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Instructor doInBackground(Instructor... instructors) {

        try {
            InstructorConsultingDatesIqRequest iq = new InstructorConsultingDatesIqRequest();

            iq.setInstructorId(instructors[0].getInstructorId().toString());
            iq.setType(IQ.Type.get);
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final InstructorConsultingDatesIqRequest iqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return OfficeHourConverter.convertToAskInstructorOfficeHourPojo(iqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }
        return new Instructor();
    }

    @Override
    protected void onPreExecute() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(Instructor instructor) {
        super.onPostExecute(instructor);
        loadingDialog.dismiss();
        if (instructor != null) {
            OfficeHourFragment fragment = new OfficeHourFragment();
            Bundle bundle = new Bundle();
            //TODO!
            OfficeHour officeHour = new OfficeHour(1L, new FromToDatesInLong(System.nanoTime(), System.nanoTime() + 3600L), 5L);
            List<OfficeHour> officeHours = new ArrayList<>();
            officeHours.add(officeHour);
            instructor.setOfficeHourList(officeHours);
            bundle.putSerializable(OFFICE_HOUR_INSTRUCTOR_KEY, instructor);



        /*    List<OfficeHour> officeHours = new ArrayList<>();
            OfficeHour officeHour = new OfficeHour(1L, new FromToDatesInLong(System.nanoTime(), System.nanoTime() + 3600L), 5L);
            officeHours.add(officeHour);
            Instructor instructor1 = new Instructor(1L, "Pató Pál", officeHours);

            bundle.putSerializable(OFFICE_HOUR_INSTRUCTOR_KEY, instructor1); // TODO
*/

//            fragment.setArguments(bundle);
//            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.drawer_layout, fragment, OFFICE_HOUR_LIST_FRAGMENT);
//            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
