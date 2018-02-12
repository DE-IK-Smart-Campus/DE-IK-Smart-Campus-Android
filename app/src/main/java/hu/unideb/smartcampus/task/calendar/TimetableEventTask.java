package hu.unideb.smartcampus.task.calendar;

import android.os.AsyncTask;

import hu.unideb.smartcampus.pojo.calendar.AskTimetableEventPojo;

public class TimetableEventTask extends AsyncTask<String, Long, AskTimetableEventPojo> {
    @Override
    protected AskTimetableEventPojo doInBackground(String... strings) {
        return null;
    }

//    @Override
//    protected AskTimetableEventPojo doInBackground(HashMap<String, String>... params) {
//        try {
//            CalendarSubjectsIqRequest iq = new CalendarSubjectsIqRequest();
//            EntityFullJid user = Connection.getInstance().getXmppConnection().getUser();
//            iq.setStudent(user.getLocalpartOrThrow().toString());
//            iq.setStartPeriod(1485813600L);
//            iq.setEndPeriod(1496181600L);
//            iq.setType(IQ.Type.get);
//            iq.setFrom(user);
//            iq.setTo(JidCreate.from(ADMINJID));
//
//            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
//            final CalendarSubjectsIqRequest calendarSubjectsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
//            return TimetableEventConverter.convertToAskTimeTableEventPojo(calendarSubjectsIqRequest);
//
//        } catch (SmackException.NotConnectedException
//                | XMPPException.XMPPErrorException
//                | XmppStringprepException
//                | SmackException.NoResponseException
//                | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return new AskTimetableEventPojo();
//
//    }
}
