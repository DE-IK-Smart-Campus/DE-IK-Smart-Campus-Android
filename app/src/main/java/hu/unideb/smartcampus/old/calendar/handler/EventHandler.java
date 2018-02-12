package hu.unideb.smartcampus.old.calendar.handler;


import android.content.Context;
import android.support.v4.app.FragmentManager;

import hu.unideb.smartcampus.pojo.calendar.AskCustomEventPojo;
import hu.unideb.smartcampus.pojo.calendar.AskTimetableEventPojo;

public class EventHandler {

    boolean isNetworkFinished = false;

    private AskTimetableEventPojo askTimetableEventPojo;
    private AskCustomEventPojo askCustomEventPojo;
    private Context context;
    private FragmentManager fragmentManager;
    static EventHandler instance;

    public static EventHandler getInstance() {
        if(instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

//    public void askEvents(FragmentManager fragmentManager, final Context context) {
//        try {
//            final Connection connection = Connection.getInstance();
//            this.fragmentManager = fragmentManager;
//            HashMap<String, String> param = new HashMap<>();
//            param.put("Actual JID", connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
////            askTimetableEventPojo = connection.runAsyncTask(new TimetableIqRequestTask(), param);
////            askCustomEventPojo = connection.runAsyncTask(new CustomEventIqRequestTask(), param);
//
//            DatabaseManager databaseManager = new DatabaseManager(context);
//
//            databaseManager.open();
//
//            databaseManager.deleteTable();
//
//            databaseManager.close();
//
//            databaseManager.open();

//            List<TimetableEvent> timetableEvents = askTimetableEventPojo.getTimetableEvents();
//            List<CustomEvent> customEvents = askCustomEventPojo.getCustomEvents();

//            for (TimetableEvent timetableEvent : timetableEvents) {
//                    databaseManager.insertTimetableEvent(timetableEvent);
//                }
//
//            for (CustomEvent customEvent : customEvents) {
//                    databaseManager.insertCustomEvent(customEvent);
//                }

//            isNetworkFinished = true;
//            changeToCalndarFragmentView(new Bundle());
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

//    }

//    private void changeToCalndarFragmentView(Bundle bundle) {
//        Fragment fragment = new CalendarFragmentOld();
//        fragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment, "CALENDAR_TAG");
//        fragmentTransaction.commitAllowingStateLoss();
//    }

}
