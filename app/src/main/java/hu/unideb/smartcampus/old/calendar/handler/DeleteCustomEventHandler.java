package hu.unideb.smartcampus.old.calendar.handler;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import hu.unideb.smartcampus.shared.iq.request.DeleteCustomEventIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class DeleteCustomEventHandler {

    public static void delete(String uuid) {
        XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        try {
            DeleteCustomEventIqRequest deleteCustomEventIqRequest = new DeleteCustomEventIqRequest();
            deleteCustomEventIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());

            deleteCustomEventIqRequest.setEventGuid(uuid);

            deleteCustomEventIqRequest.setType(IQ.Type.set);
            deleteCustomEventIqRequest.setTo(JidCreate.from(ADMINJID));
            deleteCustomEventIqRequest.setFrom(xmppboshConnection.getUser());

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend( deleteCustomEventIqRequest);
            stanzaCollectorAndSend.nextResultOrThrow(5000);

        } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
            e.printStackTrace();
        }
    }
}
