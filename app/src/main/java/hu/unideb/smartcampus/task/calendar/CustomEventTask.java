package hu.unideb.smartcampus.task.calendar;

import android.os.AsyncTask;

import hu.unideb.smartcampus.pojo.calendar.AskCustomEventPojo;

public class CustomEventTask extends AsyncTask<String, Long, AskCustomEventPojo> {
    @Override
    protected AskCustomEventPojo doInBackground(String... strings) {
        return null;
    }

//    @Override
//    protected AskCustomEventPojo doInBackground(HashMap<String, String>... params) {
//        try {
//
//            ListCustomEventIqRequest iq = new ListCustomEventIqRequest();
//            iq.setStudent(Connection.getInstance().getXmppConnection().getUser().getLocalpartOrThrow().toString());
//            iq.setType(IQ.Type.get);
//            iq.setTo(JidCreate.from(ADMINJID));
//
//            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
//            final ListCustomEventIqRequest listCustomEventIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
//            return CustomEventConverter.convertToAskCustomEventPojo(listCustomEventIqRequest);
//
//        } catch (SmackException.NotConnectedException
//                | XMPPException.XMPPErrorException
//                | SmackException.NoResponseException
//                | XmppStringprepException
//                | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return new AskCustomEventPojo();
//    }
}
