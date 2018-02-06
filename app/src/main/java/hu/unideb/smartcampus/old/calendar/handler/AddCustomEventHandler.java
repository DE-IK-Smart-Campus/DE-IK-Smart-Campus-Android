package hu.unideb.smartcampus.old.calendar.handler;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import hu.unideb.smartcampus.shared.iq.request.AddCustomEventIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.CustomEventIqElement;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class AddCustomEventHandler {

    public static void add(String uuid,Long eventWhen,String eventName, String eventDescription, String eventPlace, Long eventStart, Long eventEnd,String repeat, String remainder) {
        XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        try {
            AddCustomEventIqRequest addCustomEventIqRequest = new AddCustomEventIqRequest();
            addCustomEventIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());
            CustomEventIqElement myCustomEventIqElement = new CustomEventIqElement();

            myCustomEventIqElement.setGuid(uuid);
            myCustomEventIqElement.setEventWhen(eventWhen);
            myCustomEventIqElement.setEventName(eventName);
            myCustomEventIqElement.setEventDescription(eventDescription);
            myCustomEventIqElement.setEventPlace(eventPlace);
            myCustomEventIqElement.setEventStart(eventStart);
            myCustomEventIqElement.setEventEnd(eventEnd);
            myCustomEventIqElement.setEventRepeat(repeat);
            myCustomEventIqElement.setReminder(remainder);

            addCustomEventIqRequest.setCustomEvent(myCustomEventIqElement);

            addCustomEventIqRequest.setType(IQ.Type.set);
            addCustomEventIqRequest.setTo(JidCreate.from(ADMINJID));
            addCustomEventIqRequest.setFrom(xmppboshConnection.getUser());

            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(addCustomEventIqRequest);
            stanzaCollectorAndSend.nextResultOrThrow(5000);

        } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
            e.printStackTrace();
        }
    }
}
