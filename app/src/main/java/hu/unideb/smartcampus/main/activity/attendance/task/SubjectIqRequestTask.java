package hu.unideb.smartcampus.main.activity.attendance.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.main.activity.attendance.converter.AttendanceConverter;
import hu.unideb.smartcampus.main.activity.attendance.pojo.AskSubjectPojo;
import hu.unideb.smartcampus.shared.iq.request.ListUserAttendanceIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class SubjectIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, AskSubjectPojo> {
    @Override
    protected AskSubjectPojo doInBackground(HashMap<String, String>... hashMaps) {
        try {
            ListUserAttendanceIqRequest iq = new ListUserAttendanceIqRequest();
            EntityFullJid user = Connection.getInstance().getXmppConnection().getUser();
            iq.setStudent(user.getLocalpartOrThrow().toString());
            iq.setType(IQ.Type.get);
            iq.setFrom(user);
            iq.setTo(JidCreate.from(ADMINJID));


            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final ListUserAttendanceIqRequest calendarSubjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return AttendanceConverter.convertToAskSubjectPojo(calendarSubjectsIqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | XmppStringprepException
                | SmackException.NoResponseException
                | InterruptedException e) {
            e.printStackTrace();
        }

        return new AskSubjectPojo();
    }
}
