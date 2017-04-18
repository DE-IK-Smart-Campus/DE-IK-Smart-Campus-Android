package hu.unideb.smartcampus.main.activity.officehours.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.main.activity.officehours.converter.OfficeHourConverter;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Instructor;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2017. 03. 24..
 * //TODO
 */

public class InstructorConsultingDatesIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, Instructor> {


    public static final String INSTRUCTOR_ID = "INSTRUCTOR_ID";

    @Override
    protected Instructor doInBackground(HashMap<String, String>... params) {
        try {
            InstructorConsultingDatesIqRequest iq = new InstructorConsultingDatesIqRequest();

            iq.setInstructorId(params[0].get(INSTRUCTOR_ID));
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
}
