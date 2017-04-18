package hu.unideb.smartcampus.main.activity.calendar.handler;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskTimetableEventPojo;

public class TimetableEventHandler {

    private FragmentManager fragmentManager;
    private Context context;

    public TimetableEventHandler(FragmentManager fragmentManager,Context context)throws SmackException.NotConnectedException, InterruptedException {
        super();
        AskTimetableEventPojo askTimetableEventPojo = new AskTimetableEventPojo();
        this.fragmentManager = fragmentManager;
        ObjectMapper objectMapper = new ObjectMapper();
        this.context = context;
    }

    public void sendDefaultMesg() {
    /*    try{
            HashMap<String, String> param = new HashMap<>();
            AskTimetableEventPojo askTimetableEventPojo = Connection.getInstance().createLoadingDialog(new TimetableIqRequestTask(), fragmentManager,param);

            DatabaseManager databaseManager = new DatabaseManager(context);
            databaseManager.open();
            List<TimetableEvent> timetableEvents = askTimetableEventPojo.getTimetableEvents();

            for(TimetableEvent timetableEvent : timetableEvents) {
                timetableEvent.getTimetableEventDate().toString();
                databaseManager.insertTimetableEvent(timetableEvent);
            }
            databaseManager.close();

            Bundle bundle = new Bundle();
            changeToCalndarFragmentView(bundle);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        */
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
