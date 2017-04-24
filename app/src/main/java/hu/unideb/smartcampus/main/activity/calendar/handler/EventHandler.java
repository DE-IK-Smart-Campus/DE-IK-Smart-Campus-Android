package hu.unideb.smartcampus.main.activity.calendar.handler;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskCustomEventPojo;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskTimetableEventPojo;
import hu.unideb.smartcampus.main.activity.calendar.task.CustomEventIqRequestTask;
import hu.unideb.smartcampus.main.activity.calendar.task.TimetableIqRequestTask;
import hu.unideb.smartcampus.sqlite.manager.DatabaseManager;
import hu.unideb.smartcampus.sqlite.model.CustomEvent;
import hu.unideb.smartcampus.sqlite.model.TimetableEvent;
import hu.unideb.smartcampus.xmpp.Connection;

public class EventHandler {

    private FragmentManager fragmentManager;
    private Context context;
    private AskTimetableEventPojo askTimetableEventPojo;
    private ObjectMapper objectMapper;

    public EventHandler(FragmentManager fragmentManager, Context context) throws SmackException.NotConnectedException, InterruptedException {
        super();
        askTimetableEventPojo = new AskTimetableEventPojo();
        this.fragmentManager = fragmentManager;
        objectMapper = new ObjectMapper();
        this.context = context;
    }

    public void sendDefaultMesg() {
        try {
            HashMap<String, String> param = new HashMap<>();

//            AskTimetableEventPojo askTimetableEventPojo = Connection.getInstance().createLoadingDialogFragment(new TimetableIqRequestTask(), fragmentManager, param);
//            AskCustomEventPojo askCustomEventPojo = Connection.getInstance().createLoadingDialogFragment(new CustomEventIqRequestTask(), fragmentManager, param);

            DatabaseManager databaseManager = new DatabaseManager(context);
            databaseManager.open();

            List<TimetableEvent> timetableEvents = askTimetableEventPojo.getTimetableEvents();
//            List<CustomEvent> customEvents = askCustomEventPojo.getCustomEvents();

            List<TimetableEvent> timetableEventListAll = databaseManager.getAllTimetableEvent();
            List<CustomEvent> customEventListAll = databaseManager.getAllCustomEvent();

                for (TimetableEvent timetableEvent : timetableEvents) {
                    databaseManager.insertTimetableEvent(timetableEvent);
                }

//                for (CustomEvent customEvent : customEvents) {
//                    databaseManager.insertCustomEvent(customEvent);
//            }

            databaseManager.close();

            Bundle bundle = new Bundle();
            changeToCalndarFragmentView(bundle);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changeToCalndarFragmentView(Bundle bundle) {
        Fragment fragment = new CalendarFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "CALENDAR_TAG");
        fragmentTransaction.commitAllowingStateLoss();
    }

}
