package hu.unideb.smartcampus.task.attendance;

import android.app.Activity;
import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import hu.unideb.smartcampus.converter.attendance.AttendanceConverter;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.pojo.attendance.AskAttendancetPojo;
import hu.unideb.smartcampus.shared.iq.request.ListUserAttendanceIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.container.Container.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class AttendanceTask extends AsyncTask<String, Long, AskAttendancetPojo> {

    private LoadingDialog loadingDialog;
    private Activity activity;

    public AttendanceTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
    }

    @Override
    protected AskAttendancetPojo doInBackground(String... strings) {
        final Connection connection = Connection.getInstance();
        try {

            ListUserAttendanceIqRequest iq = new ListUserAttendanceIqRequest();
            EntityFullJid user = Connection.getInstance().getXmppConnection().getUser();
            iq.setStudent(connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
            iq.setType(IQ.Type.get);
            iq.setFrom(user);
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final ListUserAttendanceIqRequest calendarSubjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return AttendanceConverter.convertToAskSubjectPojo(calendarSubjectsIqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }
        return new AskAttendancetPojo();
    }

    @Override
    protected void onPostExecute(AskAttendancetPojo askAttendancetPojo) {
        super.onPostExecute(askAttendancetPojo);
        loadingDialog.dismiss();
    }
}
