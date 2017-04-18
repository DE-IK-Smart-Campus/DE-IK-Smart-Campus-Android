package hu.unideb.smartcampus.main.activity.calendar.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.main.activity.calendar.converter.CustomEventConverter;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskCustomEventPojo;
import hu.unideb.smartcampus.shared.iq.request.ListCustomEventIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class CustomEventIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, AskCustomEventPojo> {


    @Override
    protected AskCustomEventPojo doInBackground(HashMap<String, String>... params) {
        try {

            ListCustomEventIqRequest iq = new ListCustomEventIqRequest();
            iq.setStudent(Connection.getInstance().getXmppConnection().getUser().getLocalpartOrThrow().toString());
            iq.setType(IQ.Type.get);
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
            final ListCustomEventIqRequest listCustomEventIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
            return CustomEventConverter.convertToAskCustomEventPojo(listCustomEventIqRequest);

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }

        return new AskCustomEventPojo();
    }
}
