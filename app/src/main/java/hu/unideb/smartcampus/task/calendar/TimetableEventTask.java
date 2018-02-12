package hu.unideb.smartcampus.task.calendar;

import android.app.Activity;
import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.converter.calendar.TimetableEventConverter;
import hu.unideb.smartcampus.pojo.calendar.AskTimetableEventPojo;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.container.Container.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class TimetableEventTask extends AsyncTask<String, Long, AskTimetableEventPojo> {

    private static final String TAG = TimetableEventTask.class.getSimpleName();

    private LoadingDialog loadingDialog;

    private Activity activity;

    public TimetableEventTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
    }

    @Override
    protected AskTimetableEventPojo doInBackground(String... strings) {
        final Connection connection = Connection.getInstance();
        try {
            CalendarSubjectsIqRequest calendarSubjectsIqRequest = new CalendarSubjectsIqRequest();
            calendarSubjectsIqRequest.setStudent(connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
            calendarSubjectsIqRequest.setStartPeriod(1485813600L);
            calendarSubjectsIqRequest.setEndPeriod(1496181600L);
            calendarSubjectsIqRequest.setType(IQ.Type.get);
            calendarSubjectsIqRequest.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(calendarSubjectsIqRequest);
            final CalendarSubjectsIqRequest calendarSubjectsIq = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return TimetableEventConverter.convertToAskTimeTableEventPojo(calendarSubjectsIq);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }
        return new AskTimetableEventPojo();
    }

    @Override
    protected void onPostExecute(AskTimetableEventPojo askTimetableEventPojo) {
        super.onPostExecute(askTimetableEventPojo);
        loadingDialog.dismiss();
    }
}
