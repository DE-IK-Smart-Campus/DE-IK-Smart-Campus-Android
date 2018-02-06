package hu.unideb.smartcampus.old.officehours.task;

import android.os.AsyncTask;


import java.util.HashMap;

import hu.unideb.smartcampus.old.officehours.pojo.Instructor;


/**
 * Created by Headswitcher on 2017. 03. 24..
 * //TODO
 */

public class InstructorConsultingDatesIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, Instructor> {
    @Override
    protected Instructor doInBackground(HashMap<String, String>[] hashMaps) {
        return null;
    }


//    public static final String INSTRUCTOR_ID = "INSTRUCTOR_ID";
//
//    @Override
//    protected Instructor doInBackground(HashMap<String, String>... params) {
//        try {
//            InstructorConsultingDatesIqRequest iq = new InstructorConsultingDatesIqRequest();
//
//            iq.setInstructorId(params[0].get(INSTRUCTOR_ID));
//            iq.setType(IQ.Type.get);
//            iq.setTo(JidCreate.from(ADMINJID));
//
//            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
//            final InstructorConsultingDatesIqRequest iqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
//            return OfficeHourConverter.convertToAskInstructorOfficeHourPojo(iqRequest);
//
//        } catch (SmackException.NotConnectedException
//                | XMPPException.XMPPErrorException
//                | SmackException.NoResponseException
//                | XmppStringprepException
//                | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new Instructor();
//    }
}
