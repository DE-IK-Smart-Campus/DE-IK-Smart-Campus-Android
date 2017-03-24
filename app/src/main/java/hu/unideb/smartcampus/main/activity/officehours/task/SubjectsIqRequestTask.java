package hu.unideb.smartcampus.main.activity.officehours.task;

import android.os.AsyncTask;
import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsProcessMessagePojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Instructor;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Subject;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2017. 03. 24..
 */

public class SubjectsIqRequestTask extends AsyncTask<Jid, Integer, AskSubjectsProcessMessagePojo> {


    @Override
    protected AskSubjectsProcessMessagePojo doInBackground(Jid... params) {
        SubjectsIqRequest iq = new SubjectsIqRequest();
        iq.setStudent("adamkai");
        iq.setType(IQ.Type.get);
        try {
            iq.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend;
            stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);

            final SubjectsIqRequest stanza = stanzaCollectorAndSend.nextResultOrThrow(5000);
            Log.e(TAG, "doInBackground: Got stanza : " + stanza.getSubjects());
            AskSubjectsProcessMessagePojo askSubjectsProcessMessagePojo = new AskSubjectsProcessMessagePojo();
            Subject subj = new Subject();
            subj.setName(stanza.getSubjects().get(0).getSubjectName());
            subj.setInstructors(new ArrayList<Instructor>());
            List<Subject> ge = new ArrayList<>();
            ge.add(subj);
            askSubjectsProcessMessagePojo.setSubjects(ge);
            Log.e(TAG, "doInBackground: return mockpojo");
            return askSubjectsProcessMessagePojo;

        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (
                XmppStringprepException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        }
        return new AskSubjectsProcessMessagePojo();
    }
}
