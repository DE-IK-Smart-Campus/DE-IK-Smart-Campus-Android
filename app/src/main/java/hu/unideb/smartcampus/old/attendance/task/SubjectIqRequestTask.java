package hu.unideb.smartcampus.old.attendance.task;

import android.os.AsyncTask;

import java.util.HashMap;

import hu.unideb.smartcampus.old.attendance.pojo.AskSubjectPojo;

public class SubjectIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, AskSubjectPojo> {
    @Override
    protected AskSubjectPojo doInBackground(HashMap<String, String>[] hashMaps) {
        return null;
    }
//    @Override
//    protected AskSubjectPojo doInBackground(HashMap<String, String>... hashMaps) {
//        try {
//            ListUserAttendanceIqRequest iq = new ListUserAttendanceIqRequest();
//            EntityFullJid user = Connection.getInstance().getXmppConnection().getUser();
//            iq.setStudent(user.getLocalpartOrThrow().toString());
//            iq.setType(IQ.Type.get);
//            iq.setFrom(user);
//            iq.setTo(JidCreate.from(ADMINJID));
//
//
//            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
//            final ListUserAttendanceIqRequest calendarSubjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
//            return AttendanceConverter.convertToAskSubjectPojo(calendarSubjectsIqRequest);
//
//        } catch (SmackException.NotConnectedException
//                | XMPPException.XMPPErrorException
//                | XmppStringprepException
//                | SmackException.NoResponseException
//                | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return new AskSubjectPojo();
//    }
}
