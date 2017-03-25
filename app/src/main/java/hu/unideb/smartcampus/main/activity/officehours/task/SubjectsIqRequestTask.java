package hu.unideb.smartcampus.main.activity.officehours.task;

import android.os.AsyncTask;

import org.jxmpp.jid.Jid;

import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsProcessMessagePojo;

/**
 * Created by Headswitcher on 2017. 03. 24..
 */

public class SubjectsIqRequestTask extends AsyncTask<Jid, Integer, AskSubjectsProcessMessagePojo> {


    @Override
    protected AskSubjectsProcessMessagePojo doInBackground(Jid... params) {
   /*     SubjectsIqRequest iq = new SubjectsIqRequest();
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
        return new AskSubjectsProcessMessagePojo();*/
        return null;
    }
}
