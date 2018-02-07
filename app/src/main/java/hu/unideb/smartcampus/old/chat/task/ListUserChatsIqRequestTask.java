package hu.unideb.smartcampus.old.chat.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.old.chat.converter.ChatConverter;
import hu.unideb.smartcampus.old.chat.pojo.ListUserChatsIqRequestPojo;
import hu.unideb.smartcampus.shared.iq.request.ListUserChatsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2017. 04. 11..
 */

public class ListUserChatsIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, ListUserChatsIqRequestPojo> {

    public static final String STUDENT_PARAM_KEY = "STUDENT_PARAM_KEY";

    @Override
    protected ListUserChatsIqRequestPojo doInBackground(HashMap<String, String>... params) {
        try {

            final XMPPBOSHConnection xmppConnection = Connection.getInstance().getXmppConnection();


            ListUserChatsIqRequest iqRequest = new ListUserChatsIqRequest();
            iqRequest.setStudent(params[0].get(STUDENT_PARAM_KEY));
            iqRequest.setType(IQ.Type.get);
            iqRequest.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = xmppConnection.createStanzaCollectorAndSend(iqRequest);
            final ListUserChatsIqRequest listUserChatsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(SmackConfiguration.getDefaultReplyTimeout());
//            final ListUserChatsIqRequestPojo requestPojo = ChatConverter.convertToListUserChatsIqRequestPojo(listUserChatsIqRequest);
//            return requestPojo;

        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException
                | XmppStringprepException
                | InterruptedException e) {
            e.printStackTrace();
        }
        return new ListUserChatsIqRequestPojo();
    }


}
