package hu.unideb.smartcampus.main.activity.calendar.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.main.activity.calendar.converter.TimetableEventConverter;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskTimetableEventPojo;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;


public class TimetableIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, AskTimetableEventPojo> {

    @Override
    protected AskTimetableEventPojo doInBackground(HashMap<String, String>... params) {
        try {
            CalendarSubjectsIqRequest iq = new CalendarSubjectsIqRequest();
            iq.setStudent(Connection.getInstance().getUserJID());
            iq.setType(IQ.Type.get);
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final CalendarSubjectsIqRequest calendarSubjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return TimetableEventConverter.convertToAskTimeTableEventPojo(calendarSubjectsIqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }

        return new AskTimetableEventPojo();

    }
}

